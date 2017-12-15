import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


public class KSoundMP3 extends MediaView{

    /** 繰り返し */
    private		boolean			flgLoop			= true;

    // メディアプレイヤー
    private		MediaPlayer		mediaPlayer		= null;

    /**
     * コンストラクタ
     * <pre>
     * MP3オブジェクトを生成します。
     * </pre>
     * @param obj パスを決めるオブジェクト
     * @param fileName ファイル名
     * @param flgLoop true：繰り返す ／ false：繰り返さない
     */
    public KSoundMP3(Object obj, String fileName, boolean flgLoop){

        super();

        // パスを決めるオブジェクト
        if(obj == null){
            obj = this;
        }

        // 繰り返し設定
        this.flgLoop = flgLoop;

        // URLを取得
        URL resource = obj.getClass().getResource(fileName);

        // メディアを取得
        Media media = new Media(resource.toString());

        // メディアプレイヤーを取得
        mediaPlayer = new MediaPlayer(media);

        // メディアプレイヤーを設定
        super.setMediaPlayer(mediaPlayer);

        // 繰り返し設定
        this.flgLoop = flgLoop;

        // 繰り返す場合、
        if(this.flgLoop){

            // 繰り返し設定
            mediaPlayer.setCycleCount(Integer.MAX_VALUE);

        }

    } // end KSoundMP3

    /**
     * 演奏スタート
     */
    public void start(){

        // スタート
        mediaPlayer.play();

    }

    /**
     * 演奏ストップ
     */
    public void stop(){

        // 停止
        mediaPlayer.stop();

    }

    /**
     * 演奏一時停止
     */
    public void pause(){

        // 一時停止
        mediaPlayer.pause();

    }

    /**
     * 演奏位置初期化
     */
    public void init(){

        // ポジションを戻す
        mediaPlayer.seek(Duration.ZERO);

    }

}
