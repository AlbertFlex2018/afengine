/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afengine.core.util;

/**
 * some general data and method for object who have position,ratation and some transform attributes.<br>
 * @author Albert Flex
 */
public class Transform{
    public static final int ROTATE_X=0;
    public static final int ROTATE_Y=1;
    public static final int ROTATE_Z=2;

    public final Vector position;
    public final Vector rotation;
    public final Vector scalation;
    public final Vector anchor;
    
    public Transform(Vector position, Vector rotation, Vector scalation, Vector anchor) {
        this.position = position;
        this.rotation = rotation;
        this.scalation = scalation;
        this.anchor = anchor;
    }
    public Transform(Vector position,Vector rotation,Vector scalation){
        this(position,rotation,scalation,new Vector(0,0,0,0));
    }
    public Transform(Vector position,Vector rotation){
        this(position,rotation,new Vector(1.0,1.0,1.0,0));
    }    
    public Transform(Vector position){
        this(position,new Vector(0,0,0,0));
    }

    public Transform() {
        this(new Vector(0,0,0,0));
    }
    
    public void scale(double sx,double sy,double sz){
        scalation.setX(scalation.getX()*sx);
        scalation.setY(scalation.getY()*sy);
        scalation.setZ(scalation.getZ()*sz);
    }
    public void scaleTo(double sx,double sy,double sz){
        scalation.setX(sx);
        scalation.setY(sy);
        scalation.setZ(sz);        
    }
    public void rotate(int rotateType,double rotate){
        switch(rotateType){
            case ROTATE_X:
                rotation.setX(rotation.getX()+rotate);
                break;
            case ROTATE_Y:
                rotation.setY(rotation.getY()+rotate);
                break;
            case ROTATE_Z:
               rotation.setZ(rotation.getZ()+rotate);
                break;
            default:
                System.out.println("rotate Type is not found!!");
                break;
        }
    }
    public void rotateTo(int rotateType,double rotate){
        switch(rotateType){
            case ROTATE_X:
                rotation.setX(rotate);
                break;
            case ROTATE_Y:
                rotation.setY(rotate);
                break;
            case ROTATE_Z:
                rotation.setZ(rotate);
                break;
            default:
                System.out.println("rotate Type is not found!!");
                break;
        }        
    }
    public void translate(double x,double y,double z){
        position.setX(position.getX()+x);
        position.setY(position.getY()+y);
        position.setZ(position.getZ()+z);
    }
    public void translateTo(double x,double y,double z){
        position.setX(x);
        position.setY(y);
        position.setZ(z);        
    }    
}
