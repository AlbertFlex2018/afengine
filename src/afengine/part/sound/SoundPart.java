/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.part.sound;

import afengine.core.AbPartSupport;

/**
 * Part for Sound.<br>
 * @see AbPartSupport
 * @see SoundCenter
 * @author Albert Flex
 */
public class SoundPart extends AbPartSupport{
    public static final String PART_NAME="SoundPart";
    
    private SoundCenter manager;
    public SoundPart() {
        super(SoundPart.PART_NAME);
    }
    
    @Override
    public boolean init(){
        manager=SoundCenter.getInstance();
        return true;
    }

    @Override
    public boolean update(long time) {
        return true;
    }

    @Override
    public boolean shutdown() {
        manager.stopMidi();        
        return true;
    }    
}
