package snakegame;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame(){
        super("Snake Game");//this method is always written on the first place
        add(new Board());//adding the code of board class on the frame
        pack();//reloads the frame, even if the frame is open and we change something in the code then also it reloads automatically.

        setSize(300, 300);
        setLocationRelativeTo(null);//set the frame to the center
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}
