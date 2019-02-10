
package finalproject;
/**
 *
 * @author gormanjc
 */
public interface PHYSICS 
{
    public static final int WIDTH = 1600;   //1600
    public static final int HEIGHT = 900;   //900
    public static final int PLAYERSIZE = 50;    //50
    public static final double DECELLMOD = .99; // CONTROLS HOW MUCH THE BALL NATURALLY SLOWS DOWN, SHOULD BE > .95, < 1.0
    public static final int BALLSIZE = 80;  //80
    public static final int PLAYERMASS = 50;    //50
    public static final int BALLMASS = 20;  //20
    public static final int FIELDWIDTH = 1400;  //1400
    public static final int FIELDHEIGHT = 800;  //800
    public static final int GOALHEIGHT = 400;   //400
    public static final int GOALDEPTH = 100;    //100
    public static final double JUKE = .75;  //THE HIGHER THIS IS, THE MORE TOUCHY AND SENSITIVE THE CARS RESPOND    .3 SEEMS ALRIGHT    .3
    public static final int MAXSPEED = 6;   //8
    public static enum Gamestate {menu, instructions, play, goal, replay, winner, pause, countdown, colorpick};

    
}
