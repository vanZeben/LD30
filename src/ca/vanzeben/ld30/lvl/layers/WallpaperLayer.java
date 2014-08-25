package ca.vanzeben.ld30.lvl.layers;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.utils.Images;

public class WallpaperLayer extends ParalaxLayer {

  public WallpaperLayer(World level, int zIndex, int width, int height) {
    super(level, zIndex, width, height);
  }

  public void loadImage() {
    this.image = Images.WALLPAPER;
    this.deathWorldImage = Images.WALLPAPER_HELL;
  }

}
