package ca.vanzeben.ld30.lvl.layers.obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.vanzeben.ld30.ent.Animation;
import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.Images;

public class Door extends BackgroundObject {

  private static ArrayList<BufferedImage[]> sprites;
  private static final int[]                NUM_FRAMES    = { 1, 1 };
  private static final int[]                FRAME_WIDTHS  = { 20, 20 };
  private static final int[]                FRAME_HEIGHTS = { 40, 40 };
  private static final int[]                SPRITE_DELAYS = { -1, -1 };
  private Animation                         animation;
  public DoorType                           type;

  public enum DoorType {
    OPEN, CLOSED;
  }

  public Door(World level, int x, int y, DoorType type) {
    super(level, x, y, FRAME_WIDTHS[type.ordinal()], FRAME_HEIGHTS[type.ordinal()]);
    if (sprites == null) {
      BufferedImage spritesheet = Images.DOORS;
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

  private void setAnimation(DoorType type) {
    this.type = type;
    animation.setFrames(sprites.get(type.ordinal()));
    animation.setDelay(SPRITE_DELAYS[type.ordinal()]);
    width = FRAME_WIDTHS[type.ordinal()];
    height = FRAME_HEIGHTS[type.ordinal()];
  }

  public BufferedImage getImage(LevelType t) {
    if (t == LevelType.DEATH_VALLEY) { return sprites.get(DoorType.CLOSED.ordinal())[0]; }
    return animation.getImage();
  }

  @Override public void render(Graphics2D g) {
    if (inFrame()) {
      g.drawImage(getImage(LevelType.OVERWORLD), (-world.getScreen(LevelType.OVERWORLD).cam.getX()) + pos.getFloorX(), world.getScreen(LevelType.OVERWORLD).yOffs
          + (world.getScreen(LevelType.OVERWORLD).cam.getY() + world.getScreen(LevelType.OVERWORLD).height - height) + pos.getFloorY(), width, height, null);
    }
  }
}
