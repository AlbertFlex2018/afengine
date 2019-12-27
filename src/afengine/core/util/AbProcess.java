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
/**
 *
 * @author Admin
 */
public abstract class AbProcess{

    private final long processId;
    private final String processName;

    private ProcessState state;
    private String exceptionInfo;
    
    public AbProcess(long processId,String processName)
    {
        this.processId=processId;
        this.processName=processName;
        state=ProcessState.UNSETUP;
    }

    public long getProcessId() {
        return processId;
    }

    public String getProcessName() {
        return processName;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }
    
    
    public abstract boolean init();
    public abstract boolean update(long time);
    
    public void setUp()
    {
        if(state==ProcessState.UNSETUP)
        {
            if(init())
            {
                System.out.println("Init process "+this.processName+" failed!!");
                state=ProcessState.EXCEPTION;
                return;
            }
            state=ProcessState.SETUP;
        }        
    }
    public void success()
    {
        state=ProcessState.END_SUCCESS;
    }
    public void failed()
    {
        state=ProcessState.END_FAILED;
    }
    public void abort()
    {
        state=ProcessState.END_ABORT;
    }    

    public void exception(String info)
    {
        state=ProcessState.EXCEPTION;
        exceptionInfo=info;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    
    public abstract void successEnd();
    public abstract void failedEnd();
    public abstract void abortEnd();
    public abstract void killed();
}
