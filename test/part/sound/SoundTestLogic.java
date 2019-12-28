package part.sound;

import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import afengine.part.sound.SoundCenter;
import java.util.Scanner;

/**
 *
 * @author Albert Flex
 */
public class SoundTestLogic implements IAppLogic{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("assets/soundtestboot.xml");        
    } 
    private final Scanner scan = new Scanner(System.in);
    @Override
    public boolean init() {
        SoundCenter center=SoundCenter.getInstance();
        long soundId = center.addSound("assets/sound1.wav");
        long midiId = center.addMidi("assets/midi1.mid");
        center.playMidi(midiId,true);
        center.playSound(soundId,3);
        return true;
    }
    @Override
    public boolean update(long time) {
        String word = scan.nextLine();
        if(word.equals("exit"))
            AppState.setValue("run","false");
        return true;
    }
    @Override
    public boolean shutdown() {
        Debug.log("Shutdown for SoundTest");
        return true;
    }    
}
