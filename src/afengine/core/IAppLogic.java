/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core;

/**
 * The Logic for the App to Run.<br>
 * AppLogic has its lifespan to Manager the Logic for App Run.<br>
 * Instance a AbApp app, and Instance one IAppLogic logic.<br>
 * then use app.run(logic) to run logic for app.<br>
 * @see AbApp
 * @author Albert Flex
 */
public interface IAppLogic{

    public boolean init();
    public boolean update(long time);
    public boolean shutdown();    
}
