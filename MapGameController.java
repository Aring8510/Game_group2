import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;


public class MapGameController implements Initializable {
    public MapData mapData;
    public int[][] map;
    public MoveChara chara;
    public Enemy enemy;
    public GridPane mapGrid;
    public ImageView[] mapImageView;
    public Label floor;
    public Label clock;
    private int sec = 0;
    private int min = 0;
    private int initCount = 1;
    private double rate = 1.0;
    private AudioClip bgm,se;
    public ImageView Wiz;
    public Timeline timeline;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bgm = new AudioClip(Paths.get("BGM/BGM_0.wav").toUri().toString());
        bgm.setCycleCount(AudioClip.INDEFINITE);
        se = new AudioClip(Paths.get("SE/nya.wav").toUri().toString());
        controlNPC();
        init();
        timer();
    }

    private void init(){
        bgm.stop();
        if (initCount < 4 ){
            timeline.play();
        }
        if(initCount == 1){
          rate = 1.0;
        } else if (rate >= 0.7){
          rate *= 0.97;
        }
        bgm.setRate(rate);
        bgm.play();

        //マップの初期設定(縦15×横21マス)
        mapData = new MapData(21,15);
        //キャラクターの初期位置設定と画像設定
        chara = new MoveChara(1,1,mapData);
        enemy = new Enemy(19,13,mapData);
        mapImageView = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                final int index = y*mapData.getWidth() + x;
                mapImageView[index] = new ImageView();
                mapGrid.add(mapImageView[index], x, y);
            }
        }
        map = new int[mapData.getHeight()][mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                if(mapData.getMap(x,y) == MapData.TYPE_WALL ){
                    map[y][x] = -1;
                }else{
                    map[y][x] = 0;
                }
            }
        }
        mapPrint();
    }

    public void controlNPC(){
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent) -> {
            final int x = enemy.getPosX();
            final int y = enemy.getPosY();
            int minIndex = 0;
            int[] mapInfo  = {
                map[y-1][x],
                map[y][x-1],
                map[y+1][x],
                map[y][x+1]
            };

            int i = 0;
            int minCost = Integer.MAX_VALUE;
            for(int info:mapInfo){
                if(info > -1 && info <= minCost){
                    minCost = info;
                    minIndex = i;
                }
                i++;
            }

            final String[] dir = {"up","left","down","right"};
            switch (dir[minIndex]){
                case "up":
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_UP);
                    enemy.move(0,-1);
                    break;
                case "left":
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_LEFT);
                    enemy.move(-1,0);
                    break;
                case "down":
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_DOWN);
                    enemy.move(0,1);
                    break;
                case "right":
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_RIGHT);
                    enemy.move(1,0);
                    break;
            }

            /*for(int Y =0;Y<15;Y++){
                for(int X =0;X<21; X++){
                    if(map[Y][X] == -1){
                        System.out.print("*"+" ");
                    }else if(map[Y][X] !=0){
                        System.out.print(map[Y][X]+" ");
                    }else{
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }*/
            enemyPrint(x, y);
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
    public void printWizMap(){
        int dir = chara.getCharaDir();
        //0:Down
        //1:Right
        //2:Left
        //3:Up
        int x = chara.getPosX();
        int y = chara.getPosY();
        int front;
        int ahead;
        int right;
        int left;
        switch (dir){
            case 0://Down
                front = mapData.getMap(x,y+1);
                ahead = mapData.getMap(x,y+2);
                right = mapData.getMap(x-1,y+1);
                left  = mapData.getMap(x+1,y+1);
                Wiz.setImage(WizCheck(front,ahead,right,left));
                break;
            case 1://left
                front = mapData.getMap(x-1,y);
                ahead = mapData.getMap(x-2,y);
                right = mapData.getMap(x-1,y-1);
                left  = mapData.getMap(x-1,y+1);
                Wiz.setImage(WizCheck(front,ahead,right,left));
                break;
            case 2://Right
                front = mapData.getMap(x+1,y);
                ahead = mapData.getMap(x+2,y);
                right = mapData.getMap(x+1,y+1);
                left  = mapData.getMap(x+1,y-1);
                Wiz.setImage(WizCheck(front,ahead,right,left));
                break;
            case 3://Up
                front = mapData.getMap(x,y-1);
                ahead = mapData.getMap(x,y-2);
                right = mapData.getMap(x+1,y-1);
                left  = mapData.getMap(x-1,y-1);
                Wiz.setImage(WizCheck(front,ahead,right,left));
                break;
        }
    }
    public Image WizCheck(int front, int ahead, int right, int left){
        String s;
        if (front == 3){
            if (right == 1){
                if (left == 1){
                    s = "pic/straight.jpg";
                }else{
                    s = "pic/left.jpg";
                }
            }else{
                s = "pic/right.jpg";
            }
        } else if (front == 0){
            if (right == 0){
                if (left == 0){
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/3.jpg";
                    }else{
                        s = "pic/6.jpg";
                    }
                }else{
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/2.jpg";
                    }else{
                        s = "pic/5.jpg";
                    }
                }
            }else{
                if (left == 0){
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/4.jpg";
                    }else{
                        s = "pic/8.jpg";
                    }
                }else{
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/0.jpg";
                    }else{
                        s = "pic/1.jpg";
                    }
                }
            }
        }else if (front==2){
            if (right == 0){
                if (left == 0){
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/12.jpg";
                    }else{
                        s = "pic/15.jpg";
                    }
                }else{
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/11.jpg";
                    }else{
                        s = "pic/14.jpg";
                    }
                }
            }else{
                if (left == 0){
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/13.jpg";
                    }else{
                        s = "pic/17.jpg";
                    }
                }else{
                    if (ahead == 0 || ahead == 2 || ahead == 3){
                        s = "pic/9.jpg";
                    }else{
                        s = "pic/10.jpg";
                    }
                }
            }
        }else{
            s = "pic/7.jpg";
        }
        return new Image (getClass().getResourceAsStream(s));
    }
    public void timer(){
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent) -> {
            clock.setText(String.format("%d:%02d",min,sec));
            sec++;
            if(sec >= 60){
                sec = 0;
                min++;
            }
        }
        ));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }
    //マップ描画
    public void mapPrint(){
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                double distance =Math.sqrt((x - chara.getPosX()) *  (x - chara.getPosX())+ (y - chara.getPosY()) * (y - chara.getPosY())); 
                final int index = y*mapData.getWidth() + x;
                if (x == chara.getPosX() && y == chara.getPosY() ){
                    mapImageView[index].setImage(chara.getImage());
                } else if (x == enemy.getPosX() && y == enemy.getPosY()){
                    // do nothing
                } else {
                    if(initCount == 3){
                        if(distance <= 2){
                            mapImageView[index].setImage(mapData.getImage(x,y));
                        }else{
                            mapImageView[index].setImage(mapData.getBlack());
                        }
                    }else{
                        mapImageView[index].setImage(mapData.getImage(x,y));
                    }
                }
            }
        }
        printWizMap();
    }

    public void enemyPrint(final int prevX, final int prevY){
        final int prevIndex = prevY*mapData.getWidth()+prevX;
        mapImageView[prevIndex].setImage(mapData.getImage(prevX,prevY));
        final int index1 = enemy.getPosY()*mapData.getWidth() + enemy.getPosX();
        mapImageView[index1].setImage(enemy.getImage());
    }

    public void func1ButtonAction(ActionEvent event) { init(); }
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }
    //アローキーを使ったキャラクターの操作
    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        switch(key){
            case DOWN  : downButtonAction();  break;
            case RIGHT : rightButtonAction(); break;
            case UP    : upButtonAction();    break;
            case LEFT  : leftButtonAction();  break;
        }
    }
    public void downButtonAction(){
        //System.out.println("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        switch(chara.getCharaDir()){
            case MoveChara.TYPE_UP:
                chara.move(0,1);
                break;
            case MoveChara.TYPE_LEFT:
                chara.move(1,0);
                break;
            case MoveChara.TYPE_DOWN:
                chara.move(0,-1);
                break;
            case MoveChara.TYPE_RIGHT:
                chara.move(-1,0);
                break;
        }
        afterMove();
    }
    public void downButtonAction(ActionEvent event) {
    }

    public void rightButtonAction(){
        //System.out.println("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        afterMove();
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }
    public void upButtonAction(){
        //System.out.println("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        switch(chara.getCharaDir()){
            case MoveChara.TYPE_UP:
                chara.move(0,-1);
                break;
            case MoveChara.TYPE_LEFT:
                chara.move(-1,0);
                break;
            case MoveChara.TYPE_DOWN:
                chara.move(0,1);
                break;
            case MoveChara.TYPE_RIGHT:
                chara.move(1,0);
                break;
        }
        afterMove();
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public void leftButtonAction(){
        //System.out.println("LFET");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        afterMove();
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
    public void afterMove(){
        
        if (initCount<4){
            mapPrint();
        }
        if(chara.getPosX() == 19 && chara.getPosY() == 13 && chara.getItemCount() >= 3){
            initCount++;
            if (initCount == 4 ){
                Wiz.setImage(new Image (getClass().getResourceAsStream("pic/clear.jpg")));
                timeline.stop();
            }
            floor.setText("B"+initCount);
            if (initCount<4){
                init();
            }
        }else if(chara.getPosX() == enemy.getPosX() && chara.getPosY() == enemy.getPosY()){
            se.play();
            Wiz.setImage(new Image (getClass().getResourceAsStream("pic/cat.jpg")));
            timeline.stop();
        }
    }
}
