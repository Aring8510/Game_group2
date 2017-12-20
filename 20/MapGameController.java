import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.animation.Timeline;
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.animation.KeyValue;
import javafx.scene.media.*;
import java.nio.file.*;
import javafx.scene.layout.BorderPane;

public class MapGameController implements Initializable {
    public MapData mapData;
    public int[][] map;
    public MoveChara chara;
    public MoveChara enemy;
    public GridPane mapGrid;
    public ImageView Wiz;
    public ImageView[] mapImageView;
    public Label floor;
    public Label clock;
    private static int min = 0;
    private static int sec = 0;
    private static int initCount = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        timer();
    }
    private void init(){
        AudioClip clip_1F;
        AudioClip clip_2F;
        AudioClip clip_3F;
        AudioClip clip_4F;
        clip_1F = new AudioClip(Paths.get("BGM/BGM_0.wav").toUri().toString());
        clip_2F = new AudioClip(Paths.get("BGM/BGM_1.wav").toUri().toString());
        clip_3F = new AudioClip(Paths.get("BGM/BGM_2.wav").toUri().toString());
        clip_4F = new AudioClip(Paths.get("BGM/BGM_3.wav").toUri().toString());
        switch(mapData.floor_count){
            case 1:
                clip_1F.play();

                break;
            case 2:
                clip_1F.stop();
                clip_2F.play();
                break;
            case 3:
                clip_2F.stop();
                clip_3F.play();
                break;
            case 4 :
                clip_3F.stop();
                clip_4F.play();
        }
        mapData.get_item_count = 0;
        //マップの初期設定(縦15×横21マス)
        mapData = new MapData(21,15);
        //キャラクターの初期位置設定と画像設定
        chara = new MoveChara(1,1,mapData);
        enemy = new MoveChara(19,13,mapData);
        mapImageView = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageView[index] =new ImageView();
                mapGrid.add(mapImageView[index], x, y);
            }
        }
        mapPrint(chara, mapData);
        enemyPrint(enemy,mapData);
        controlNPC();
    }
    public void controlNPC(){
        map = new int[16][22];
        for(int y=0; y<15; y++){
            for(int x=0; x<21; x++){
                if(mapData.getMap(x,y) == 1 ){
                    map[y][x] = -1;
                }else{
                    map[y][x] = 0;
                }
            }
        }
        for(int y =0;y<15;y++){
            for(int x =0;x<21; x++){
                if(map[y][x] == -1){
                    System.out.print("*"+" ");
                }else{
                    System.out.print(" "+" ");
                }
            }
            System.out.println();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                int x = enemy.getPosX();
                int y = enemy.getPosY();
                if(map[y-1][x] >-1 && map[y-1][x] <= map[y][x]){
                    //上が壁ではなくかつ今の座標より評価が小さい時上
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_UP);
                    enemy.move(0,-1);
                    enemyPrint(enemy,mapData);
                }else if(map[y+1][x] >-1 && map[y+1][x] <= map[y][x]){
                    //下が壁でなくかつ今の座標より評価が小さい時下
                    map[y][x]++;
                    enemy.setCharaDir(MoveChara.TYPE_DOWN);
                    enemy.move(0,1);
                    enemyPrint(enemy,mapData);
                }else if(map[y][x-1] >-1 && map[y][x-1] <= map[y][x]){
                    //左が壁でなくかつ今の座標より評価が小さい時左
                    enemy.setCharaDir(MoveChara.TYPE_LEFT);
                    map[y][x]++;
                    enemy.move(-1,0);
                    enemyPrint(enemy,mapData);
                }else if(map[y][x+1] > -1 && map[y][x+1] <= map[y][x]){
                    //右が壁でなくかつ今の座標より評価が小さい時右
                    enemy.setCharaDir(MoveChara.TYPE_RIGHT);
                    map[y][x]++;
                    enemy.move(1,0);
                    enemyPrint(enemy,mapData);
                }else if(map[y-1][x] <-1){
                    //上が壁の時下へ進む
                    enemy.setCharaDir(MoveChara.TYPE_DOWN);
                    map[y][x]++;
                    enemy.move(0,-1);
                    enemyPrint(enemy,mapData);
                }else /*if(map[y][x-1] < -1)*/{
                    //左が壁の時右へ進む
                    enemy.setCharaDir(MoveChara.TYPE_RIGHT);
                    map[y][x]++;
                    enemy.move(1,0);
                    enemyPrint(enemy,mapData);
                }
                /*
                for(int Y =0;Y<15;Y++){
                    for(int X =0;X<21; X++){
                        if(map[Y][X] == -1){
                            System.out.print("*"+" ");
                        }else if(map[Y][X] !=0){
                            System.err.print(map[Y][X]+" ");
                        }else{
                            System.err.print("  ");
                        }
                    }
                    System.out.println();
                }
                */
            }
        }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
        if (front == 0){
            if (right == 0){
                if (left == 0){
                    if (ahead == 0){
                        s = "pic/3.png";
                    }else{
                        s = "pic/6.png";
                    }
                }else{
                    if (ahead == 0){
                        s = "pic/2.png";
                    }else{
                        s = "pic/5.png";
                    }
                }
            }else{
                if (left == 0){
                    if (ahead == 0){
                        s = "pic/4.png";
                    }else{
                        s = "pic/8.png";
                    }
                }else{
                    if (ahead == 0){
                        s = "pic/0.png";
                    }else{
                        s = "pic/1.png";
                    }
                }
            }
        }else{
            s = "pic/7.png";
        }
        Image i = new Image (getClass().getResourceAsStream(s));
        return i; 
    }
    public void timer(){
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(sec < 10){
                    clock.setText(min + ":"+"0"+sec);
                }else{
                    clock.setText( min +":"+sec);
                }
                sec ++;
                if(sec == 60){
                    sec = 0;
                    min++;
                }
            }
        }
        ));
        timer.setCycleCount(timer.INDEFINITE);
        timer.play();
    }
    //マップ描画
    public void mapPrint(MoveChara c, MapData m){
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==c.getPosX() && y==c.getPosY() ){
                    mapImageView[index].setImage(c.getImage());
                } else {
                    mapImageView[index].setImage(m.getImage(x,y));
                }
            }
        }
        printWizMap();
    }
    //敵の描写		
    public void enemyPrint(MoveChara e, MapData m){
        mapPrint(chara,mapData);
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==e.getPosX() && y==e.getPosY() ){
                    mapImageView[index].setImage(e.getImage());
                } 
            }
        }
    }
    public void func1ButtonAction(ActionEvent event) { init();}
    public void func2ButtonAction(ActionEvent event) { }
    public void func3ButtonAction(ActionEvent event) { }
    public void func4ButtonAction(ActionEvent event) { }
    //アローキーを使ったキャラクターの操作
    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            downButtonAction();
        }else if (key == KeyCode.RIGHT){
            rightButtonAction();
        }else if(key == KeyCode.UP){
            upButtonAction();
        }else if(key == KeyCode.LEFT){
            leftButtonAction();
        }
    }    
    public void downButtonAction(){
        //System.out.println("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        mapPrint(chara, mapData);
        //ゴール判定
        if(chara.getPosX()==19 && chara.getPosY() == 13 && mapData.get_item_count >=3){
            mapData.floor_count++;
            floor.setText("B"+mapData.floor_count);
        }
    }
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        //System.out.println("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        chara.move( 1, 0);
        mapPrint(chara, mapData);
        if(chara.getPosX()==19 && chara.getPosY() == 13 && mapData.get_item_count >=3){
            mapData.floor_count++;
            floor.setText("B"+mapData.floor_count);
            init();
        }
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }
    public void upButtonAction(){
        //System.out.println("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        chara.move(0, -1);
        mapPrint(chara, mapData);
        if(chara.getPosX()==19 && chara.getPosY() == 13 && mapData.get_item_count >=3){
            mapData.floor_count++;
            floor.setText("B"+mapData.floor_count);
            init();
        }
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public void leftButtonAction(){
        //System.out.println("LFET");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        chara.move( -1, 0);
        mapPrint(chara, mapData);
        if(chara.getPosX()==19 && chara.getPosY() == 13 && mapData.get_item_count >=3){
            mapData.floor_count++;
            floor.setText("B"+mapData.floor_count);
            init();
        }
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
}
