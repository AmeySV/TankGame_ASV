package TankGame_ASV.game;

import TankGame_ASV.GameConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Score extends JPanel implements Runnable {
    private BufferedImage blank;
    private Graphics2D g4;
    private BufferedImage t1img, rocket_upgrade;
    private int t1hp, t2hp;
    private BufferedImage p1hp, p2hp;
    private BufferedImage bar1, bar2, bar3, bar4, bar5, bar6, bar7, bar8, bar9, bar10;
    private BufferedImage live;
    private Tank t1, t2;

    Score(Tank a, Tank b)
    {
        setSize(GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT/2);
        blank = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT/2,
                BufferedImage.TYPE_INT_RGB);
        try {
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            bar1 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_1.png")));
            bar2 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_2.png")));
            bar3 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_3.png")));
            bar4 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_4.png")));
            bar5 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_5.png")));
            bar6 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_6.png")));
            bar7 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_7.png")));
            bar8 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_8.png")));
            bar9 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_9.png")));
            bar10 = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("health_bar_10.png")));
            live = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("live.png")));
            rocket_upgrade = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("rocket.png")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();}

        p1hp = bar10;
        p2hp = bar10;
        this.t1 = a;
        this.t2 = b;

        int rgb = new Color(0, 0, 0).getRGB();
        blank.setRGB(0,0, rgb);
        //p1health = new JLabel("");
        //p1health.setForeground(Color.GREEN);
        //setLayout(new BorderLayout());
        //setBorder(new EmptyBorder(224, 620, 324, 720));
        //add(p1health);
    }

    public void update(Tank a, Tank b)
    {
        t1 = a;
        t2 = b;
        t1hp = t1.getHealth();
        t2hp = t2.getHealth();

        if(t1hp>=91){p1hp = bar10;}
        else if(t1hp>=81){p1hp = bar9;}
        else if(t1hp>=71){p1hp = bar8;}
        else if(t1hp>=61){p1hp = bar7;}
        else if(t1hp>=51){p1hp = bar6;}
        else if(t1hp>=41){p1hp = bar5;}
        else if(t1hp>=31){p1hp = bar4;}
        else if(t1hp>=21){p1hp = bar3;}
        else if(t1hp>=11){p1hp = bar2;}
        else if(t1hp>=01){p1hp = bar1;}

        if(t2hp>=91){p2hp = bar10;}
        else if(t2hp>=81){p2hp = bar9;}
        else if(t2hp>=71){p2hp = bar8;}
        else if(t2hp>=61){p2hp = bar7;}
        else if(t2hp>=51){p2hp = bar6;}
        else if(t2hp>=41){p2hp = bar5;}
        else if(t2hp>=31){p2hp = bar4;}
        else if(t2hp>=21){p2hp = bar3;}
        else if(t2hp>=11){p2hp = bar2;}
        else if(t2hp>=01){p2hp = bar1;}

    }

    @Override
    public void run()
    {
        //System.out.println("Score painting");
        try{
            //System.out.println("Placeholder");
            this.repaint();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void paintComponent(Graphics f) {
        g4 = (Graphics2D) f;
        g4.drawImage(blank, 0, 0, null);
        g4.drawImage(t1img, 0, 0, null);
        if(t1.getbulletstate() == true)
        {
            g4.drawImage(rocket_upgrade, (t1img.getWidth()+10), 0, null);
        }
        g4.drawImage(t1img, (447-t1img.getWidth()), 0, null);
        if(t2.getbulletstate() == true)
        {
            g4.drawImage(rocket_upgrade, (447-t1img.getWidth()-rocket_upgrade.getWidth())-10, 0, null);
        }
        g4.drawImage(p1hp, 0, t1img.getHeight(),null);
        g4.drawImage(p2hp, 224, t1img.getHeight(), null);
        if(t1.getlives() == 3)
        {
            g4.drawImage(live, 0, t1img.getHeight()+p1hp.getHeight(), null);
            g4.drawImage(live, live.getWidth(), t1img.getHeight()+p1hp.getHeight(), null);
            g4.drawImage(live, 2*live.getWidth(), t1img.getHeight()+p1hp.getHeight(), null);
        }
        else if(t1.getlives() == 2)
        {
            g4.drawImage(live, 0, t1img.getHeight()+p1hp.getHeight(), null);
            g4.drawImage(live, live.getWidth(), t1img.getHeight()+p1hp.getHeight(), null);
        }
        else
        {
            g4.drawImage(live, 0, t1img.getHeight()+p1hp.getHeight(), null);
        }
        if(t2.getlives() == 3)
        {
            g4.drawImage(live, 224, t1img.getHeight()+p2hp.getHeight(), null);
            g4.drawImage(live, 224+live.getWidth(), t1img.getHeight()+p2hp.getHeight(), null);
            g4.drawImage(live, 224 + (2*live.getWidth()), t1img.getHeight()+p2hp.getHeight(), null);
        }
        else if(t2.getlives() == 2)
        {
            g4.drawImage(live, 224, t1img.getHeight()+p2hp.getHeight(), null);
            g4.drawImage(live, 224 + live.getWidth(), t1img.getHeight()+p2hp.getHeight(), null);
        }
        else
        {
            g4.drawImage(live, 224, t1img.getHeight()+p2hp.getHeight(), null);
        }

    }
}
