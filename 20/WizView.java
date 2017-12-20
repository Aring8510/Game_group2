
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;
import javafx.scene.image.*;

class WizView {
    MapData mapData;
    MoveChara chara;
    public void printWizMap(){
        BorderPane PaneWiz = new BorderPane();
        Image Wiz = new Image(getClass().getResourceAsStream("WALL.png"));
        ImageView imageView = new ImageView();
        imageView.setImage(Wiz);
        int dir = chara.getCharaDir();
        int x = chara.getPosX();
        int y = chara.getPosY();
        //switch (dir){
        //  case 0:
        PaneWiz.getChildren().addAll(imageView);
        }
    }

