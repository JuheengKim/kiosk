package util;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/** Ŭ�� ȿ���� **/
public class SoundEffectUtil {
	
	static Media sound = new Media(new File("resource/sound/click.mp3").toURI().toString());
	
	public static void sound() {
		//sound	
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}
}