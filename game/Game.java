package game;

import javax.swing.JPanel;
import java.awt.event.KeyListener;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

public class Game extends JPanel implements KeyListener {
  private Board board;
  private BlockHolder blockholder;
  private ScoreBoard scoreBoard;

  public Game() {
    this.board = new Board(10, 10);
    this.blockholder = new BlockHolder();
    this.scoreBoard = new ScoreBoard(); 
    this.blockholder.generate();

    super.setFocusable(true);
    super.requestFocusInWindow();
    super.addKeyListener(this);

    super.setLayout(new BorderLayout());
    super.add(scoreBoard, BorderLayout.NORTH);
    super.add(board, BorderLayout.CENTER);
    super.add(blockholder, BorderLayout.SOUTH);
    super.setBorder(new EmptyBorder(10, 10, 10, 10));
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
      case KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> {
        this.board.next(key);
      }

      case KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3 -> {
        this.board.setBlock(this.blockholder.getBlock(key - KeyEvent.VK_1));
      }

      case KeyEvent.VK_SPACE -> {
        if (this.board.placeable()) {
          Block block = this.board.place();
          this.blockholder.hideBlock(block);
          this.blockholder.generate();
          int points = this.board.clear();
          this.scoreBoard.addScore(points);
        }
      }

      case KeyEvent.VK_ESCAPE -> {
        this.board.setBlock(null);
      }
    }
  }
}
