package TankGame_ASV.game;

import java.awt.geom.*;
import java.lang.Math;
import java.util.ArrayList;

public class CollisionDetector {

    boolean tank_v_tank_collision = false;
    boolean t1_v_wall_collision = false;
    boolean t2_v_wall_collision = false;
    Tank tank1;
    Tank tank2;
    //ArrayList<Bullet> t1_bullets;
    //ArrayList<Bullet> t2_bullets;
    ArrayList<Wall> wall_list;
    ArrayList<PowerUp> powerUps;

    double closest_dist_t1 = 10000;
    double closest_dist_t2 = 10000;
    int t1_wall_list_pos;
    int t2_wall_list_pos;
    Wall closest_wall_t1;
    Wall closest_wall_t2;

    double closest_dist_t1_bullet = 10000;
    double closest_dist_t2_bullet = 10000;
    Bullet closest_t1_bullet;
    Bullet closest_t2_bullet;

    double tank_width, tank_height, wall_width, wall_height;
    //ArrayList<Wall> wall_list;
    CollisionDetector(Tank a, Tank b,  ArrayList<Wall> c, ArrayList<PowerUp> d)
    {
        this.tank1 = a;
        this.tank2 = b;
        //this.t1_bullets = a.getBulletList();
        //this.t2_bullets = b.getBulletList();
        this.wall_list = c;
        this.powerUps = d;
        tank_width = tank1.getWidth();
        tank_height = tank1.getHeight();
        wall_width = wall_list.get(0).getWidth();
        wall_height = wall_list.get(0).getHeight();
    }

    public void col_check(Tank a, Tank b, ArrayList<Wall> c)
    {
        this.tank1 = a;
        this.tank2 = b;
        //this.t1_bullets = a.getBulletList();
        //this.t2_bullets = b.getBulletList();
        this.wall_list = c;
        tank_v_tank_check();
        bullet_v_tank_check();
        tanks_v_walls_check();
        bullets_v_walls_check();
        tank_v_powerup_check();
    }

    public void tank_v_tank_check()
    {
        double distance = Math.hypot(Math.abs(tank2.getCentralX() - tank1.getCentralX()), Math.abs(tank2.getCentralY() - tank1.getCentralY()));
        if(distance < 49)
        {
            if(tank_v_tank_collision == false)
            {
                tank_v_tank_collision = true;
                tank1.take_Damage(tank2.getDamage());
                tank2.take_Damage(tank1.getDamage());
                System.out.println("Collision detected!");
            }
            tank1.recoil();
            tank2.recoil();

        }
        else if(distance > 52)
        {
            tank_v_tank_collision = false;
        }

    }

    public void bullet_v_tank_check()
    {
        for (int i = 0; i < tank1.getBulletList().size(); i++) {
            double distance = Math.hypot(Math.abs(tank2.getCentralX() - tank1.getBulletList().get(i).getCentralX()),
                                         Math.abs(tank2.getCentralY() - tank1.getBulletList().get(i).getCentralY()));
            if (distance < 29) {
                tank1.getBulletList().get(i).hide();
                tank2.take_Damage(tank1.getBulletList().get(i).getDamage());
                System.out.println("Hiding Bullet for Tank 1");
                if (!(tank1.getBulletList().get(i).get_Show())) {
                    try {
                        tank1.getBulletList().remove(i);
                        i--;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No bullets in list to hide");
                    }
                }
            }
        }
        for (int j = 0; j < tank2.getBulletList().size(); j++)
        {
            double distance = Math.hypot(Math.abs(tank1.getCentralX() - tank2.getBulletList().get(j).getCentralX()),
                                         Math.abs(tank1.getCentralY() - tank2.getBulletList().get(j).getCentralY()));
            if (distance < 29) {
                tank2.getBulletList().get(j).hide();
                tank1.take_Damage(tank2.getBulletList().get(j).getDamage());
                if (!(tank2.getBulletList().get(j).get_Show())) {
                    try {
                        tank2.getBulletList().remove(j);
                        j--;
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("No bullets to hide");
                    }
                }
            }
        }
    }

