package afengine.part.sound;
import afengine.core.util.ThreadPool;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer 
{
    private boolean ifpause;
    private int loopCount;
    private int loopnow;
    private ThreadPool pool;

    private Object pausedLock;
    public SoundPlayer()
    {
    	ifpause=false;
    	loopCount=loopnow=1;
    	pool=new ThreadPool(1000);
        pausedLock = new Object();
    	
    }
    
    public static Sound getSound(String filename)
    {
    	Sound sound=null;
        try {
            // open the audio input stream
            AudioInputStream stream =
                AudioSystem.getAudioInputStream(
                new File(filename));

            AudioFormat format = stream.getFormat();

            // get the audio samples
            
            sound = new Sound(getSamples(stream));
            sound.setFormat(format);
        }
        catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return sound;
    }

    private static byte[] getSamples(AudioInputStream audioStream) {
        // get the number of bytes to read
        int length = (int)(audioStream.getFrameLength() *
            audioStream.getFormat().getFrameSize());

        // read the entire stream
        byte[] samples = new byte[length];
        DataInputStream is = new DataInputStream(audioStream);
        try {
            is.readFully(samples);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // return the samples
        return samples;
    }

    
    public int getLoopCount() {return loopCount;}
    public void setLoopCount(int count) {this.loopCount=loopnow=count;}
    

    protected class SoundPlayerIner implements Runnable {

        private Sound sound;
        private int count;

        public SoundPlayerIner(Sound sound,int count) {
            this.sound=sound;
            this.count=count;
        }

        public void run() 
        {
            try {
                while(count-->0)
                {
        	        InputStream source = new ByteArrayInputStream(sound.getSamples());
            		// TODO Auto-generated method stub
                    // use a short, 100ms (1/10th sec) buffer for real-time
                    // change to the sound stream
                    int bufferSize = sound.getFormat().getFrameSize() *
                        Math.round( sound.getFormat().getSampleRate() / 10);
                    byte[] buffer = new byte[bufferSize];

                    // create a line to play to
                    SourceDataLine line;
                    try {
                        DataLine.Info info =
                            new DataLine.Info(SourceDataLine.class,  sound.getFormat());
                        line = (SourceDataLine)AudioSystem.getLine(info);
                        line.open( sound.getFormat(), bufferSize);
                    }
                    catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                        return;
                    }

                    // start the line
                    line.start();
    	
                int numBytesRead = 0;
               	while (numBytesRead != -1)
                {
                    // if paused, wait until unpaused
                    synchronized (pausedLock) {
                        if (ifpause) {
                            try {
                                pausedLock.wait();
                            }
                            catch (InterruptedException ex) {
                                return;
                            }
                        }
                    }
                    numBytesRead =
                        source.read(buffer, 0, buffer.length);
                    if (numBytesRead != -1) {
                       line.write(buffer, 0, numBytesRead);
                    }
                }
                line.drain();
                line.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void play(Sound sound,int count) 
    {
    	pool.runTask(new SoundPlayerIner(sound,count));
    }
        
    public void play(Sound sound,SoundFilter filter,
            boolean loop)
   {
    	pool.runTask(()->
    	{    		
            InputStream is=null;
            if (sound != null) {
                if (loop) {
                    is = new LoopingByteInputStream(
                        sound.getSamples());
                }
                else {
                    is = new ByteArrayInputStream(sound.getSamples());
                }
            }
            if (is!= null) {
                if (filter != null) {
                    is = new FilteredSoundStream(is, filter);
                }
            	SoundPlayer22 player = new SoundPlayer22(is,sound.getFormat());
                player.run();
            }            
    	});
   }    

    public void setPaused(boolean paused) {
        if (this.ifpause != paused) {
            synchronized (pausedLock) {
                this.ifpause = paused;
                if (!paused) {
                    // restart sounds
                    pausedLock.notifyAll();
                }
            }
        }
    }    

    /**
        Returns the paused state.
    */
    public boolean isPaused() {
        return ifpause;
    }
    protected class SoundPlayer22 implements Runnable {

        private InputStream source;
        private AudioFormat format;        

        public SoundPlayer22(InputStream source,AudioFormat format) 
        {
            this.source = source;
            this.format=format;            
        }

        public void run() 
        {

            int bufferSize = format.getFrameSize() *
                    Math.round(format.getSampleRate() / 10);
                byte[] buffer = new byte[bufferSize];

                // create a line to play to
                SourceDataLine line;
                try {
                    DataLine.Info info =
                        new DataLine.Info(SourceDataLine.class, format);
                    line = (SourceDataLine)AudioSystem.getLine(info);
                    line.open(format, bufferSize);
                }
                catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    return;
                }

                // start the line
                line.start();

                // copy data to the line
                try {
                    int numBytesRead = 0;
                    while (numBytesRead != -1) {
                        synchronized (pausedLock) {
                            if (ifpause){
                                try {
                                    pausedLock.wait();
                                }
                                catch (InterruptedException ex) {
                                    return;
                                }
                            }
                        }
                        numBytesRead =
                            source.read(buffer, 0, buffer.length);
                        if (numBytesRead != -1) {
                           line.write(buffer, 0, numBytesRead);
                        }
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                // wait until all data is played, then close the line
                line.drain();
                line.close();
        }
    }    
}
