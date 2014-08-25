package ca.vanzeben.ld30.lvl.layers.obj;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.vanzeben.ld30.ent.Animation;
import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.Images;

public class Painting extends BackgroundObject {

  private static ArrayList<BufferedImage[]> sprites;
  private static int[]                      NUM_FRAMES      = { 2, 8, 2, 2, 2, 2, 2 };
  private static int[]                      FRAME_WIDTHS    = { 10, 20, 108, 108, 100, 100, 100 };
  private static int[]                      FRAME_HEIGHTS   = { 10, 19, 30, 31, 30, 30, 30 };
  private static int[]                      SPRITE_DELAYS   = { 2, 10, -1, -1, -1, -1, -1 };
  public static int[]                       PAINTING_RARITY = { 70, 30, 0, 0, 0, 0, 0 };
  private Animation                         animation;
  private PaintingType                      type;

  public enum PaintingType {
    SMALL, MED, STAMINA_PAINTING, HEALTH_PAINTING, DOOR, WELCOME, PLAYER;
  }

  public Painting(World level, int x, int y, PaintingType type) {
    super(level, x, y, FRAME_WIDTHS[type.ordinal()], FRAME_HEIGHTS[type.ordinal()]);
    if (sprites == null) {
      BufferedImage spritesheet = Images.PAINTINGS;
      int count = 0;
      sprites = new ArrayList<BufferedImage[]>();
      for (int i = 0; i < NUM_FRAMES.length; i++) {
        BufferedImage[] bi = new BufferedImage[NUM_FRAMES[i]];
        for (int j = 0; j < NUM_FRAMES[i]; j++) {
          bi[j] = spritesheet.getSubimage(j * FRAME_WIDTHS[i], count, FRAME_WIDTHS[i], FRAME_HEIGHTS[i]);
        }
        sprites.add(bi);
        count += FRAME_HEIGHTS[i];
      }
    }
    animation = new Animation();
    setAnimation(type);
  }

  public void update() {
    animation.update();
  }

  private void setAnimation(PaintingType type) {
    this.type = type;
    animation.setFrames(sprites.get(type.ordinal()));
    animation.setDelay(SPRITE_DELAYS[type.ordinal()]);
    width = FRAME_WIDTHS[type.ordinal()];
    height = FRAME_HEIGHTS[type.ordinal()];
  }

  public BufferedImage getImage(LevelType t) {
    if (t == LevelType.DEATH_VALLEY && type.ordinal() > 1) { return sprites.get(type.ordinal())[1]; }
    if (t == LevelType.OVERWORLD) { return sprites.get(type.ordinal())[0]; }
    return animation.getImage();
  }

}
