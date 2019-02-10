/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import static finalproject.PHYSICS.FIELDHEIGHT;
import static finalproject.PHYSICS.FIELDWIDTH;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import sun.audio.*;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Soccer implements PHYSICS, ActionListener, KeyListener
{
    private boolean winner1 = false;
    private int countdown = 0;
    private boolean winner2 = false;  
    private JFrame frame;
    private DrawingPanel panel;
    private Gamestate status;
    private Timer gametime;
    private Car p1;
    private Ball b;
    private int score1 = 0;
    private int score2 = 0;
    private int carcollisionctr = 0;
    private int ballcollisionctr = 0;
    private int menuCtr = 0;
    private int p1ColorCtr = 0;
    private int p2ColorCtr = 0;
    private Car p2;
    private Goal g2;
    private Goal g1;
    private boolean[] keysDown;
    private Timer Pause;
    private Rectangle PlayBut = new Rectangle(660,152,30,60);
    private Rectangle ExitBut = new Rectangle(660,252,30,60);
    private Rectangle InstBut = new Rectangle(660,352,30,60);
    private Rectangle ColorPi = new Rectangle(660,452,30,60);
    private Rectangle P1ColrCur = new Rectangle(660,452,30,60);
    private Rectangle P2ColrCur = new Rectangle(660,452,30,60);
    private ImageIcon InstPic;
    private ArrayList<Color> p1color = new ArrayList<>();
    private ArrayList<Color> p2color = new ArrayList<>();
   
    private InputStream in;   
    private AudioStream collision;
    public Soccer() throws FileNotFoundException, IOException
    {
        frame = new JFrame("Fast Club"); // Outside of window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Tells thing to close
        panel = new DrawingPanel();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));    // Inside the frame. All the pictures
        frame.getContentPane().add(panel);
        
        InstPic = new ImageIcon("FastClubInst.png");
        
        status = Gamestate.menu;
        p1 = new Car(400, HEIGHT/2, "Player 1", Color.RED); //xpos ypos name
        p2 = new Car(WIDTH-400, HEIGHT/2, "Player 2", Color.BLUE);
        b = new Ball();
        //System.out.println("g1 xpos " + ((PHYSICS.WIDTH-FIELDWIDTH)/2 - GOALDEPTH));
        g1 = new Goal((PHYSICS.WIDTH-FIELDWIDTH)/2 - GOALDEPTH, PHYSICS.HEIGHT/2 - GOALHEIGHT/2, Color.YELLOW, Color.RED); //P1 DEFENDS G1
        // Goal(UPPER LEFT X, UPPER LEFT Y, GOAL INTERIOR, GOAL EXTERIOR)
        g2 = new Goal(PHYSICS.WIDTH-(PHYSICS.WIDTH-FIELDWIDTH)/2, PHYSICS.HEIGHT/2 -GOALHEIGHT/2, Color.CYAN, Color.BLUE); //P2 DEFENDS G2
        gametime = new Timer(8, this);
        keysDown = new boolean[8]; //8 possible different inpts
        for (int x = 0; x < keysDown.length; x++)
        {
            keysDown[x] = false;
        }
        Pause = new Timer(1000, this);
        /**
         *     W    A   S   D   UP  LEFT    DOWN    RIGHT
         *     0    1   2   3   4   5       6       7
         */
        
        // Audio
       
        
       
        
        
        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(this);
        
        gametime.start();
        frame.pack();
        frame.setVisible(true);
        
        // Put colors into array list.
        p1addcolor();
        p2addcolor();

    }
    public void p1addcolor() throws FileNotFoundException
    {
        Color temp = new Color(0,0,0);
        FileReader inputfile = new FileReader("p1color.txt");
        Scanner fin = new Scanner (inputfile);
        while(fin.hasNext())
        {
            temp = new Color((int)fin.nextInt(), (int)fin.nextInt(), (int)fin.nextInt());
            p1color.add(temp);
        }
    }
    public void p2addcolor() throws FileNotFoundException      
    {
        Color temp = new Color(0,0,0);
        FileReader inputfile = new FileReader("p2color.txt");
        Scanner fin = new Scanner (inputfile);
        while(fin.hasNext())
        {
            temp = new Color((int)fin.nextInt(), (int)fin.nextInt(), (int)fin.nextInt());
            p2color.add(temp);
        }
    }
   
    @Override
    public void actionPerformed(ActionEvent ae)  
    {//performed once per CYCLE
        
        if (status == Gamestate.play){
            
            if (g1.rect.contains(b.rect))
            {                          
                System.out.println("GOAL PLAYER 2");
                //b.xpos = WIDTH/2;
                //b.ypos = HEIGHT/2;
                score2++;
                System.out.println("PLAYER 1: " + score1);
                System.out.println("PLAYER 2: " + score2);
                p1.xvel = 0;
                p1.yvel = 0;
                p2.xvel = 0;
                p2.yvel = 0;
                b.xvel = 0;
                b.yvel = 0;
                p1.xpos = 400;
                p1.ypos = HEIGHT/2;
                p2.xpos = WIDTH - 400;
                p2.ypos = HEIGHT/2;
                b.ypos = HEIGHT/2;
                b.xpos = WIDTH/2 - 100;
                if (score2 != 3)
                {
                    status = Gamestate.countdown;
                    countdown = 3000;
                }
                
            }

            if (g2.rect.contains(b.rect))

            {         

                System.out.println("GOAL PLAYER 1");
                //b.xpos = WIDTH/2;
                //b.ypos = HEIGHT/2;
                score1++;
                System.out.println("PLAYER 1: " + score1);
                System.out.println("PLAYER 2: " + score2);
                p1.xvel = 0;
                p1.yvel = 0;
                p2.xvel = 0;
                p2.yvel = 0;
                b.xvel = 0;
                b.yvel = 0;
                p1.xpos = 400;
                p1.ypos = HEIGHT/2;
                p2.xpos = WIDTH - 400;
                p2.ypos = HEIGHT/2;
                b.ypos = HEIGHT/2; 
                b.xpos = WIDTH/2 + 100;
                
                if (score1 != 3)
                {
                    status = Gamestate.countdown;
                    countdown = 3000;
                }
               
            }

            if(score1 == 3)
            {
                winner1 = true;
                System.out.println("PLAYER 1 WINS");
                status = Gamestate.winner;
                score1 = 0;
                score2 = 0;

            }
            else if (score2 == 3)
            {
                winner2 = true;
                System.out.println("PLAYER 2 WINS");
                status = Gamestate.winner;
                score2 = 0;
                score1 = 0;
            }


            if (status == Gamestate.play)
            {
            if (keysDown[0]){p1.yvel -= JUKE;}
            if (keysDown[1]){p1.xvel -= JUKE;}
            if (keysDown[2]){p1.yvel += JUKE;}
            if (keysDown[3]){p1.xvel += JUKE;}

            if (keysDown[4]){p2.yvel -= JUKE;}
            if (keysDown[5]){p2.xvel -= JUKE;}
            if (keysDown[6]){p2.yvel += JUKE;}
            if (keysDown[7]){p2.xvel += JUKE;}
            
                p1.move();
                p2.move();
                b.move();

                try {
                    collide();
                } catch (IOException ex) {
                    Logger.getLogger(Soccer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }  
        }
        //System.out.println("Gaze into the IRIS");
        
        //System.out.println("p1 xvel: " + p1.xvel + " p1 yvel: " + p1.yvel + " p1 total vel: "  +  ((Math.sqrt((Math.pow(p1.xvel,2) + Math.pow(p1.yvel,2))))));
        panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        
        if(status == Gamestate.menu)
        {
            if(e.getKeyCode() == KeyEvent.VK_UP)
            {
                menuCtr--;
                if(menuCtr < 0) menuCtr = 3;
                panel.repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                menuCtr++;
                if(menuCtr > 3) menuCtr = 0;
                panel.repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                if(menuCtr == 0)
                {
                    status = Gamestate.play;
                    if(score1 == 0 && score2 == 0)
                    {
                        b.xpos = WIDTH/2;
                        b.ypos = HEIGHT/2;
                    }
                    panel.repaint();
                }
                if(menuCtr == 1)
                {
                    System.exit(0);
                }
                if(menuCtr == 2)
                {
                    status = Gamestate.instructions;
                    panel.repaint();
                }
                if(menuCtr == 3)
                {
                    status = Gamestate.colorpick;
                    panel.repaint();
                }
            }
        }
        if(status == Gamestate.play)
        {
            if (e.getKeyCode() == KeyEvent.VK_W){keysDown[0] = true;}
            if (e.getKeyCode() == KeyEvent.VK_A){keysDown[1] = true;}
            if (e.getKeyCode() == KeyEvent.VK_S){keysDown[2] = true;}
            if (e.getKeyCode() == KeyEvent.VK_D){keysDown[3] = true;}

            if (e.getKeyCode() == KeyEvent.VK_UP){keysDown[4] = true;}
            if (e.getKeyCode() == KeyEvent.VK_LEFT){keysDown[5] = true;}
            if (e.getKeyCode() == KeyEvent.VK_DOWN){keysDown[6] = true;}
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){keysDown[7] = true;}  
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {status = Gamestate.menu;}
            
        }
        if(status == Gamestate.instructions)
        {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {status = Gamestate.menu;}
        }
        if(status == Gamestate.winner)
        {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {status = Gamestate.menu;}
        }
        if(status == Gamestate.colorpick)
        {
            if(e.getKeyCode() == KeyEvent.VK_D) 
            {
                p1ColorCtr++;
                if(p1ColorCtr > p1color.size() - 1 ) p1ColorCtr = 0;
            }        
            if(e.getKeyCode() == KeyEvent.VK_A) 
            {
                p1ColorCtr--;
                if(p1ColorCtr < 0) p1ColorCtr = p1color.size() - 1;
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
            {
                p2ColorCtr++;
                if(p2ColorCtr > p2color.size() - 1 ) p2ColorCtr = 0;
            }        
            if(e.getKeyCode() == KeyEvent.VK_LEFT) 
            {
                p2ColorCtr--;
                if(p2ColorCtr < 0) p2ColorCtr = p2color.size() - 1;
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                p1.SetColor(p1color.get(p1ColorCtr));
                p2.SetColor(p2color.get(p2ColorCtr));
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {status = Gamestate.menu;}
        }                  
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){keysDown[0] = false;}
        if (e.getKeyCode() == KeyEvent.VK_A){keysDown[1] = false;}
        if (e.getKeyCode() == KeyEvent.VK_S){keysDown[2] = false;}
        if (e.getKeyCode() == KeyEvent.VK_D){keysDown[3] = false;}
        
        if (e.getKeyCode() == KeyEvent.VK_UP){keysDown[4] = false;}
        if (e.getKeyCode() == KeyEvent.VK_LEFT){keysDown[5] = false;}
        if (e.getKeyCode() == KeyEvent.VK_DOWN){keysDown[6] = false;}
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){keysDown[7] = false;}
    }
    
    private class DrawingPanel extends JPanel
    {
        
        @Override
        public void paintComponent(Graphics g)
        {
            //System.out.println("We're ");
            super.paintComponent(g);
            
            //System.out.println("WE MADE IT");          
            if (status == Gamestate.play)
            {
                drawPlay(g);
            }
            else if (status == Gamestate.menu)
            {
                drawMenu(g);
            }
            else if (status == Gamestate.instructions)
            {
                drawInst(g);
            }
            else if (status == Gamestate.winner)
            {
                drawWin(g);
            }
            else if (status == Gamestate.countdown)
            {
                drawCountdown(g);
            }
            else if(status == Gamestate.colorpick)
            {
                drawColorPick(g);
            }
        }
        
        public void drawPlay(Graphics g)
        {
            winner1 = false;
            winner2 = false;
            //System.out.println("DRAWING...");
            panel.setBackground(Color.DARK_GRAY);
            g.setColor(Color.GRAY);
            
            g.setFont(new Font("Times New Roman",Font.PLAIN, 48));
            
            //g.fillRect(WIDTH-(FIELDWIDTH/2), HEIGHT-(FIELDHEIGHT/2), FIELDWIDTH, FIELDWIDTH);
            Rectangle field = new Rectangle((PHYSICS.WIDTH-FIELDWIDTH)/2, (PHYSICS.HEIGHT-FIELDHEIGHT)/2, FIELDWIDTH, FIELDHEIGHT);

            g.fillRect(field.x, field.y, field.width, field.height);
            g.setColor(Color.red);
            g.drawString("" + score1,PHYSICS.WIDTH/2 - 90,45);
            g.setColor(Color.BLUE);
            g.drawString("" + score2,PHYSICS.WIDTH/2 + 50,45);
            
            g1.draw(g);
            g2.draw(g);
            p1.draw(g);
            p2.draw(g);
            b.draw(g);           
        }
         public void drawMenu(Graphics g)
        {
            panel.setBackground(Color.GRAY);
            if(menuCtr == 0)
                {
                    g.setColor(Color.red);
                    g.fillRect(PlayBut.x, PlayBut.y, PlayBut.width, PlayBut.height);                              
                }
            if(menuCtr == 1)
                {
                    g.setColor(Color.red);
                    g.fillRect(ExitBut.x, ExitBut.y, ExitBut.width, ExitBut.height);
                }
            if(menuCtr == 2)
            {
                g.setColor(Color.red);
                g.fillRect(InstBut.x, InstBut.y, InstBut.width, InstBut.height);
            }
            if(menuCtr == 3)
            {
                g.setColor(Color.red);
                g.fillRect(ColorPi.x, ColorPi.y, ColorPi.width, ColorPi.height);
            }
            //System.out.println("WE MADE IT");
            frame.setLocationRelativeTo(null);
            g.setColor(Color.BLACK);
            //g.drawRect(485, 50, 585, 60);
            //g.setColor(Color.BLUE);
            g.setFont(new Font("Times New Roman",Font.PLAIN, 48));
            g.drawString("WELCOME TO FAST CLUB",485 , 100);

            g.setColor(Color.BLACK);
            
            //g.setColor(Color.BLUE);
            g.drawString("PLAY", 710, 200);
            g.drawString("EXIT", 710, 300);
            g.drawString("INSTRUCTIONS", 710, 400);
            g.drawString("COLOR PICKER", 710,500);
           
            
        }
         public void drawInst(Graphics g)
        {
            g.drawImage(InstPic.getImage(), 0, 0, PHYSICS.WIDTH,PHYSICS.HEIGHT, null);
        }
         public void drawWin(Graphics g)
         {
             panel.setBackground(Color.LIGHT_GRAY);
             if(winner1)
             {
                 //winner1 = false;
                 //panel.setBackground(Color.LIGHT_GRAY);
                  g.setColor(p1.col);
                  g.setFont(new Font("Times New Roman",Font.PLAIN, 48));                
                  g.drawString("PLAYER 1 IS THE WINNER!!!!", 485, 100);
                  g.drawString("HIT ESC TO GO BACK TO THE MENU.", 485, 200);
                  panel.repaint();
             }
             if(winner2)
             {
                  //winner2 = false;
                  //panel.setBackground(Color.LIGHT_GRAY);
                  g.setColor(p2.col);
                  g.setFont(new Font("Times New Roman",Font.PLAIN, 48));                
                  g.drawString("PLAYER 2 IS THE WINNER!!!!", 485, 100);
                  g.drawString("HIT ESC TO GO BACK TO THE MENU.", 485, 200);
                  panel.repaint();
             }
         }

        private void drawCountdown(Graphics g) 
        {
            
            
            drawPlay(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman",Font.PLAIN, 48));    
            g.drawString("" + (countdown /1000 + 1),PHYSICS.WIDTH/2 - 30, PHYSICS.HEIGHT/2 -150);
            
            System.out.println(countdown/1000);
            panel.repaint();
            countdown--;
            //System.out.println(countdown);
            //if ((Pause.get - 3) == 0)
            if (countdown == 0)   
            {
                
                status = Gamestate.play;
                System.out.println("it's playing");
            }
        }
        private void drawColorPick(Graphics g)
        {
            g.setColor(Color.RED);
            g.setFont(new Font("Times New Roman",Font.PLAIN, 48));    
            g.drawString("P1 use A and D to navigate the colors", 50,50);
            g.setColor(Color.BLUE);
            g.drawString("P2 use Right and Left to navigate the colors", 50,100);
            g.setColor(Color.BLACK);
            g.drawString("Hit Enter to lock in your colors.", 50,150);
            g.drawString("Hit Esc to go back to the menu.", 50, 200);
            for(int x = 0; x < p1color.size(); x ++)
            {
                g.setColor(p1color.get(x));
                g.fillOval((x * 100 + 50),400 , 60,60); 
            }
           for(int x = 0; x < p2color.size(); x ++)
            {
                g.setColor(p2color.get(x));  
                g.fillOval((x * 100 + 50), 500, 60,60);
            }
           // THIS IS BAD. FIX IF TIME.
           // P1 Set Color
            if(p1ColorCtr == 0)
            {
                g.setColor(p1color.get(0));
                g.fillRect(65, 340, 30,60);
            }
            if(p1ColorCtr == 1)
            {
                g.setColor(p1color.get(1));
                g.fillRect(165, 340, 30,60);
            }
             if(p1ColorCtr == 2)
            {
                g.setColor(p1color.get(2));
                g.fillRect(265, 340, 30,60);
            }
              if(p1ColorCtr == 3)
            {
                g.setColor(p1color.get(3));
                g.fillRect(365, 340, 30,60);
            }
               if(p1ColorCtr == 4)
            {
                g.setColor(p1color.get(4));
                g.fillRect(465, 340, 30,60);
            }
                if(p1ColorCtr == 5)
            {
                g.setColor(p1color.get(5));
                g.fillRect(565, 340, 30,60);
            }
                  if(p1ColorCtr == 6)
            {
                g.setColor(p1color.get(6));
                g.fillRect(665, 340, 30,60);
            }         
           //------------------------
           // P2 Color Picker
           if(p2ColorCtr == 0)
            {
                g.setColor(p2color.get(0));
                g.fillRect(65, 562, 30,60);
            }
            if(p2ColorCtr == 1)
            {
                g.setColor(p2color.get(1));
                g.fillRect(165, 562, 30,60);
            }
             if(p2ColorCtr == 2)
            {
                g.setColor(p2color.get(2));
                g.fillRect(265, 562, 30,60);
            }
              if(p2ColorCtr == 3)
            {
                g.setColor(p2color.get(3));
                g.fillRect(365, 562, 30,60);
            }
               if(p2ColorCtr == 4)
            {
                g.setColor(p2color.get(4));
                g.fillRect(465, 562, 30,60);
            }
                if(p2ColorCtr == 5)
            {
                g.setColor(p2color.get(5));
                g.fillRect(565, 562, 30,60);
            }   
                if(p2ColorCtr == 6)
            {
                g.setColor(p2color.get(6));
                g.fillRect(665, 562, 30,60);
            }     
                
        }
    
    }

    private void collide() throws FileNotFoundException, IOException
    {
        carcollisionctr--;
        ballcollisionctr--;
        //CAR ON CAR
        if (Math.sqrt(Math.pow((p1.xpos - p2.xpos),2)+ Math.pow((p1.ypos - p2.ypos),2)) <= PLAYERSIZE && carcollisionctr < 1)
        {
            in = new FileInputStream("Collision on Player.wav");
            collision = new AudioStream(in);
            AudioPlayer.player.start(collision);
            //AudioPlayer.player.stop(collision);
            System.out.println("CAR ON CAR COLLISION");
            custom2dVector p1vel = new custom2dVector(p1.xvel, p1.yvel);
            custom2dVector p2vel = new custom2dVector(p2.xvel, p2.yvel);
            
            custom2dVector p1pos = new custom2dVector(p1.xpos, p1.ypos);
            custom2dVector p2pos = new custom2dVector(p2.xpos, p2.ypos);
            
            
            //custom2dVector test1 = new custom2dVector(3,0);
            //custom2dVector test2 = new custom2dVector(-3,0);
            //custom2dVector temp = new custom2dVector();
            
            //temp = test1.scale(4);
            
            
            //System.out.println(temp);
            
            
            
            custom2dVector newp1 = new custom2dVector();
            custom2dVector newp2 = new custom2dVector();
            
            newp1 = p1vel.subtract(((p1vel.subtract(p2vel).dotProduct(p1pos.subtract(p2pos))).scale(1.0/Math.pow((p1pos.subtract(p2pos).magnitude()),2))).dotProduct(p1pos.subtract(p2pos)));
            newp2 = p2vel.subtract(((p2vel.subtract(p1vel).dotProduct(p2pos.subtract(p1pos))).scale(1.0/Math.pow((p2pos.subtract(p1pos).magnitude()),2))).dotProduct(p2pos.subtract(p1pos)));
            p1.xvel = newp1.x;
            p1.yvel = newp1.y;
            p2.xvel = newp2.x;
            p2.yvel = newp2.y;
            
            //System.out.println("xvel = " + p1.xvel);
            //System.out.println("yvel = " + p1.yvel);
            //p1.xvel = 0;
            //p1.yvel = 0;
            
            carcollisionctr = 2;
                                                                                 
            //p1.xvel = p1.xvel - ((p1.xvel-p2.xvel)*(p1.xpos-p2.xpos))/p1.                       
            //p1.xpos = PHYSICS.WIDTH/2;
            
            if (p1.xpos > p2.xpos)
            {
                p1.xpos += 2;
                p2.xpos -= 2;
            }
            else
            {
                p1.xpos -= 2;
                p2.xpos += 2;
            }
            
            if (p1.ypos > p2.ypos)
            {
                p1.ypos += 2;
                p2.ypos -= 2;
            }
            else
            {
                p1.ypos -= 2;
                p2.ypos += 2;
            }
            
        }
        
        // P1 BALL COLLISION
        if (Math.sqrt(Math.pow((p1.xpos - b.xpos),2)+ Math.pow((p1.ypos - b.ypos),2)) <= (PLAYERSIZE + BALLSIZE) / 2 && ballcollisionctr < 1)
        {
            in = new FileInputStream("Collision On Ball.wav");
            collision = new AudioStream(in);
            AudioPlayer.player.start(collision);
            System.out.println("P1 ON BALL COLLISION");
            custom2dVector p1vel = new custom2dVector(p1.xvel, p1.yvel);
            custom2dVector bvel = new custom2dVector(b.xvel, b.yvel);
            
            custom2dVector p1pos = new custom2dVector(p1.xpos, p1.ypos);
            custom2dVector bpos = new custom2dVector(b.xpos, b.ypos);
            
            
            //custom2dVector test1 = new custom2dVector(3,0);
            //custom2dVector test2 = new custom2dVector(-3,0);
            //custom2dVector temp = new custom2dVector();
            
            //temp = test1.scale(4);
            
            
            //System.out.println(temp);
            
            
            
            custom2dVector newp1 = new custom2dVector();
            custom2dVector newb = new custom2dVector();
            
            newp1 = p1vel.subtract(((p1vel.subtract(bvel).dotProduct(p1pos.subtract(bpos))).scale(1.0/Math.pow((p1pos.subtract(bpos).magnitude()),2))).dotProduct(p1pos.subtract(bpos)));
            newb = bvel.subtract(((bvel.subtract(p1vel).dotProduct(bpos.subtract(p1pos))).scale(1.0/Math.pow((bpos.subtract(p1pos).magnitude()),2))).dotProduct(bpos.subtract(p1pos)));
            p1.xvel = newp1.x;
            p1.yvel = newp1.y;
            b.xvel = newb.x;
            b.yvel = newb.y;
            
            //System.out.println("xvel = " + p1.xvel);
            //System.out.println("yvel = " + p1.yvel);
            //p1.xvel = 0;
            //p1.yvel = 0;
            
            
            //NOT GOOD FIX IF TIME
            b.xvel *= 1.1;
            b.yvel *= 1.1;
            p1.xvel *= .9;
            p1.yvel *= .9;
            
            
            //NOT GOOD FIX IF TIME
            ballcollisionctr = 2;
            
            if (p1.xpos > b.xpos)
            {
                p1.xpos += 2;
                b.xpos -= 2;
            }
            else
            {
                p1.xpos -= 2;
                b.xpos += 2;
            }
            
            if (p1.ypos > b.ypos)
            {
                p1.ypos += 2;
                b.ypos -= 2;
            }
            else
            {
                p1.ypos -= 2;
                b.ypos += 2;
            }
            
            
            
        }
        // P2 ON BALL COLLISION
        if (Math.sqrt(Math.pow((p2.xpos - b.xpos),2)+ Math.pow((p2.ypos - b.ypos),2)) <= (PLAYERSIZE + BALLSIZE) / 2 && ballcollisionctr < 1)
        {
            in = new FileInputStream("Collision on Ball.wav");
            collision = new AudioStream(in);
            AudioPlayer.player.start(collision);
            System.out.println("P2 ON BALL COLLISION");
            custom2dVector p2vel = new custom2dVector(p2.xvel, p2.yvel);
            custom2dVector bvel = new custom2dVector(b.xvel, b.yvel);
            
            custom2dVector p2pos = new custom2dVector(p2.xpos, p2.ypos);
            custom2dVector bpos = new custom2dVector(b.xpos, b.ypos);
            
            
            //custom2dVector test1 = new custom2dVector(3,0);
            //custom2dVector test2 = new custom2dVector(-3,0);
            //custom2dVector temp = new custom2dVector();
            
            //temp = test1.scale(4);
            
            
            //System.out.println(temp);
            
            
            
            custom2dVector newp2 = new custom2dVector();
            custom2dVector newb = new custom2dVector();
            
            newp2 = p2vel.subtract(((p2vel.subtract(bvel).dotProduct(p2pos.subtract(bpos))).scale(1.0/Math.pow((p2pos.subtract(bpos).magnitude()),2))).dotProduct(p2pos.subtract(bpos)));
            newb = bvel.subtract(((bvel.subtract(p2vel).dotProduct(bpos.subtract(p2pos))).scale(1.0/Math.pow((bpos.subtract(p2pos).magnitude()),2))).dotProduct(bpos.subtract(p2pos)));
            p2.xvel = newp2.x;
            p2.yvel = newp2.y;
            b.xvel = newb.x;
            b.yvel = newb.y;
            
            // NOT GOOD FIX IF TIME
            p2.xvel *= .9;
            p2.yvel *= .9;
            b.xvel *= 1.1;
            b.yvel *= 1.1;
            
            //System.out.println("xvel = " + p1.xvel);
            //System.out.println("yvel = " + p1.yvel);
            //p1.xvel = 0;
            //p1.yvel = 0;
            
            // NOT GOOD FIX IF TIME
            ballcollisionctr = 2;
            
             if (p2.xpos > b.xpos)
            {
                p2.xpos += 2;
                b.xpos -= 2;
            }
            else
            {
                p2.xpos -= 2;
                b.xpos += 2;
            }
            
            if (p2.ypos > b.ypos)
            {
                p2.ypos += 2;
                b.ypos -= 2;
            }
            else
            {
                p2.ypos -= 2;
                b.ypos += 2;
            }
                  
    }
    }
}

