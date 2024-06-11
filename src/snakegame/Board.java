package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//JFrame is like a parent div, and JPanel is like a small div's inside it.
public class Board extends JPanel implements ActionListener { //now Board class is a component....Panel

    //Images.............................
    private Image apple;
    private Image head;
    private Image dot;
    private int dots;

    private boolean inGame = true;

    //apple position..............................
    private int random = 29;
    private int apple_x;
    private int apple_y;

    //direction..............................
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = true;
    private boolean downDirection = true;

    //for declaring the positions of dots ...........................
    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;

    private final int x[]  = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private Timer timer;
    private int score = 0;



    //default constructor ...(automatically calls in the main method of the SnakeGame.java as there is object is created)
    Board(){
        //addKeyListener is the function of ActionListener interface
        addKeyListener(new TAdapter());

        setBackground(Color.BLACK);
        setFocusable(true);

        loadImages();
        initGame();
    }

    //setting images to the particula veriables
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png"));
        head = i3.getImage();
    }

    public void initGame(){
        dots = 3;
        //this loop is responsible for the initial position of snakes (it's head and it's two dots)
        for(int i=0; i<dots; i++){
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }

        locateApple();

        timer = new Timer(140, this);
        timer.start();

    }

    //randomly places apple in any position
    public void locateApple(){
        int r = (int)(Math.random() * random);
        apple_x = r * DOT_SIZE;

        r = (int)(Math.random() * random);
        apple_y = r * DOT_SIZE;
    }

    //to paint the things on the JFrame
    public void paintComponent(Graphics g/* object of graphics class*/){//method fromm graphics class
        super.paintComponent(g);//calls the parent component
        draw(g);
    }

    //draw all the graphics on the JFrame
    public void draw(Graphics g){
        if(inGame) {
            //draw apple
            g.drawImage(apple, apple_x, apple_y, this);

            //draw snake
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            drawScore(g);
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);

        }
    }

    private void drawScore(Graphics g) {
        String scoreText = "Score: " + score;
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(scoreText, 10, 20);
    }

    public void gameOver(Graphics g){
        String str = "Game Over !  Score : " + score;
        Font font = new Font("SAN_SERIF", Font.BOLD, 19);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(str, (300-metrices.stringWidth((str)))/2, 300/2);
    }

    public void move(){
        for(int i=dots ;i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        if(rightDirection) {
            x[0] += DOT_SIZE;
        }
        if(leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots += 1;
            score += 1;
            locateApple();
        }
    }

    public void checkCollision(){
        for(int i = dots; i > 0; i--){
            if((i>4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }

        if((y[0] >=300) || (y[0] < 0) || (x[0] >= 300) || (x[0] < 0)){
            inGame = false;
        }

        if(!inGame){
            timer.stop();
        }
    }


    public void actionPerformed(ActionEvent ae){
        if(inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    //inner class.............................
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

}
