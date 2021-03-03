/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tavuk;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author berat
 */
public class GamePlay
        extends JPanel
        implements KeyListener, ActionListener {
    
    private Asset chicken;
    private Asset wheat;
    private Asset cat;
    private Asset egg;
    private Basket basket;   

    private double backgroundY;
    private boolean play;
    private int score;
    private String score_str;
    private int level_num;
    private String level;
    private String egg_count;
    private double ballYdir;
    private double speed;
    private int egg_c;
    private final int dimension;

    private boolean is_egg_in_bottom;
    private boolean is_in_top;
    private boolean is_cat_in_top;
    private boolean is_basket_in_top;
    private boolean is_dead;
    private boolean is_egg_fired;
    private boolean is_fire_button_pushed;
    private boolean is_catched;
    private boolean is_started;

    private BufferedImage background;

    JLabel label;
    Timer timer;

    public GamePlay() {
       
        chicken = new Asset(10, 50, "pics/chicken-icon.png");
        wheat = new Asset((int) (Math.random() * 181.0D) + 10, 600, "pics/bugday.png");
        cat = new Asset(155, -60, "pics/caticon.png");
        egg = new Asset(600, 50, "pics/egg.png");

        basket = new Basket(155, -60);

        this.dimension = 50;
        this.label = new JLabel("My label");
        this.is_started = false;
        this.is_catched = false;
        this.is_fire_button_pushed = false;
        this.is_egg_fired = false;
        this.level = String.valueOf(level_num);
        this.level_num = 0;
        this.is_dead = false;
        this.is_basket_in_top = false;
        this.is_cat_in_top = false;
        this.is_in_top = false;
        this.is_egg_in_bottom = false;
        this.speed = -0.1;
        this.ballYdir = -0.1;

        this.egg_c = 3;
        this.egg_count = String.valueOf(egg_c);

        this.backgroundY = 0;
        this.score_str = String.valueOf(score);
        this.score = 0;
        this.play = false;

        try {
            background = ImageIO.read(new File(OsUtils.fixFilePath("pics/sky.png")));
        } catch (IOException localIOException) {
            System.err.println(localIOException.toString());
        }

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(1, this);
        timer.start();
    }

    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1024, 1024);
        g.drawImage(background, 0, (int) backgroundY, null);
        g.drawImage(background, 0, (int) backgroundY + 600, null);
        if (is_started == false) {
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("default", 1, 25));
            g.drawString("Press Enter To", 40, 275);
            g.drawString("START", 100, 300);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.setFont(new Font("default", 1, 14));
            g.drawString("Level: ", 10, 30);
            g.drawString(level, 60, 30);

            g.drawImage(chicken.getImage(), chicken.X, (int) chicken.Y, dimension, dimension, null);

            g.drawImage(egg.getImage(), egg.X, (int) egg.Y, dimension-20, dimension-20, null);

            g.drawImage(wheat.getImage(), wheat.X, (int) wheat.Y, dimension, dimension, null);

            g.drawImage(cat.getImage(), cat.X, (int) cat.Y, dimension, dimension, null);

            g.drawImage(basket.getImage(), basket.X, (int) basket.Y, dimension, dimension, null);

            g.drawString("Eggs: ", 220, 30);
            g.drawString(egg_count, 267, 30);

            g.drawString("Score: ", 100, 30);
            g.drawString(score_str, 155, 30);

            if (is_dead) {
                g.setFont(new Font("default", 1, 25));
                g.drawString("YOU ARE DEAD", 50, 275);
                g.setFont(new Font("default", 1, 15));
                g.drawString("Score:", 110, 300);
                g.drawString(score_str, 170, 300);
                g.drawString("Press Enter to Play Again.", 57, 325);
            }
        }

        g.dispose();
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        timer.start();
        if (play) {
            // Wheat intersects with chicken
            if ((new Rectangle(wheat.X, (int) wheat.Y, dimension, dimension)
                    .intersects(new Rectangle(chicken.X, (int) chicken.Y, dimension, dimension)))
                    && (!is_dead)) {
                wheat.X = 1000;
                if (egg_c < 10) {
                    egg_c += 3;
                    if (egg_c > 10) {
                        egg_c = 10;
                    }
                    egg_count = String.valueOf(egg_c);
                }
            }

            //Cat intersects with chicken
            if (((new Rectangle(cat.X, (int) cat.Y, dimension, dimension)
                    .intersects(new Rectangle(chicken.X, (int) chicken.Y, dimension, dimension)))
                    && (!is_catched)) || (is_dead)) {
                is_dead = true;
                is_catched = true;
                egg.X = 1000;
                chicken.Y += 5;
            }
            if (!is_dead) {
                //Egg intersects with basket
                if (new Rectangle(basket.X, (int) basket.Y, dimension, dimension)
                        .intersects(new Rectangle(egg.X, (int) egg.Y, dimension-20, dimension-20))) {

                    basket.X = 600;
                    score += basket.getPoint();
                    score_str = String.valueOf(score);

                    level_num = (int) score / 10;
                    level = String.valueOf(level_num);
                    ballYdir = speed * (level_num + 1);

                    basket.resetBasket();
                    egg.X = 1000;
                    is_egg_fired = false;
                    is_fire_button_pushed = false;
                    is_egg_in_bottom = false;
                }

                if (backgroundY > -600) {
                    backgroundY += -0.5;
                } else {
                    backgroundY = 0;
                }
                if (is_egg_fired) {
                    egg.Y += 4;
                    if (egg.Y > 600) {
                        is_egg_in_bottom = true;
                    }
                    if (is_egg_in_bottom) {
                        egg.Y = 999;
                        is_egg_fired = false;
                        is_fire_button_pushed = false;
                        is_egg_in_bottom = false;
                    }
                }

                if (!is_basket_in_top) {
                    basket.Y += ballYdir;
                    if (basket.Y < 3) {
                        is_basket_in_top = true;
                    }
                    if (is_basket_in_top) {
                        is_basket_in_top = false;
                        basket.X = ((int) (Math.random() * 181.0D) + 10);
                        basket.Y = 600;
                    }
                }

                if (!is_cat_in_top) {
                    cat.Y += ballYdir;
                    if (cat.Y < -30) {
                        is_cat_in_top = true;
                    }
                    if (is_cat_in_top) {
                        is_catched = false;
                        is_cat_in_top = false;
                        cat.X = ((int) (Math.random() * 186.0D) + 5);
                        cat.Y = 600;
                    }
                }

                if (!is_in_top) {
                    wheat.Y += ballYdir;
                    if (wheat.Y < 0) {
                        is_in_top = true;
                    }

                    if (is_in_top) {
                        is_in_top = false;
                        wheat.X = ((int) (Math.random() * 181.0D) + 10);
                        wheat.Y = 600;
                    }
                }
            }
        }

        repaint();
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void keyPressed(KeyEvent arg0) {

        // right arrow pressed
        if ((arg0.getKeyCode() == 39) && (!is_dead) && is_started) {
            if (chicken.X >= 240) {
                chicken.X = 240;
            } else {
                moveRight();
            }
        }

        // left arrow pressed
        if ((arg0.getKeyCode() == 37) && (!is_dead) && is_started) {
            if (chicken.X <= 10) {
                chicken.X = 10;
            } else {
                moveLeft();
            }
        }

        // spacebar pressed
        if ((arg0.getKeyCode() == 32) && (!is_dead) && is_started) {

            if ((!is_fire_button_pushed) && (egg_c > 0)) {
                egg.X = chicken.X;
                egg.Y = chicken.Y;
                egg_c -= 1;
                egg_count = String.valueOf(egg_c);
                is_egg_fired = true;
                is_fire_button_pushed = true;
            }

            // ENTER for death
        } else if ((arg0.getKeyCode() == 10) && (is_dead)) {

            is_dead = false;
            score = 0;
            egg_c = 3;
            level_num = 0;
            chicken.X = 10;
            chicken.Y = 50;
            backgroundY = 0;
            wheat.X = ((int) (Math.random() * 181.0D) + 10);
            wheat.Y = 600;
            egg.X = 600;
            egg.Y = chicken.Y;
            basket.X = 155;
            basket.Y = -60;
            cat.X = 155;
            cat.Y = -60;
            ballYdir = -0.1;
            score_str = String.valueOf(score);
            level = String.valueOf(level_num);
            egg_count = String.valueOf(egg_c);

            //ENTER for start
        } else if ((arg0.getKeyCode() == 10) && (!is_dead) && !is_started) {

            is_started = true;
        }
    }

    public void moveRight() {
        play = true;
        chicken.X += 20;
    }

    public void moveLeft() {
        play = true;
        chicken.X -= 20;
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    /**
     *
     * @param arg0
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
    }
    
    
}


