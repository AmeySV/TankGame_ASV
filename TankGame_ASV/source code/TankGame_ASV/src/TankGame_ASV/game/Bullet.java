package TankGame_ASV.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int speed, damage, vx, vy;
    private boolean show;
    Bullet(double x, double y, int health, float angle, BufferedImage img, int speed, int damage)
    {
        super(x,y,health,angle,img);
        this.speed = speed;
        this.damage = damage;
    }

    void update()
    {
        vx = (int) Math.round(speed * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(speed * Math.sin(Math.toRadians(angle)));

        px = x;
        py = y;
        x += vx;
        y += vy;
        cx += vx;
        cy += vy;
        checkBorder();
    }

    private void checkBorder()
    {

        if (x < 128 || x >= (2944 - width) || y < 128 || y >= (1408 - height))
        {
            //System.out.println("Show is now false");
            show = false;
        }
        else
        {
            show = true;
        }
    }

    boolean get_Show() {
        return show;
    }
    int getDamage(){return damage;}
    void hide()
    {
        show = false;
    }

    void drawImage(Graphics g){
        if(show)
        {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(this.img, rotation, null);
        }
    }

}
