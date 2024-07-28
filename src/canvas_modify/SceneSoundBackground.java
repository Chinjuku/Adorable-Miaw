package canvas_modify;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;



public class SceneSoundBackground implements SceneSoundPlayer{
    private Clip clip;

    private File[] sound;

    public SceneSoundBackground() {
        sound = new File[6]; //Create an array of File objects with a size of 6
        sound[0] = new File("src/resource/audio/Attention.wav");
        sound[1] = new File("src/resource/audio/OMG.wav");
        sound[2] = new File("src/resource/audio/Hurt.wav");
        sound[3] = new File("src/resource/audio/Ditto.wav");
        sound[4] = new File("src/resource/audio/Cookie.wav");
        sound[5] = new File("src/resource/audio/HypeBoy.wav");

    }

    @Override
    public void getSoundPath(int filePath) {
        playAudio(filePath);
    }

    private void playAudio(int filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound[filePath].getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing audio: " + e.getMessage());
        }
    }

    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            // Create a Runnable to gradually reduce the volume
            Runnable stopRunnable = new Runnable() {
                @Override
                public void run() {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float startVolume = gainControl.getValue();
                    float endVolume = -80.0f; // Volume to reduce to (0 dB)
                    float duration = 0.5f; // Duration in seconds
                    float decrementPerMillis = (startVolume - endVolume) / (duration * 1000);

                    for (float volume = startVolume; volume >= endVolume; volume -= decrementPerMillis) {
                        gainControl.setValue(volume);
                        try {
                            Thread.sleep(15); // Adjust the delay as needed for smooth volume reduction
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    // Stop the sound
                    clip.stop();
                    clip.close();
                }
            };

            Thread stopThread = new Thread(stopRunnable);
            stopThread.start();
        }
    }


}