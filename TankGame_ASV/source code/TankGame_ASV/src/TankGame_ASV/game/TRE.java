/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame_ASV.game;


import TankGame_ASV.GameConstants;
import TankGame_ASV.Launcher;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author Amey Vaze
 * Template by Anthony Souza
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage land, land2, t1img, w1img, w100img, hppack, rocket;
    private Tank t1, t2;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Wall> walls;
    private CollisionDetector cd;
    private Scanner wall_reader;

    private Launcher lf;
    private long tick = 0;
    private int w;
    private int h;

    private Camera cam1;
    private Camera cam2;
    private JPanel stats;
    private Minimap map;
    private Score HUD;
    public TRE(Launcher lf, boolean first_focus)
    {
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank 1 position, stats and bulletlist internally
                this.t2.update(); // update tank 2 position, stats and bulletlist internally
                this.HUD.update(t1,t2);

                cd.col_check(t1,t2, walls); //checks for collisions between any game objects, using t1.update() and t2.update() data
                cam1.updateTanks(t1,t2, walls); // update tank positions on screen 1, using data from both tanks' update()
                map.update_data(t1,t2);
                cam2.updateTanks(t2,t1, walls); // update tank positions on screen 2, using data from both tanks' update()
                cam1.update_render(); // update viewpoint on screen 1 to focus on tank 1
                cam2.update_render(); // update viewpoint on screen 2 to focus on tank 2
                cam1.run();
                map.run();
                HUD.run();
                cam2.run();


                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 20000 frames have been drawn
                 */
                if(t1.getHealth() <= 0 || t2.getHealth() <= 0)
                {
                    if(t1.getHealth() <= 0)
                    {
                        t1.reducelives();
                        this.t1.resettank(false);
                    }
                    else if(t2.getHealth() <= 0)
                    {
                        t2.reducelives();
                        this.t2.resettank(false);
                    }
                    if(t1.getlives() == 0 || t2.getlives() == 0)
                    {
                        this.lf.setFrame("end");
                        return;
                    }
                }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.resettank(true);
        this.t1.resetlives();
        this.t2.resettank(true);
        this.t2.resetlives();
        this.walls.clear();
        this.powerUps.clear();
        load_layout(true,true);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {

        this.land2 = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        try {
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            land = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));
            w1img = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall.png")));
            w100img = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("wall-100.png")));
            hppack = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_pack.png")));
            rocket = ImageIO.read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rocket.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();}

        w = land.getWidth();
        h = land.getHeight();
        land2 = new BufferedImage(w*12, h*6, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(12.0, 6.0);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        land2 = scaleOp.filter(land, land2);

        walls = new ArrayList<>();
        powerUps = new ArrayList<>();
        load_layout(false, false);

        cam1 = new Camera(t1,t2,walls,powerUps,land2,w1img);
        cam2 = new Camera(t2,t1,walls,powerUps,land2,w1img);
        stats = new JPanel();
        stats.setSize(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        map = new Minimap(t1,t2,walls,powerUps);
        HUD = new Score(t1, t2);
        stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
        stats.add(map);
        stats.add(HUD);

        cd = new CollisionDetector(t1,t2, walls, powerUps);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.lf.getJf().addKeyListener(tc2);
        this.lf.getJf().addKeyListener(tc1);
    }

    private void load_layout(boolean t1_loaded, boolean t2_loaded)
    {
        int line = 0;
        //File directory = new File("./");
        //System.out.println(directory.getAbsolutePath());
        File layout = new File("layout1.txt");
        try
        {
            wall_reader = new Scanner(layout);
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        while(wall_reader.hasNextLine())
        {
            String row = wall_reader.nextLine();
            char[] row_objects = row.toCharArray();
            for(int place = 0; place < row_objects.length; place++)
            {
                if(row_objects[place]==('U'))
                {
                    Wall unbreakable = new Wall(place*128, line*128, 100, 0, w1img, false);
                    walls.add(unbreakable);
                }
                else if(row_objects[place] == 'B')
                {
                    Wall breakable = new Wall(place*128, line*128, 100, 0, w100img, true);
                    walls.add(breakable);
                }
                else if(row_objects[place] == 'H')
                {
                    PowerUp health = new PowerUp(place*128 + 39 , line*128 + 39, 1, 0, hppack, 0);
                    powerUps.add(health);
                }
                else if(row_objects[place] == 'R')
                {
                    PowerUp rocket_pack = new PowerUp(place*128 + 39 , line*128 + 39, 1, 0, rocket, 1);
                    powerUps.add(rocket_pack);
                }
                else if(row_objects[place] == '1' && !t1_loaded)
                {
                    t1 = new Tank(place*128 + 64, line*128 + 64, 100, 0, t1img, 0,0);
                }
                else if(row_objects[place] == '2' && !t2_loaded)
                {
                    t2 = new Tank(place*128 + 64, line*128 + 64, 100, 180, t1img, 0,0);
                }
            }
            line++;
        }
    }

    public Camera getCam1(){return cam1;}
    public Camera getCam2(){return cam2;}
    public JPanel getStats(){return stats;}
}
