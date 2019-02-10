/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author chomp
 */
public class Ball implements PHYSICS
{
    public double xpos, ypos, xvel, yvel;
    public Rectangle rect;

    public Ball()
    {
        xpos = WIDTH/2;
        ypos = HEIGHT/2;
        rect = new Rectangle((int)xpos- BALLSIZE/2, (int)ypos - BALLSIZE/2, BALLSIZE, BALLSIZE);
        
    }
    
    public void move()
    {
        xpos += xvel;
        ypos += yvel;
        xvel *= DECELLMOD;
        yvel *= DECELLMOD;
        
        if (Math.abs(xvel) < .1){xvel = 0;}
        if (Math.abs(yvel) < .1){yvel = 0;}
        
        
        double totalvel = (Math.sqrt((Math.pow(xvel,2)) + (Math.pow(yvel,2))));
        
        if (totalvel > MAXSPEED)
        {
            xvel *= .8;
            yvel *= .8;
        }
        //System.out.println("xpos: " + xpos + " ypos: " + ypos);
        
        
        //Rectangle field = new Rectangle((PHYSICS.WIDTH-FIELDWIDTH)/2, (PHYSICS.HEIGHT-FIELDHEIGHT)/2, FIELDWIDTH, FIELDHEIGHT); // DRAWS THE FIELD
        
        
        // TOP INSIDE GOAL
        if ((ypos < (HEIGHT-GOALHEIGHT)/2 + BALLSIZE/2) && (xpos > WIDTH - (WIDTH-FIELDWIDTH)/2 || xpos < (WIDTH-FIELDWIDTH)/2))
        {
            yvel = yvel*-.8;
             
            ypos = (HEIGHT-GOALHEIGHT)/2 + BALLSIZE/2;
        }
        
        
        
        // BOTTOM INSIDE GOAL
        if ((ypos > HEIGHT - ((HEIGHT-GOALHEIGHT)/2 + BALLSIZE/2)) && (xpos > WIDTH - (WIDTH-FIELDWIDTH)/2 || xpos < (WIDTH-FIELDWIDTH)/2))
        {
            yvel = yvel*-.8;
             
            ypos = HEIGHT - ((HEIGHT-GOALHEIGHT)/2 + BALLSIZE/2);
        }
        
        
        
      
        if ((xpos > WIDTH - ((PHYSICS.WIDTH-FIELDWIDTH)/2) - BALLSIZE/2) && ((ypos < (HEIGHT-GOALHEIGHT)/2) || (ypos > (HEIGHT-(HEIGHT-GOALHEIGHT)/2)))) //RIGHT     SECOND STATEMENT AFTER && SHOULD LET IT GO IN THE GOAL
        {
            xvel = xvel*-.8;
            //System.out.println("OUT OF WIDTH");
            xpos = WIDTH - ((PHYSICS.WIDTH-FIELDWIDTH)/2) - BALLSIZE/2;
        }
        
        
        if (ypos > HEIGHT - ((PHYSICS.HEIGHT-FIELDHEIGHT)/2) - BALLSIZE/2) // BOTTOM
        
        {
            
            yvel = yvel*-.8;
            //System.out.println("OUT OF HEIGHT");
            ypos = HEIGHT - ((PHYSICS.HEIGHT-FIELDHEIGHT)/2) - BALLSIZE/2;
        }
        
        //LEFT AND RIGHT IF STATEMENTS NEED SOME WORK
        if (xpos < (PHYSICS.WIDTH-FIELDWIDTH)/2 + BALLSIZE/2 && ((ypos < (HEIGHT-GOALHEIGHT)/2) || (ypos > (HEIGHT-(HEIGHT-GOALHEIGHT)/2)))) // LEFT SECOND STATEMENT AFTER THE && SHOULD LET IT GO IN THE GOAL
        {
            xvel = xvel*-.8;
            xpos = (PHYSICS.WIDTH-FIELDWIDTH)/2 + BALLSIZE/2;
        }
        
        
        if (ypos < (PHYSICS.HEIGHT-FIELDHEIGHT)/2 + BALLSIZE/2) //TOP
        {
            yvel = yvel*-.8;
            ypos = (PHYSICS.HEIGHT-FIELDHEIGHT)/2 + BALLSIZE/2;
        }
        
        rect.x = (int) (xpos - BALLSIZE/2);
        rect.y = (int) (ypos - BALLSIZE/2);
        
        
    }
    
    public void draw(Graphics g)
    {
        rect.x = (int) (xpos - BALLSIZE/2);
        rect.y = (int) (ypos - BALLSIZE/2);
        g.setColor(Color.BLACK);
        //g.fillOval((int)xpos- BALLSIZE/2, (int)ypos - BALLSIZE/2, BALLSIZE, BALLSIZE);
        g.fillOval(rect.x,rect.y,rect.width,rect.height);
        
    }

}
