package game;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ScoreBoard extends JPanel {
  private int score;
  private JLabel label;
  public ScoreBoard() {
    this.score = 0;
    this.label = new JLabel();
    this.label.setText("Score: " + this.score);
    super.add(this.label);
  }

  public void addScore(int score) {
    this.score += score;
    this.label.setText("Score: " + this.score);
  }
}
