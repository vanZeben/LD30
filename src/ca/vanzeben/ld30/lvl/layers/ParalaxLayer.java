package ca.vanzeben.ld30.lvl.layers;

import java.awt.Graphics2D;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.Images;

public abstract class ParalaxLayer extends Layer {
  private int width;
  private int height;

  public ParalaxLayer(World level, int zIndex, int width, int height) {
    super(level, zIndex);
    this.width = width;
    this.height = height;
  }

  @Override public void update() {}

  @Override public void render(Graphics2D g) {
    for (LevelType t : LevelType.values()) {
      int index = world.getScreen(t).cam.getX() / width;
      for (int i = 0; i <= (world.getScreen(t).width + 10) / width; i++) {
        g.drawImage(getImage(t), width * (index + i) + (-world.getScreen(t).cam.getX()), world.getScreen(t).yOffs, width, height, null);
        g.drawImage(getImage(t), width * (index - i) + (-world.getScreen(t).cam.getX()), world.getScreen(t).yOffs, width, height, null);
      }
    }
  }
}
