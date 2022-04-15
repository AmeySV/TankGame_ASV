package TankGame_ASV.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Wall extends GameObject {
    private boolean breakable;
    private boolean show = true;
    private BufferedImage hp25, hp50, hp75, orig;
    //private int health;
    private int damage;
    Wall(int x, int y, int health, float angle, BufferedImage img, boolean breakable)
    {
        super(x, y, health, angle, img);
        this.orig = img;
        this.breakable = breakable;
        if(breakable)
        {
            damage = 5;
            try
            {
                hp25 = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall-25.png")));
                hp50 = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall-50.png")));
                hp75 = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall-75.png")));
            }catch(IOException e)
            {
                e.printStackTrace();
            }

        }
        else{damage = 10;}
    }

    public void update()
    {
        System.out.println("Entered wall update()");
        if(breakable)
        {
            System.out.println("Wall is breakable");
            if(health <= 0)
            {
                health = 0;
                System.out.println("marking wall as invisible");
                show = false;
            }
            else if(health < 25)
            {
                img = hp25;
            }
            else if (health < 50)
            {
                img = hp50;
            }
            else if (health < 75)
            {
                System.out.println("Health has dropped below 75!");
                img = hp75;
            }
            else
            {
                img = orig;
            }
        }
    }
     void reset()
     {
         this.health = 100;
         img = orig;
         show = true;
     }
    public boolean get_Show()
    {
        return show;
    }

    public boolean getBreakable(){
        return breakable;
    }

    public int getDamage()
    {
        return damage;
    }

    public void drawImage(Graphics g)
    {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}