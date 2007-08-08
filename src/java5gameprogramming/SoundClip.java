package java5gameprogramming;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.UnsupportedAddressTypeException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundClip {

	protected AudioInputStream sample;

	protected Clip clip;

	protected boolean looping = false;

	protected int repeat = 0;

	protected String filename = "";

	public boolean isLoaded() {
		return sample != null;
	}

	public SoundClip() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
		}
	}

	/**
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            Festzulegender filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return looping
	 */
	public boolean isLooping() {
		return looping;
	}

	/**
	 * @param looping
	 *            Festzulegender looping
	 */
	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	/**
	 * @return repeat
	 */
	public int getRepeat() {
		return repeat;
	}

	/**
	 * @param repeat
	 *            Festzulegender repeat
	 */
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	/**
	 * @return clip
	 */
	public Clip getClip() {
		return clip;
	}

	public SoundClip(String audiofile) {
		this();
		load(audiofile);
	}

	protected URL getURL(String filename) {
		URL url = null;
		try {
			url = this.getClass().getResource(filename);
		} catch (Exception e) {
		}
		return url;
	}

	public boolean load(String audiofile) {
		try {
			setFilename(audiofile);
			sample = AudioSystem.getAudioInputStream(getURL(filename));
			clip.open(sample);
			return true;
		} catch (IOException e) {
			return false;
		} catch (UnsupportedAudioFileException e) {
			return false;
		} catch (LineUnavailableException e) {
			return false;
		}
	}

	public void play() {
		if (!isLoaded())
			return;
		clip.setFramePosition(0);
		if (looping)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		else
			clip.loop(getRepeat());
	}

	public void stop() {
		clip.stop();
	}
}
