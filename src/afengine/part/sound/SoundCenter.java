package afengine.part.sound;
import afengine.core.util.Vector;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.Sequence;

/**
 * main class for use to control sound,only for wav sound file,and midi music file.no more<br>
 * at first,if you want to play .wav sound,you should use addSound(file) to get the id for sound,
 * and then use playSound(id,looptime) to play any time if you like,<br>
 * if you want to play .mid music file,you should use addMidi(file) to get the id for midi,
 * and then use playMidi(id,loop) to play midi music if you like<br>
 * @author Albert Flex
 */
public class SoundCenter 
{	
	private final MidiPlayer midiManager;
	private final SoundPlayer soundManager;
	private final Map<Long,Sound> soundMap;
	private final Map<Long,Sequence> midiMap;
	private static long sid=0;
	
	private static SoundCenter manager;	
	public static SoundCenter getInstance()
	{
                 if(manager==null)
                    manager = new SoundCenter();
                return manager;
	}
	
	private SoundCenter()
	{
		midiManager = new MidiPlayer();
        soundManager = new SoundPlayer();
        soundMap = new TreeMap<>();
        midiMap =new TreeMap<>();
	}
	public long addSound(String stringfile)
	{
		Sound sound = soundManager.getSound(stringfile);
		long id = sid++;
		soundMap.put(id,sound);
		return id;	
	}
	public long addMidi(String filepath)
	{
		Sequence sequence = midiManager.getSequence(filepath);
		long id=sid++;
		midiMap.put(id,sequence);
		return id;
	}
	
	public void playMidi(long midiId,boolean loop)
	{
		Sequence sequence = midiMap.get(midiId);
		midiManager.play(sequence, loop);
	}
	public void playSound(long soundId,int count)
	{
                Sound sound = soundMap.get(soundId);
                soundManager.play(sound,count);		
	}
        public void stopMidi()
        {
            midiManager.stop();
        }
	public void playSoundWithEffect(long soundId,SoundFilter filer,boolean loop)
	{
		Sound sound = soundMap.get(soundId);
		soundManager.play(sound,filer,loop);				
	}
	public void playDistanceSound(long soundId,Vector listener,Vector shuter,int maxdistance,boolean loop)
	{
		Filter3d filter =
                new Filter3d(shuter,listener,maxdistance);
		playSoundWithEffect(soundId,filter,loop);
	}
	public boolean isPauseMidi()
	{
		return midiManager.isPaused();
	}
	public boolean isPauseSound()
	{
		return soundManager.isPaused();
	}
	public void setPauseMidi(boolean pause)
	{
		midiManager.setPaused(pause);
	}
	public void setPauseSound(boolean pause)
	{
		soundManager.setPaused(pause);
	}
}
