package game;

import java.awt.Color;

interface BlockFactoryMethod {
  Block generator(int numRotation, boolean reflect);
}

public class BlockFactory {
  public Block dot(int numRotation, boolean reflect) {
    Block block = new Block("dot", Color.LIGHT_GRAY);
    block.paint(2, 2);
    return block;
  }
  public Block square2x2(int numRotation, boolean reflect) {
    Block block = new Block("2x2 square", Color.GREEN);
    block.paint(new int[][] {{1, 1}, {1, 2}, {2, 1}, {2, 2}});
    return block;
  }

  public Block square3x3(int numRotation, boolean reflect) {
    Block block = new Block("3x3 square", Color.CYAN);
    for (int i = Block.DIMENSION - 4; i < Block.DIMENSION - 1; i++) {
      for (int j = 1; j < Block.DIMENSION - 1; j++) {
        block.paint(i, j);
      }
    }
    return block;
  }

  public Block line2(int numRotation, boolean reflect) {
    Block block = new Block("line 2", Color.YELLOW);
    int[][] coordinates = {{2, 2}, {3, 2}};
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  public Block line3(int numRotation, boolean reflect) {
    Block block = new Block("line 3", Color.ORANGE);
    int[][] coordinates = {{2, 1}, {2, 2}, {2, 3}};
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  public Block line4(int numRotation, boolean reflect) {
    Block block = new Block("line 4", Color.MAGENTA);
    int[][] coordinates = {{1, 2}, {2, 2}, {3, 2}, {4, 2}};
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  public Block line5(int numRotation, boolean reflect) {
    Block block = new Block("line 5", Color.RED);
    int[][] coordinates = {{0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}};
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  public Block l3(int numRotation, boolean reflect) {
    Block block = new Block("L3", Color.MAGENTA);
    int[][] coordinates = {{1, 2}, {2, 2}, {2, 1}};
    if (reflect) {
      coordinates = this.reflect(coordinates);
    }
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  public Block l5(int numRotation, boolean reflect) {
    Block block = new Block("L5", Color.BLUE);
    int[][] coordinates = {{0, 2}, {1, 2}, {2, 2}, {2, 1}, {2, 0}};
    if (reflect) {
      coordinates = this.reflect(coordinates);
    }
    block.paint(this.rotate(coordinates, numRotation));
    return block;
  }

  private int[][] reflect(int[][] coordinates) {
    for (int i = 0; i < coordinates.length; i++) {
      int row[] = coordinates[i];
      row[0] = Block.CENTER_X - (row[0] - Block.CENTER_Y);
    }
    return coordinates;
  }

  private int[][] rotate(int[][] coordinates, int numRotation) {
    for (int i = 0; i < coordinates.length; i++) {
      int[] row = coordinates[i];
      for (int j = 0; j < numRotation; j++) {
        int x = row[0], y = row[1];
        row[0] = Block.CENTER_X - (y - Block.CENTER_Y);
        row[1] = Block.CENTER_Y + (x - Block.CENTER_X);
      }
    }
    return coordinates;
  }
}
