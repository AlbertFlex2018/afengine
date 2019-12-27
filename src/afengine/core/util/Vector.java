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
public class Vector {
    
    private double x,y,z,a;
    private double lengthcache;
    private boolean cachedirty;

    public Vector(double x,double y,double z,double a)
    {
        this.x=x;
        this.y=y;
        this.z=z;
        this.a=a;        
        cachedirty=true;
    }
    public Vector(double x,double y,double z)
    {
        this(x,y,z,0f);
    }
    public Vector()
    {
        this(0f,0f,0f,0f);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        
        cachedirty=true;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        cachedirty=true;        
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        cachedirty=true;        
        this.z = z;
    }        

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }
        
    public double getLength()
    {
        if(cachedirty)
        {
            lengthcache=Math.sqrt(x*x+y*y+z*z);
            cachedirty=false;            
        }
        
        return lengthcache;
    }
    
    public Vector addVector(Vector other)
    {
        double vx = this.x+other.x;
        double vy = this.y+other.y;
        double vz = this.z+other.z;
        return new Vector(vx,vy,vz,0f);
    }
    
    public Vector delVector(Vector other)
    {
        double vx = this.x-other.x;
        double vy = this.y-other.y;
        double vz = this.z-other.z;
        return new Vector(vx,vy,vz,0f);
    }   
    
    
    public void dotNumber(double number)
    {
        x*=number;
        y*=number;
        z*=number;
        cachedirty=true;
    }
    
    public double dotVector(Vector other)
    {
        double result=x*other.x+y*other.y+z*other.z;
        return result;
    }
    
    public Vector crossVector(Vector other)
    {
        double vx = y*other.z-other.y*z;
        double vy = other.x*z -x*other.z;
        double vz = x*other.y-other.x*y;
        return new Vector(vx,vy,vz);
    }
    
    public void normal()
    {
        if(lengthcache==0)
        {
            System.out.println("normal failed, cause divide by 0");
            return;
        }
        else
        {
            x/=lengthcache;
            y/=lengthcache;
            z/=lengthcache;
            cachedirty=true;
        }
    }
    
    public double cosAngleWithVector(Vector other)
    {
        double dots = dotVector(other);
        double length1=getLength();
        double length2=other.getLength();
        return dots/(length1*length2);
    }
}
