/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import afengine.core.AbPartSupport;
import afengine.core.AppState;
import afengine.core.IAppLogic;
import afengine.core.util.Debug;
import afengine.core.util.XMLEngineBoot;
import java.util.Scanner;
import org.dom4j.Element;
import afengine.core.util.IXMLPartBoot;

/**
 *
 * @author Albert Flex
 */
public class XMLEngineBootTest{
    public static void main(String[] args) {
        XMLEngineBoot.bootEngine("assets/boot1.xml");        
//        System.out.println(PartBoot1.class.getName());
//        System.out.println(PartBoot2.class.getName());
//        System.out.println(PartBoot3.class.getName());
//        System.out.println(AppLogic.class.getName());
//        System.out.println(ServiceAppBoot.class.getName());
    }
    public static class PartBoot1 implements IXMLPartBoot{
        @Override
        public AbPartSupport bootPart(Element element) {
            System.out.println("Part Boot - 1");
            return null;
        }
    }
    public static class PartBoot2 implements IXMLPartBoot{
        @Override
        public AbPartSupport bootPart(Element element) {
            System.out.println("Part Boot - 2");
            return null;
        }        
    }
    public static class PartBoot3 implements IXMLPartBoot{
        @Override
        public AbPartSupport bootPart(Element element) {
            System.out.println("Part Boot - 3");
            return null;
        }        
    }
    public static class AppLogic implements IAppLogic{

        Scanner scan = new Scanner(System.in);
        @Override
        public boolean init() {
            return true;
        }
        @Override
        public boolean update(long time){
            String cmd = scan.nextLine();
            if(cmd.equals("exit")){
                AppState.setValue("run","false");
            }else Debug.log(cmd);
            return true;        
        }
        @Override
        public boolean shutdown() {
            return true;
        }
    }
}
