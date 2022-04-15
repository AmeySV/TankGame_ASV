package TankGame_ASV.game;

import TankGame_ASV.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera extends JPanel implements Runnable{
    private Tank focus_tank;
    private Tank other_tank;
    private ArrayList<Bullet> focus_tank_bullets;
    private ArrayList<Bullet> other_tank_bullets;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Wall> wall_list;
    //private Graphics2D background_img;
    //private Graphics2D wall_img;
    private int cam_x, cam_y, offsetMaxX, offsetMinX, offsetMaxY, offsetMinY;
    private Graphics2D g2;
    private BufferedImage land2, w1img;

    Camera(Tank a, Tank b, ArrayList<Wall> c, ArrayList<PowerUp> d, BufferedImage land2, BufferedImage w1img)
    {
        focus_tank = a;
        other_tank = b;
        wall_list = c;
        powerUps = d;
        this.land2 = land2;
        this.w1img = w1img;
        focus_tank_bullets = focus_tank.getBulletList();
        other_tank_bullets = other_tank.getBulletList();

        offsetMaxX = land2.getWidth() - GameConstants.GAME_SCREEN_WIDTH;
        offsetMaxY = land2.getHeight() - GameConstants.GAME_SCREEN_HEIGHT;
        offsetMinX = 0;
        offsetMinY = 0;
    }

    @Override
    public void run()
    {
        try
        {
            this.repaint();
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    public void updateTanks(Tank a, Tank b, ArrayList<Wall> c)
    {
        focus_tank = a;
        other_tank = b;
        wall_list = c;
    }

    public void drawBorders()
    {
        int backgd_w = land2.getWidth();
        int backgd_h = land2.getHeight();
        int wall_w = w1img.getWidth();
        int wall_h = w1img.getHeight();

        int horizontal = backgd_w / wall_w;
        int vertical = backgd_h / wall_h;

        for(int i = 0; i <= horizontal-1; i++)
        {
            for(int j = 0; j <= vertical-1; j++)
            {
                if(i == 0 || i == horizontal-1)
                {
                    g2.drawImage(w1img, i*128,j*128, null);
                }
                else
                {
                    if(j == 0 || j == vertical-1)
                    {
                        g2.drawImage(w1img, i*128,j*128, null);
                    }
                }
            }
        }

    }


    public void update_render()
        {
            cam_x = (int)focus_tank.getX() - GameConstants.GAME_SCREEN_WIDTH/2;
            cam_y = (int)focus_tank.getY() - GameConstants.GAME_SCREEN_HEIGHT/2;
            if (cam_x > offsetMaxX)
                cam_x = offsetMaxX;
            else if (cam_x < offsetMinX)
                cam_x = offsetMinX;
            if (cam_y > offsetMaxY)
                cam_y = offsetMaxY;
            else if (cam_y < offsetMinY)
                cam_y = offsetMinY;
        }

    public void render(Graphics g)
    {
        g.translate(-cam_x, -cam_y);
    }


    @Override
    public void paintComponent(Graphics g) {
        update_render();
        render(g);
        g2 = (Graphics2D) g;
        g2.drawImage(land2, 0,0,null);
        this.focus_tank.drawImage(g2);
        this.other_tank.drawImage(g2);
        for (Bullet focus_tank_bullet : focus_tank_bullets) {
            focus_tank_bullet.drawImage(g2);
        }
        for (Bullet other_tank_bullet : other_tank_bullets) {
            other_tank_bullet.drawImage(g2);
        }
        for (Wall wall : wall_list) {
            wall.drawImage(g2);
        }
        for (PowerUp powerUp : powerUps) {
            powerUp.drawImage(g2);
        }
        drawBorders();
    }

}
