package game;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BlockHolder extends JPanel {
  private static final int HSIZE = 3, ROTATION = 4;
  private BlockFactoryMethod[] generator;
  private Block[] holder = { null, null, null };

  public BlockHolder() {
    BlockFactory factory = new BlockFactory();
    this.generator = new BlockFactoryMethod[]{
      factory::dot,
      factory::square2x2,
      factory::square3x3,
      factory::line2,
      factory::line3,
      factory::line4,
      factory::line5,
      factory::l3,
      factory::l5
    };

    super.setBorder(new EmptyBorder(10, 0, 0, 0));
    super.setLayout(new GridLayout(1, HSIZE, 0, 3));
  }

  public void generate() {
    if (!this.allHidden()) {
      return;
    }
    super.removeAll();
    for (int i = 0; i < HSIZE; i++) {
      int blockMethod = this.randomize(0, this.generator.length - 1);
      int numRotation = this.randomize(0, ROTATION);
      boolean reflect = this.randomize(0, 1) == 1;
      this.holder[i] = this.generator[blockMethod].generator(numRotation, reflect);
      super.add(this.holder[i]);
    }
  }

  private int randomize(int low, int high) {
    return (int) Math.floor(Math.random() * (high - low + 1) + low);
  }

  public Block getBlock(int idx) {
    if (idx < 0 || idx >= HSIZE) {
      throw new IndexOutOfBoundsException("Tried to access index " + idx + " for " + HSIZE + " pieces");
    }
    Block block = this.holder[idx];
    if (block.isVisible()) {
      block.resetCoordinates();
      return block;
    } else {
      return null;
    }
  }

  public void hideBlock(Block block) {
    for (Block b : this.holder) {
      if (b == block) {
        b.setVisible(false);
      }
    }
  }

  private boolean allHidden() {
    for (Block block : this.holder) {
      if (block != null && block.isVisible()) {
        return false;
      }
    }
    return true;
  }
}
