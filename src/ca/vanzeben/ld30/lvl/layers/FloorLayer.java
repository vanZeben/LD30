package ca.vanzeben.ld30.lvl.layers;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.utils.Images;

public class FloorLayer extends ParalaxLayer {

  public FloorLayer(World level, int zIndex, int width, int height) {
    super(level, zIndex, width, height);
  }

  public void loadImage() {
    this.image = Images.FLOOR;
    this.deathWorldImage = Images.FLOOR_HELL;
  }

}
