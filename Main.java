import java.awt.Dimension;

import javax.swing.JFrame;
import game.Game;

public class Main extends JFrame {
  public static void main(String args[]) {
    Game game = new Game();
    JFrame frame = new JFrame("1010!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(game);
    frame.getContentPane().setMinimumSize(new Dimension(250, 300));
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
  } 
}
