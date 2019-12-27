/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

/**
 *
 * @author Admin
 */
public enum ProcessState {
    UNSETUP,    
    SETUP,

    RUNNING,
    PAUSED,
    
    EXCEPTION,
            
    END_SUCCESS,
    END_FAILED,
    END_ABORT,
    END_KILLED,
    
    COULD_REMOVE,        
}
