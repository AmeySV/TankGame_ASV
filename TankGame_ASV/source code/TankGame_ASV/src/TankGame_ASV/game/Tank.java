package TankGame_ASV.game;

import TankGame_ASV.Launcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author anthony-pc
 */
public class Tank extends GameObject{


    private double vx, vy;

    //private float angle;
    //private int health = 100;
    private int lives = 3;
    private int R = 2;
    private final float ROTATIONSPEED = 3.0f;
    private int damage = 10;
    private int bullet_damage = 15;
    private int rocket_damage = 25;
    private boolean upgraded_bullet = false;

    private ArrayList<Bullet> myBulletList = new ArrayList<Bullet>();

    private boolean UPPER_WALL_COLLISION = false;
    private boolean LOWER_WALL_COLLISION = false;
    private boolean LEFT_WALL_COLLISION = false;
    private boolean RIGHT_WALL_COLLISION = false;

    private BufferedImage bullet;
    private BufferedImage rocket;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    private boolean shootallowed = true;

    private Random rng = new Random();


    Tank(double x, double y, int health, float angle, BufferedImage img, double vx, double vy) {
        super(x,y,health,angle,img);
        this.vx = vx;
        this.vy = vy;
        try{
            bullet = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet.png")));
            rocket = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("bullet2.png")));
        }catch(IOException e){System.out.println("IOException has occurred.");}
    }

    void setX(int x){ this.x = x; }
    void setY(int y) { this.y = y;}


    int getDamage(){return damage;}
    void resettank(boolean game_reset)
    {
        upgraded_bullet = false;
        reset_pos();
        if (!game_reset)
        {
            int offsetx = rng.nextInt() % 200;
            int offsety = rng.nextInt() % 200;
            boolean positivex = rng.nextBoolean();
            boolean positivey = rng.nextBoolean();
            if(!positivex)
            {
                x = x * (-1);
            }
            if(!positivey)
            {
                y = y * (-1);
            }
            x = INIT_X + offsetx;
            y = INIT_Y + offsety;
            cx = INIT_CX + offsetx;
            cy = INIT_CY + offsety;
        }
        resetHealth();
        myBulletList.clear();
    }
    boolean getbulletstate(){return upgraded_bullet;}
    void upgrade_bullet(){upgraded_bullet = true;}
    void reducelives(){lives -= 1;}
    int getlives(){return lives;}
    void resetlives(){lives = 3;}
    //float getAngle(){return angle;}

    ArrayList<Bullet> getBulletList(){return myBulletList;}

    void toggleUpPressed() {
        this.UpPressed = true;
    }
    void toggleDownPressed() {
        this.DownPressed = true;
    }
    void toggleRightPressed() {
        this.RightPressed = true;
    }
    void toggleLeftPressed() {this.LeftPressed = true;}
    void toggleShootPressed() { this.ShootPressed = true;}
    void unToggleUpPressed() {
        this.UpPressed = false;
    }
    void unToggleDownPressed() {
        this.DownPressed = false;
    }
    void unToggleRightPressed() {
        this.RightPressed = false;
    }
    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }
    void unToggleShootPressed() { this.ShootPressed = false;
    shootallowed = true;
    //System.out.println("Shoot released acknowledged!");
    }

    void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
            this.shoot();
        }
        for(int i = 0; i < myBulletList.size(); i++)
        {
            myBulletList.get(i).update();
            if (!(myBulletList.get(i).get_Show()))
            {
                try {
                    System.out.println("Before removal: " + myBulletList.toString());
                    myBulletList.remove(i);
                    i--;
                    System.out.println("After removal: " + myBulletList.toString());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No bullets in list to hide");
                }
            }
        }
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
        if(angle < 0)
        {
            angle += 360;
        }
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
        if(angle > 359)
        {
            angle -= 360;
        }
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        px = x;
        py = y;
        x -= vx;
        y -= vy;
        cx -= vx;
        cy -= vy;
        checkBorder();

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        px = x;
        py = y;
        x += vx;
        y += vy;
        cx += vx;
        cy += vy;
        checkBorder();

    }


    private void shoot()
    {
        //System.out.println("Pew pew!");
        if(this.ShootPressed && shootallowed)
        {
            shootallowed = false;
            Bullet player_bullet;
            if(upgraded_bullet)
                player_bullet = new Bullet(cx - 17.0, cy - 7,1,angle, rocket, R+1, rocket_damage);
            else
                player_bullet = new Bullet(cx - 17.0, cy - 7,1,angle, bullet, R, bullet_damage);
            myBulletList.add(player_bullet);
            this.ShootPressed = false;
        }
    }




    private void checkBorder() {
        if (x < 128) {
            x = 128;
            cx = 128 + width/2;
            if(!LEFT_WALL_COLLISION)
            {
                health = health - 10;
            }
            LEFT_WALL_COLLISION = true;
        }
        else
        {
            LEFT_WALL_COLLISION = false;
        }
        if (x >= 2891) {
            x = 2891;
            cx = 2891 + width/2;
            if(!RIGHT_WALL_COLLISION)
            {
                health = health - 10;
            }
            RIGHT_WALL_COLLISION = true;
        }
        else
        {
            RIGHT_WALL_COLLISION = false;
        }
        if (y < 128) {
            y = 128;
            cy = 128 + height/2;
            if(!UPPER_WALL_COLLISION)
            {
                health = health - 10;
            }
            UPPER_WALL_COLLISION = true;
        }
        else
        {
            UPPER_WALL_COLLISION = false;
        }
        if (y >= 1363) {
            y = 1363;
            cy = 1363 + height/2;
            if(!LOWER_WALL_COLLISION)
            {
                health = health - 10;
            }
            LOWER_WALL_COLLISION = true;
        }
        else
        {
            LOWER_WALL_COLLISION = false;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth(null) / 2.0, this.img.getHeight(null) / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.img, rotation, null);
    }



}