    public void tanks_v_walls_check()
    {
        for(int i = 0; i < wall_list.size(); i++)
        {
            double dist_to_t1 = Math.hypot(Math.abs(wall_list.get(i).getCentralX() - tank1.getCentralX()),
                                         Math.abs(wall_list.get(i).getCentralY() - tank1.getCentralY()));
            double dist_to_t2 = Math.hypot(Math.abs(wall_list.get(i).getCentralX() - tank2.getCentralX()),
                                         Math.abs(wall_list.get(i).getCentralY() - tank2.getCentralY()));

            if(closest_dist_t1 > dist_to_t1)
            {
                closest_dist_t1 = dist_to_t1;
                t1_wall_list_pos = i;
                //closest_wall_t1 = wall_list.get(i);
            }
            if(closest_dist_t2 > dist_to_t2)
            {
                closest_dist_t2 = dist_to_t2;
                t2_wall_list_pos = i;
                closest_wall_t2 = wall_list.get(i);
            }
        }
        if(closest_dist_t1 < 126)
        {
            Ellipse2D.Double t1_ellipse = new Ellipse2D.Double(tank1.getX(), tank1.getY(), tank_width, tank_height);
            Rectangle2D.Double wall_rect = new Rectangle2D.Double(wall_list.get(t1_wall_list_pos).getX(), wall_list.get(t1_wall_list_pos).getY(),
                                                                    wall_width, wall_height);
            if(t1_ellipse.intersects(wall_rect))
            {
                if(!t1_v_wall_collision)
                {
                    t1_v_wall_collision = true;
                    tank1.take_Damage(wall_list.get(t1_wall_list_pos).getDamage());
                    if(wall_list.get(t1_wall_list_pos).getBreakable())
                    {
                        wall_list.get(t1_wall_list_pos).take_Damage(tank1.getDamage());
                        wall_list.get(t1_wall_list_pos).update();
                    }
                    System.out.println("Wall Collision detected!");
                }
                tank1.recoil();
            }
        }
        else if(closest_dist_t1 > 129)
        {
            t1_v_wall_collision = false;
        }
        if(closest_dist_t2 < 126)
        {
            Ellipse2D.Double t2_ellipse = new Ellipse2D.Double(tank2.getX(), tank2.getY(), tank_width, tank_height);
            Rectangle2D.Double wall_rect = new Rectangle2D.Double(closest_wall_t2.getX(), closest_wall_t2.getY(), wall_width, wall_height);
            if(t2_ellipse.intersects(wall_rect))
            {
                if(!t2_v_wall_collision)
                {
                    t2_v_wall_collision = true;
                    tank2.take_Damage(wall_list.get(t2_wall_list_pos).getDamage());
                    if(wall_list.get(t2_wall_list_pos).getBreakable())
                    {
                        wall_list.get(t2_wall_list_pos).take_Damage(tank2.getDamage());
                        wall_list.get(t2_wall_list_pos).update();
                    }
                }
                tank2.recoil();
            }
        }
        else if(closest_dist_t2 > 129)
        {
            t2_v_wall_collision = false;
        }
        if(!(wall_list.get(t2_wall_list_pos).get_Show()))
        {
            try {
                wall_list.remove(t1_wall_list_pos);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No wall to hide");
            }
        }
        if(!(wall_list.get(t2_wall_list_pos).get_Show()))
        {
            try {
                wall_list.remove(t2_wall_list_pos);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("No wall to hide");
            }
        }
        t1_wall_list_pos = -1;
        t2_wall_list_pos = -1;
        closest_dist_t1 = 10000;
        closest_dist_t2 = 10000;
        closest_wall_t1 = null;
        closest_wall_t2 = null;
    }

    void bullets_v_walls_check()
    {
        for(int i = 0; i < wall_list.size(); i++)
        {
            for (int j = 0; j < tank1.getBulletList().size(); j++)
            {
                double dist = Math.hypot(Math.abs(wall_list.get(i).getCentralX() - tank1.getBulletList().get(j).getCentralX()),
                        Math.abs(wall_list.get(i).getCentralY() - tank1.getBulletList().get(j).getCentralY()));
                //System.out.println(dist);
                if(dist < 70)
                {
                    tank1.getBulletList().get(j).hide();
                    if(wall_list.get(i).getBreakable())
                    {
                        wall_list.get(i).take_Damage(tank1.getBulletList().get(j).getDamage());
                        wall_list.get(i).update();
                    }
                    if (!(tank1.getBulletList().get(j).get_Show())) {
                        try {
                            tank1.getBulletList().remove(j);
                            j--;
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("No bullets to hide");
                        }
                    }
                }
            }
            for (int j = 0; j < tank2.getBulletList().size(); j++)
            {
                double dist = Math.hypot(Math.abs(wall_list.get(i).getCentralX() - tank2.getBulletList().get(j).getCentralX()),
                        Math.abs(wall_list.get(i).getCentralY() - tank2.getBulletList().get(j).getCentralY()));
                if(dist < 70)
                {
                    tank2.getBulletList().get(j).hide();
                    if(wall_list.get(i).getBreakable())
                    {
                        wall_list.get(i).take_Damage(tank2.getBulletList().get(j).getDamage());
                        wall_list.get(i).update();
                    }
                    if (!(tank2.getBulletList().get(j).get_Show())) {
                        try {
                            tank2.getBulletList().remove(j);
                            j--;
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("No bullets to hide");
                        }
                    }
                }
            }
            //System.out.println("Current wall visibility: " + wall_list.get(i).get_Show());
            if (!(wall_list.get(i).get_Show())) {
                try {
                    System.out.println("Attempting wall removal");
                    wall_list.remove(i);
                    i--;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No walls to hide");
                }
            }
        }
    }

    void tank_v_powerup_check()
    {
        for (int i = 0; i < powerUps.size(); i++)
        {
            Ellipse2D.Double t1_ellipse = new Ellipse2D.Double(tank1.getX(), tank1.getY(), tank_width, tank_height);
            Ellipse2D.Double t2_ellipse = new Ellipse2D.Double(tank2.getX(), tank2.getY(), tank_width, tank_height);
            Rectangle2D.Double pUp_rect = new Rectangle2D.Double(powerUps.get(i).getX(), powerUps.get(i).getY(), powerUps.get(i).getWidth(), powerUps.get(i).getHeight());

            if(t1_ellipse.intersects(pUp_rect))
            {
                if (powerUps.get(i).getType() == 0)
                {
                    tank1.take_Damage(-(powerUps.get(i).getDamage()));
                    if(tank1.getHealth() > 100){tank1.resetHealth();}
                    powerUps.get(i).hide();
                }
                else if (powerUps.get(i).getType() == 1)
                {
                    tank1.upgrade_bullet();
                    powerUps.get(i).hide();
                }
            }
            if(t2_ellipse.intersects(pUp_rect))
            {
                if (powerUps.get(i).getType() == 0)
                {
                    tank2.take_Damage(-(powerUps.get(i).getDamage()));
                    if(tank2.getHealth() > 100){tank2.resetHealth();}
                    powerUps.get(i).hide();
                }
                else if (powerUps.get(i).getType() == 1)
                {
                    tank2.upgrade_bullet();
                    powerUps.get(i).hide();
                }
            }
            if(!powerUps.get(i).getShow())
            {
                try {
                    powerUps.remove(i);
                    i--;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("No powerups to hide");
                }
            }
        }
    }
}
