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
        XMLEngineBoot.bootEngine("test/assets/soundtestboot.xml");        
    } 
    private final Scanner scan = new Scanner(System.in);
    private long soundId;
    @Override
    public boolean init() {
        SoundCenter center=SoundCenter.getInstance();
        soundId = center.addSound("test/assets/sound1.wav");
        long midiId = center.addMidi("test/assets/midi1.mid");
        center.playMidi(midiId,true);
        center.playSound(soundId,3);
        return true;
    }
    @Override
    public boolean update(long time) {
        String word = scan.nextLine();
        switch (word) {
            case "exit":
                AppState.setValue("run","false");
                break;
            case "pause":
                SoundCenter.getInstance().setPauseMidi(true);
                break;
            case "resume":
                SoundCenter.getInstance().setPauseMidi(false);
                break;
            case "beat":
                SoundCenter.getInstance().playSound(soundId,1);
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public boolean shutdown() {
        Debug.log("Shutdown for SoundTest");
        return true;
    }    
}
