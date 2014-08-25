package ca.vanzeben.ld30.lvl.layers;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.BaseGameClass;

public abstract class Layer extends BaseGameClass {
  protected final World world;
  public int            zIndex = 0;
  protected String      imagePath;
  public BufferedImage  image;
  public BufferedImage  deathWorldImage;

  public Layer(World world, int zIndex) {
    this.world = world;
    this.zIndex = zIndex;
    loadImage();
  }

  public abstract void loadImage();

  public BufferedImage getImage(LevelType t) {
    if (LevelType.DEATH_VALLEY == t) { return deathWorldImage; }
    return image;
  }

}
