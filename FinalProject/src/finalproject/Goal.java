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
public class Goal implements PHYSICS{
    
    public Rectangle rect;
    public Color col1, col2;
    
    public Goal(int x, int y, Color c1, Color c2)
    {
        rect = new Rectangle(x,y,GOALDEPTH,GOALHEIGHT); // x of upper left, y of upper left, WIDTH, HEIGHT
        col1 = c1;
        col2 = c2;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(col2);
        g.fillRect(rect.x, rect.y, rect.width, rect.height );
        
        g.setColor(col1);
        g.fillRect(rect.x + 5, rect.y + 5, rect.width -10 , rect.height - 10);
        
        
    }
    
}
