package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip clip;
    private boolean isPlaying = false;
    private String currentTrack = null;
    private FloatControl volumeControl;

    public MusicPlayer() {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void loadAndPlay(String filePath) {
        try {
            if (isPlaying) {
                stop();
            }

            File musicFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip.open(audioStream);

            // Get volume control
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            currentTrack = filePath;
            play();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null && !isPlaying) {
            clip.setMicrosecondPosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaying = true;
        }
    }

    public void stop() {
        if (clip != null && isPlaying) {
            clip.stop();
            isPlaying = false;
        }
    }

    public void setVolume(float volume) {
        // volume should be between 0.0 and 1.0
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float value = min + (max - min) * volume;
            volumeControl.setValue(value);
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}