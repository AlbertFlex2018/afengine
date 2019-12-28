package afengine.part.sound;
import javax.sound.sampled.AudioFormat;

/**
    The Sound class is a container for sound samples. The sound
    samples are format-agnostic and are stored as a byte array.
*/
public class Sound {

    private byte[] samples;
    private AudioFormat format;

    /**
        Create a new Sound object with the specified byte array.
        The array is not copied.
    */
    public Sound(byte[] samples) {
        this.samples = samples;
    }


    /**
        Returns this Sound's objects samples as a byte array.
    */
    public byte[] getSamples() {
        return samples;
    }
    public void setFormat(AudioFormat format)
    {
    	this.format=format;
    }
    public AudioFormat getFormat()
    {
    	return format;
    }

}
