package afengine.part.uiinput;

import afengine.core.util.Debug;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class UIInputCenter{
    private static UIInputCenter center=null;
    public static UIInputCenter getInstance(){
        if(center==null)
            center=new UIInputCenter();
        
        return center;
    }
    
    private final List<UIFace> faceList; //保存的所有界面列表
    private final List<UIFace> activedfaceList;//以激活的界面列表
    private UIFace popupFace;//弹出的界面
    
    private final Map<Long,List<InputServlet>> preServlets;//先界面输入过滤列表
    private final Map<Long,List<InputServlet>> afterServlets;//后界面输入过滤列表    
    
    private UIInputCenter(){
        faceList=new LinkedList<>();
        activedfaceList=new LinkedList<>();
        preServlets=new HashMap<>();
        afterServlets=new HashMap<>();        
    }
    public List<UIFace> getFaceList() {
        return faceList;
    }
    public List<UIFace> getActivedfaceList() {
        return activedfaceList;
    }
    public UIFace getPopupFace() {
        return popupFace;
    }
    public Map<Long, List<InputServlet>> getPreServlets() {
        return preServlets;
    }
    public Map<Long, List<InputServlet>> getAfterServlets() {
        return afterServlets;
    }
    public boolean containsFaceInAll(String faceName){
        return faceList.stream().anyMatch((face) -> (face.getFaceName().equals(faceName)));
    }
    public boolean containsFaceInActived(String faceName){
        return activedfaceList.stream().anyMatch((face) -> (face.getFaceName().equals(faceName)));
    }
    public UIFace findFaceInAll(String faceName){
        for(UIFace face:faceList){
            if(face.getFaceName().equals(faceName))
                return face;
        }
        return null;
    }
    public UIFace findFaceInActived(String faceName){
        for(UIFace face:activedfaceList){
            if(face.getFaceName().equals(faceName))
                return face;
        }
        return null;
    }
    public void addFaceInAll(UIFace face){
        if(containsFaceInAll(face.getFaceName())){
            Debug.log("already has a face named"+face.getFaceName());            
            return;
        }
        faceList.add(face);
    }
    public void addFaceInActived(UIFace face){
        if(containsFaceInActived(face.getFaceName())){
            Debug.log("already has a face named"+face.getFaceName());            
            return;
        }
        activedfaceList.add(face);
    }   
    //将会保存中的界面，和激活列表里面相应的界面
    public void removeFaceInAll(String faceName){
        if(!containsFaceInAll(faceName)){
            Debug.log("do not has face for "+faceName);
            return;
        }
        
        UIFace face=findFaceInAll(faceName);
        faceList.remove(face);
        activedfaceList.remove(face);
    }
    public void removeFaceInActived(String faceName){
        if(!containsFaceInActived(faceName)){
            Debug.log("actived faces:do not has face for "+faceName);
            return;
        }
        
        UIFace face=findFaceInAll(faceName);
        activedfaceList.remove(face);
    }
    public void activeFace(String faceName){
        if(containsFaceInActived(faceName)){
            Debug.log("already actived face for "+faceName);
            return;
        }
        
        UIFace face=findFaceInAll(faceName);
        if(face==null){
            Debug.log("there is no face add for "+faceName);
        }else{
            activedfaceList.add(face);
        }
    }
    public void hideFace(String faceName){
        if(!containsFaceInActived(faceName)){
            Debug.log("has not actived face for "+faceName);
            return;
        }        
        
        UIFace face=findFaceInActived(faceName);
        activedfaceList.remove(face);
    }
    public void showPopupFace(UIFace face){
        if(popupFace!=null){
            Debug.log("poopup is not unlock,please unlock then show.");
            return;
        }

        popupFace=face;
    }
    public void removePopupFace(){
        if(popupFace==null){
            Debug.log("poopup is  unlock already,do not remove again.");
            return;            
        }
        
        popupFace=null;
    }
    
    private void addServlet(boolean pre,Long type,InputServlet servlet){
        List<InputServlet> servletList;
        if(pre){
            servletList=preServlets.get(type);
            if(servletList==null){
                servletList=new LinkedList<>();
                preServlets.put(type, servletList);
            }
            servletList.add(servlet);
        }
        else{
            servletList=afterServlets.get(type);
            if(servletList==null){
                servletList=new LinkedList<>();
                afterServlets.put(type, servletList);
            }
            servletList.add(servlet);            
        }
    }
    private void removeServlet(boolean pre,Long type,String servletName){
        List<InputServlet> servletList;
        if(pre){
            servletList=preServlets.get(type);
        }
        else{
            servletList=afterServlets.get(type);
        }        
        if (servletList == null) {
            Debug.log("do not has type map for " + type);
            return;
        }
        InputServlet dest = null;
        for (InputServlet ser : servletList) {
            if (ser.servletName.equals(servletName)) {
                dest = ser;
                break;
            }
        }
        if (dest != null) {
            servletList.remove(dest);
        } else {
            Debug.log("do not find preinputserlet for " + servletName);
        }
    }
    private InputServlet getServlet(boolean pre,Long type,String name){
        List<InputServlet> servletList;
        if(pre){
            servletList=preServlets.get(type);
        }
        else{
            servletList=afterServlets.get(type);
        }
        if (servletList == null) {
            Debug.log("do not has type map for " + type);
            return null;
        } else {
            for (InputServlet ser : servletList) {
                if (ser.servletName.equals(name)) {
                    return ser;
                }
            }
        }        
        return null;
    }    
    public InputServlet findPreServlet(Long type,String servletName){
        return getServlet(true,type,servletName);
    }
    public InputServlet findAfterServlet(Long type,String servletName){
        return getServlet(false,type,servletName);        
    }
    public void addPreServlet(Long type,InputServlet servlet){
        addServlet(true,type,servlet);
    }
    public void addAfterServlet(Long type,InputServlet servlet){
        addServlet(false,type,servlet);
    }
    public void removePreServlet(Long type,String servletName){
        removeServlet(true,type,servletName);
    }
    public void removeAfterServlet(Long type,String servletName){
        removeServlet(false,type,servletName);        
    }    
}
