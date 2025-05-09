package heroes.journey.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {

    private Music backgroundMusic;

    private static MusicManager musicManager;

    private MusicManager() {
    }

    public void playSong(String song) {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(song));
        backgroundMusic.setLooping(true);   // So it loops automatically
        backgroundMusic.setVolume(0.5f);    // Set volume (0.0 to 1.0)
        backgroundMusic.play();            // Start playing
    }

    public static void play(String song) {
        get().playSong(song);
    }

    public void dispose() {
        if (backgroundMusic != null)
            backgroundMusic.dispose();         // Clean up resources
    }

    public static MusicManager get() {
        if (musicManager == null) {
            musicManager = new MusicManager();
        }
        return musicManager;
    }
}
