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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public ImageView[] mapImageView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }
    private void init(){

        //マップの初期設定(縦15×横21マス)
        mapData = new MapData(21,15);
        //キャラクターの初期位置設定と画像設定
        chara = new MoveChara(1,1,mapData);
        mapImageView = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageView[index] =new ImageView();
                mapGrid.add(mapImageView[index], x, y);
            }
        }
        mapPrint(chara, mapData);
        KSoundMP3 mp3 = new KSoundMP3(null,"BGM/BGM_1.wav",true);//mp3再生
        mp3.init();
        mp3.start();
        //System.out.println("Music0");
        mapPrint(chara, mapData);
    }


    public void mapPrint(MoveChara c, MapData m){
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==c.getPosX() && y==c.getPosY()){
                    mapImageView[index].setImage(c.getImage());
                } else {
                    mapImageView[index].setImage(m.getImage(x,y));
                }
            }
        }

    }
    public void func1ButtonAction(ActionEvent event) { }
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
        if(chara.getPosX()==19 && chara.getPosY() == 13){
            init();
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
        if(chara.getPosX()==19 && chara.getPosY() == 13){
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
        if(chara.getPosX()==19 && chara.getPosY() == 13){
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
        if(chara.getPosX()==19 && chara.getPosY() == 13){
            init();
        }
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }
}
