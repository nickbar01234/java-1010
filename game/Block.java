package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class Block extends JPanel {
  final public static int CENTER_X = 2, CENTER_Y = 2, DIMENSION = 5;
  final private Color color; 
  private String name;
  private JPanel[][] grid;
  private ArrayList<Integer[]> coordinates;

  public Block(String name, Color color) {
    this.name = name;
    this.color = color;
    this.grid = new JPanel[DIMENSION][DIMENSION];
    this.coordinates = new ArrayList<>();

    super.setLayout(new GridLayout(DIMENSION, DIMENSION, 1, 1));
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        JPanel tile = new JPanel();
        this.grid[i][j] = tile;
        tile.setSize(1, 1);
        super.add(tile);
      }
    }
  }

  public void paint(int[][] coordinates) {
    for (int[] pos : coordinates) {
      paint(pos[0], pos[1]);
    }
  }

  public void paint(int x, int y) {
    if (x < 0 || x >= DIMENSION || y < 0 || y >= DIMENSION) {
      throw new IndexOutOfBoundsException("Coordinate (" + x + "," + y + ")out of bound");
    }
    this.grid[x][y].setBackground(this.color);
    this.coordinates.add(new Integer[]{x, y});
  }

  public void shift(int x, int y) {
    for (Integer[] position : this.coordinates) {
      position[0] += x;
      position[1] += y;
    }
  } 

  public Color getColor() {
    return this.color;
  }

  public void resetCoordinates() {
    this.coordinates = new ArrayList<>();
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        if (this.grid[i][j].getBackground().equals(this.color)) {
          this.coordinates.add(new Integer[]{i, j});
        }
      }
    }
  }

  public ArrayList<Integer[]> getCoordinates() {
    return this.coordinates;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append(name + ": ");
    for (Integer[] position : this.coordinates) {
      string.append(Arrays.toString(position));
      string.append(" ");
    }
    return string.toString();
  }
}
