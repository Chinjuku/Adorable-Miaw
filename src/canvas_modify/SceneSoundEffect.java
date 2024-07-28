package canvas_modify;

import canvas_modify.SceneModify;
import canvas_modify.SceneSoundPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;



public class SceneSoundEffect implements SceneSoundPlayer{
    private File[] sound;

    public SceneSoundEffect() {
        sound = new File[6]; // Create an array of File objects with a size of 6
        sound[0] = new File("res/sound/bg.wav");
        sound[1] = new File("res/sound/ordered.wav");
        sound[2] = new File("res/sound/hello.wav");
        sound[3] = new File("res/sound/serve.wav");
        sound[4] = new File("res/sound/angry.wav");
        sound[5] = new File("res/sound/exit.wav");
    }
    @Override
    public void getSoundPath(int filePath) {
        playAudio(filePath);
    }

    @Override
    public void stopAudio() {

    }

    private void playAudio(int filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound[filePath].getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }
}