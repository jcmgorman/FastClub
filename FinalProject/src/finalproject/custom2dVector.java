/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 *
 * @author chomp
 */
public class custom2dVector {
    public double x;
    public double y;
    
    public custom2dVector()
    {
        x = 0;
        y = 0;
    }
    
    public custom2dVector(double a, double b)
    {
        x = a;
        y = b;
    }
    
    
    public custom2dVector dotProduct(custom2dVector other)
    {
     custom2dVector temp = new custom2dVector();
     temp.x = this.x * other.x;
     temp.y = this.y * other.y;
     return temp;
    }
    
    public custom2dVector scale(double scalar)
    {
       custom2dVector temp = new custom2dVector();
       temp.x = x;
       temp.y = y;
       
       
       temp.x *= scalar;
       temp.y *= scalar;
       return temp;
    }
    
    public custom2dVector subtract(custom2dVector other)
    {
        custom2dVector temp = new custom2dVector();
        temp.x = this.x - other.x;
        temp.y = this.y - other.y;
        return temp;
    }
    
    public custom2dVector add(custom2dVector other)
    {
        custom2dVector temp = new custom2dVector();
        temp.x = this.x + other.x;
        temp.y = this.y + other.y;
        return temp;
    }
    
    public double magnitude()
    {
        double ret;
        ret = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return ret;
    }
    
    @Override
    public String toString()
    {
        return("<" + x + "," + y + ">");
    }
}
