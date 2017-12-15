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
        mapData = new MapData(21,15);
        chara = new MoveChara(1,1,mapData);
        mapImageView = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageView[index] =new ImageView();
                mapGrid.add(mapImageView[index], x, y);
            }
        }
        //Media start = new Media(getClass().getResource("BGM_0.mp3").toExternalForm());
        //MediaPlayer st = new MediaPlayer(start);
        //st.play();
        //AudioClip clip = new AudioClip(getClass().getResource("BGM_0.wav").toString());
        //        clip.play();
        //        clip.loop(true);
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

    public void keyAction(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.DOWN){
            downButtonAction();
        }else if (key == KeyCode.RIGHT){
            rightButtonAction();
        }else if(key == KeyCode.LEFT) {
            leftButtonAction();
        }else if(key == KeyCode.UP) {
            upButtonAction();
        }

    }

    public void upButtonAction(){
        System.out.println("UP");
        chara.setCharaDir(MoveChara.TYPE_UP);
        chara.move(0, -1);
        mapPrint(chara, mapData);
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }

    public void leftButtonAction(){
        System.out.println("LEFT");
        chara.setCharaDir(MoveChara.TYPE_LEFT);
        chara.move(-1, 0);
        mapPrint(chara, mapData);
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }

    public void downButtonAction(){
        System.out.println("DOWN");
        chara.setCharaDir(MoveChara.TYPE_DOWN);
        chara.move(0, 1);
        mapPrint(chara, mapData);
    }
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        System.out.println("RIGHT");
        chara.setCharaDir(MoveChara.TYPE_RIGHT);
        chara.move( 1, 0);
        mapPrint(chara, mapData);
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

}
