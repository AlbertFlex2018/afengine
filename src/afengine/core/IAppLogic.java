/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

/**
 *
 * @author Admin
 */
public interface IAppLogic{

    public boolean init();
    public boolean update(long time);
    public boolean shutdown();    
}
