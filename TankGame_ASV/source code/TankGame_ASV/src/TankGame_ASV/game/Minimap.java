package TankGame_ASV.game;

import TankGame_ASV.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Minimap extends JPanel implements Runnable {

    private BufferedImage blank;
    private BufferedImage wall_mini, tank_mini, wall_b_mini, hp_mini, rocket_mini;
    //private BufferedImage tank_mini;
    private Graphics2D g3;
    Tank t1, t2;
    private ArrayList<Wall> walls;
    private ArrayList<PowerUp> powerUps;
    Minimap(Tank t1, Tank t2, ArrayList<Wall> walls, ArrayList<PowerUp> powerUps)
    {
        this.walls = walls;
        this.powerUps = powerUps;
        this.t1 = t1;
        this.t2 = t2;
        setSize(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT/2);
        blank = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT/2,
                BufferedImage.TYPE_INT_RGB);
        try {
            wall_mini = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall_mini.png")));
            wall_b_mini = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Wall_Breakable_Mini.png")));
            tank_mini = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Tank_mini.png")));
            hp_mini = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("hp_mini.png")));
            rocket_mini = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rocket_mini.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();}

        int rgb = new Color(0, 0, 0).getRGB();
        blank.setRGB(0,0, rgb);

    }

    void update_data(Tank t1, Tank t2)
    {
        this.t1 = t1;
        this.t2 = t2;
    }
    void drawBorders()
    {
        int columns = 24;
        int rows = 12;

        for(int row = 0; row < rows; row++)
        {
            for(int column = 0; column < columns; column++)
            {
                if(row == 0 || row == rows-1)
                {
                    g3.drawImage(wall_mini, (column*20)-10,(row*20)-10, null);
                }
                else if(column == 0 || column == columns-1)
                {
                    g3.drawImage(wall_mini, (column*20)-10,(row*20)-10, null);
                }
            }
        }
    }

    @Override
    public void run()
    {
        //System.out.println("Minimap painting");
        try{
            //System.out.println("Placeholder");
            this.repaint();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void paintComponent(Graphics h) {
        g3 = (Graphics2D) h;
        g3.drawImage(blank, 0, 0, null);
        //System.out.println("Placing walls in minimap. " + walls.size() + " walls to place.");
        g3.drawImage(tank_mini, (int) (20*t1.getX()/128) - 10, (int) (20*t1.getY()/128) - 10, null);
        g3.drawImage(tank_mini, (int) (20*t2.getX()/128) - 10, (int) (20*t2.getY()/128) - 10, null);
        for(int i = 0; i < walls.size(); i++)
        {
            double ax = walls.get(i).getX();
            double ay = walls.get(i).getY();
            int mx = (int) (20*ax/128) - 10;
            int my = (int) (20*ay/128) - 10;
            //System.out.println("ax: " + ax +" ay: " + ay +" mx: " + mx +" my: " + my);
            if(!(walls.get(i).getBreakable()))
            {
                g3.drawImage(wall_mini, mx,my, null);
            }
            else
            {
                g3.drawImage(wall_b_mini, mx, my, null);
            }
        }
        for(int j = 0; j < powerUps.size(); j++)
        {
            if(powerUps.get(j).getType() == 0)
            g3.drawImage(hp_mini, (int) (20*powerUps.get(j).getX()/128) - 10, (int) (20*powerUps.get(j).getY()/128) - 10, null);
            else if(powerUps.get(j).getType() == 1)
            g3.drawImage(rocket_mini, (int) (20*powerUps.get(j).getX()/128) - 10, (int) (20*powerUps.get(j).getY()/128) - 10, null);

        }
        drawBorders();
    }
}
