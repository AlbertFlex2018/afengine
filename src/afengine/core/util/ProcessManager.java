/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ProcessManager
{
    private final List<AbProcess> processList;
    
    private final List<AbProcess> waitforAdd;
    private final List<AbProcess> waitforRemove;
    public ProcessManager()
    {
        processList=new ArrayList<>();
        waitforAdd=new ArrayList<>();
        waitforRemove=new ArrayList<>();
    }
    public void attachProcess(AbProcess process)
    {
        if(!containsProcess(process.getProcessId()))
        {
            waitforAdd.add(process);
        }
        else
        {
            System.out.println("Add Process "+process.getProcessName()+" is failed.. already has this process...");            
        }
    }    
    public void killProcess(long  processId)
    {
        if(containsProcess(processId))
        {
            AbProcess process = findProcess(processId);
            process.setState(ProcessState.END_KILLED);
        }
    }
    public AbProcess findProcess(long processId)
    {
        Iterator<AbProcess>  processiter=processList.iterator();
        while(processiter.hasNext())
        {
            AbProcess process=processiter.next();
            if(process.getProcessId()==processId)
            {
                return process;
            }
        }        
        return null;
    }
    public boolean containsProcess(long processId)
    {
        Iterator<AbProcess>  processiter=processList.iterator();
        while(processiter.hasNext())
        {
            AbProcess process=processiter.next();
            if(process.getProcessId()==processId)
            {
                return true;
            }
        }        
        return false;
    }
    public void updateAllProcess(long time)
    {
        Iterator<AbProcess> processIter=processList.iterator();
        while(processIter.hasNext())
        {
            AbProcess process=processIter.next();
            handleProcess(process.getState(),process,time);
        }
        
        Iterator<AbProcess> waitremoveiter=waitforRemove.iterator();
        while(waitremoveiter.hasNext())
        {
            AbProcess process=waitremoveiter.next();
            processList.remove(process);
        }

        Iterator<AbProcess> waitadditer=waitforAdd.iterator();        
        while(waitadditer.hasNext())
        {
            AbProcess process=waitadditer.next();
            processList.add(process);
        }
        
        //clear for wait..
        waitforAdd.clear();
        waitforRemove.clear();
    }
    private void handleProcess(ProcessState state,AbProcess process,long time)
    {
        switch(state)
        {
            case UNSETUP:
                process.setUp();
                break;
            case SETUP:
                process.setState(ProcessState.RUNNING);
                break;
            case RUNNING:
                process.update(time);
                break;
            case END_SUCCESS:               
                process.success();
                process.setState(ProcessState.COULD_REMOVE);
                break;
            case END_FAILED:
                process.failed();
                process.setState(ProcessState.COULD_REMOVE);
                break;                
            case END_ABORT:
                process.abort();
                process.setState(ProcessState.COULD_REMOVE);
                break;                
            case END_KILLED:
                process.killed();
                process.setState(ProcessState.COULD_REMOVE);
                break;
            case EXCEPTION:
                System.out.println("Exception for Process "+process.getProcessName()+":"+process.getExceptionInfo());
                process.abort();
                process.setState(ProcessState.COULD_REMOVE);
                break;
            case COULD_REMOVE:
                waitforRemove.add(process);
                break;                
            default:break;
        }                
    }
}
