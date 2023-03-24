package game;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel {
  private final Color AVAILABLE = Color.WHITE;
  private int rows, cols; 
  private JPanel[][] board;
  private Color[][] state, copy;
  Block block = null;
  
  public Board(int rows, int cols) {
    super.setLayout(new GridLayout(rows, cols, 3, 3));
    this.rows = rows;
    this.cols = cols;
    this.board = new JPanel[rows][cols];
    this.state = new Color[rows][cols];
    this.copy = new Color[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        JPanel tile = new JPanel();
        tile.setBackground(this.AVAILABLE);
        tile.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, false));
        tile.setSize(1, 1);
        super.add(tile);
        this.board[i][j] = tile;
        this.state[i][j] = this.AVAILABLE;
        this.copy[i][j] = this.AVAILABLE;
      }
    }
  }

  public void setBlock(Block block) {
    this.block = block;
    this.paint();
  }

  public void next(int key) {
    if (this.block == null) {
      return;
    }

    switch (key) {
      case KeyEvent.VK_UP -> {
        this.block.shift(-1, 0);
        if (!this.moveable()) {
          this.next(KeyEvent.VK_DOWN);
        }
      }

      case KeyEvent.VK_DOWN -> {
        this.block.shift(1, 0);
        if (!this.moveable()) {
          this.next(KeyEvent.VK_UP);
        }
      }

      case KeyEvent.VK_LEFT -> {
        this.block.shift(0, -1);
        if (!this.moveable()) {
          this.next(KeyEvent.VK_RIGHT);
        }
      }

      case KeyEvent.VK_RIGHT -> {
        this.block.shift(0, 1);
        if (!this.moveable()) {
          this.next(KeyEvent.VK_LEFT);
        }
      }
    }

    this.paint();
    return;
  }

  public Block place() {
    if (this.block == null) {
      return null;
    }
    ArrayList<Integer[]> coordinates = block.getCoordinates();
    for (Integer[] position : coordinates) {
      int x = position[0], y = position[1];
      this.state[x][y] = this.block.getColor();
    }

    Block placedBlock = this.block;
    this.block = null;
    this.paint();
    return placedBlock;
  }

  private boolean moveable() {
    if (this.block == null) {
      return false;
    }
    ArrayList<Integer[]> coordinates = block.getCoordinates();
    for (Integer[] position : coordinates) {
      int x = position[0], y = position[1];
      if (x < 0 || x >= this.rows || y < 0 || y >= this.cols) {
        return false;
      }
      this.copy[x][y] = block.getColor();
    }
    return true;
  }

  public boolean placeable() {
    if (this.block == null) {
      return false;
    }
    ArrayList<Integer[]> coordinates = block.getCoordinates();
    for (Integer[] position : coordinates) {
      int x = position[0], y = position[1];
      if (!this.state[x][y].equals(AVAILABLE)) {
        return false;
      }
    }
    return true;
  }

  public int clear() {
    boolean[][] clearable = new boolean[this.rows][this.cols];

    for (int i = 0; i < this.rows; i++) {
      Color[] row = this.state[i];
      boolean isCleared = Arrays.stream(row).allMatch(c -> !c.equals(AVAILABLE));
      if (isCleared) {
        Arrays.fill(clearable[i], true);
      }
    }

    for (int i = 0; i < this.cols; i++) {
      int idx = i;
      Color[] col = Arrays.stream(this.state).map(row -> row[idx]).toArray(Color[]::new);
      boolean isCleared = Arrays.stream(col).allMatch(c -> !c.equals(AVAILABLE));
      if (isCleared) {
        Arrays.stream(clearable).forEach(row -> row[idx] = true);
      }
    }

    int points = 0;
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        if (clearable[i][j]) {
          points += 1;
          this.board[i][j].setBackground(AVAILABLE);
          this.state[i][j] = AVAILABLE;
        }
      }
    }

    return points;
  }

  private void paint() {
    if (this.block == null) {
      return;
    }
    this.copy = this.deepCopy(this.state);
    ArrayList<Integer[]> coordinates = this.block.getCoordinates();
    for (Integer[] position : coordinates) {
      int x = position[0], y = position[1];
      this.copy[x][y] = this.block.getColor();
    }

    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.cols; j++) {
        this.board[i][j].setBackground(this.copy[i][j]);
      }
    }
  }

  private Color[][] deepCopy(Color[][] original) {
    Color[][] copy = new Color[original.length][];
    for (int i = 0; i < original.length; i++) {
      copy[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return copy;
  }
}
