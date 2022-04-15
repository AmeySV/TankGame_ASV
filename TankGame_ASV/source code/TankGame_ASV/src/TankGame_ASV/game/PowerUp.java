package TankGame_ASV.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PowerUp extends GameObject
{
    private boolean show = true;
    private int damage = 50;
    private int type;

    PowerUp(int x, int y, int health, float angle, BufferedImage img, int type)
    {
        super(x,y,health,angle,img);
        this.type = type;
    }

    int getDamage(){return damage;}
    int getType(){return type;}
    boolean getShow(){return show;}
    void hide(){show=false;}

    public void drawImage(Graphics g)
    {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
