import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MapGame extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //File f = new File("christmasnoorgan.mp3");
        //Media Music = new Media("christmasnoorgan.mp3");
        //MediaPlayer Play = new MediaPlayer(Music);
        //MediaView MV = new MediaView(Play);
        stage = primaryStage;
        primaryStage.setTitle("Run in the maze!");
        //MapGame.fxmlウィンドウ上の要素のレイアウトを決めるファイル
        Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml"));
        Scene myScene = new Scene(myPane_top);
        //Play.play();
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

