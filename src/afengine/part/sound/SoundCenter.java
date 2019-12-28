package afengine.part.sound;
import afengine.core.util.Vector;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.Sequence;

public class SoundCenter 
{	
	private final MidiPlayer midiPlayer;
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
		midiPlayer = new MidiPlayer();
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
		Sequence sequence = midiPlayer.getSequence(filepath);
		long id=sid++;
		midiMap.put(id,sequence);
		return id;
	}
	
	public void playMidi(long midiId,boolean loop)
	{
		Sequence sequence = midiMap.get(midiId);
		midiPlayer.play(sequence, loop);
	}
	public void playSound(long soundId,int count)
	{
                Sound sound = soundMap.get(soundId);
                soundManager.play(sound,count);		
	}
        public void stopMidi()
        {
            midiPlayer.stop();
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
		return midiPlayer.isPaused();
	}
	public boolean isPauseSound()
	{
		return soundManager.isPaused();
	}
	public void setPauseMidi(boolean pause)
	{
		midiPlayer.setPaused(pause);
	}
	public void setPauseSound(boolean pause)
	{
		soundManager.setPaused(pause);
	}
}
