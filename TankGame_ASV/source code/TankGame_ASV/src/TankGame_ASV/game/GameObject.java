package TankGame_ASV.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class GameObject {

    protected double x,y,px,py,cx,cy;
    protected int health, width, height;
    private final int INIT_HEALTH;
    protected final double INIT_X,INIT_Y,INIT_PX,INIT_PY,INIT_CX,INIT_CY;
    protected float INIT_ANGLE;
    protected BufferedImage img;
    protected float angle;

    public GameObject(double x, double y, int health, float angle, BufferedImage img)
    {
        width = img.getWidth(null);
        height = img.getHeight(null);
        this.img = img;
        this.x = x;
        this.INIT_X = this.x;
        this.cx = x + width/2;
        this.INIT_CX = this.cx;
        this.px = x;
        this.INIT_PX = this.px;
        this.y = y;
        this.INIT_Y = this.y;
        this.cy = y + height/2;
        this.INIT_CY = this.cy;
        this.py = y;
        this.INIT_PY = this.py;


        this.angle = angle;
        this.INIT_ANGLE = this.angle;

        this.INIT_HEALTH = health;
        this.health = INIT_HEALTH;

    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public double getCentralX(){return this.cx;}
    public double getCentralY(){return this.cy;}
    public double getPX(){return this.px;}
    public double getPY(){return this.py;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}
    public int getHealth(){return this.health;}
    public float getAngle(){return this.angle;}

    void reset_pos()
    {
        this.x = INIT_X;
        this.y = INIT_Y;
        this.cx = INIT_CX;
        this.cy = INIT_CY;
        this.px = INIT_PX;
        this.py = INIT_PY;
        this.angle = INIT_ANGLE;
    }
    void resetHealth(){this.health = INIT_HEALTH;}
    void take_Damage(int damage){this.health = health - damage;}

    public void recoil()
    {
        /*int vex = Math.abs(px - x);
        int vey = Math.abs(py - y);
        System.out.println("X: " + x);
        System.out.println("PX: " + px);
        System.out.println("VEX: " + vex);
        System.out.println("Y: " + y);
        System.out.println("PY: " + py);
        System.out.println("PY: " + vey);
        if(px < x)
        {
            this.x = x - (vex*10);
        }
        else{
            this.x = x + (vex*10);
        }
        if(py < y)
        {
            this.y = y - (vey*10);
        }
        else{
            this.y = y + (vey*10);
        }
        System.out.println("New X: " + x);
        System.out.println("New Y: " + y);*/
        this.x = px;
        this.y = py;
        this.cx = this.x + width/2;
        this.cy = this.y + height/2;
    }
    //public void recoilY(){this.y = py;}


    public void draw(Graphics g, ImageObserver obs){g.drawImage(img,(int)x,(int)y,obs);}
}
