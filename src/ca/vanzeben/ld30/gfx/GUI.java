package ca.vanzeben.ld30.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.Images;

public class GUI extends BaseGameClass {

  private final World                world;
  private ArrayList<BufferedImage[]> sprites;
  private final int[]                NUM_FRAMES    = { 2 };
  private final int[]                FRAME_WIDTHS  = { 8 };
  private final int[]                FRAME_HEIGHTS = { 7 };
  private final int[]                SPRITE_DELAYS = { -1 };

  private int                        stamHeight    = 5;
  private int                        stamWidth     = 100;

  public GUI(World world) {
    this.world = world;
    load();
  }

  private void load() {
    BufferedImage spritesheet = Images.GUI;
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

  @Override public void update() {}

  @Override public void render(Graphics2D g) {
    for (int i = world.player.maxHealth; i > 0; i--) {
      if (world.player.health >= i) {
        g.drawImage(sprites.get(0)[0], 2 * (2 * i), 2, 8, 7, null);
      } else {
        g.drawImage(sprites.get(0)[1], 2 * (2 * i), 2, 8, 7, null);
      }
    }
    g.setColor(Color.WHITE);
    g.drawString(world.player.time + "", 2, world.getScreen(LevelType.OVERWORLD).height + 5);
    g.setColor(Color.BLACK);
    g.drawRect(world.getScreen(LevelType.OVERWORLD).width - stamWidth - 2, 2, stamWidth, stamHeight);
    if (world.player.stamina > 0) {
      g.fillRect(world.getScreen(LevelType.OVERWORLD).width - stamWidth - 2 + 2, 4, (int) Math.round(((double) world.player.stamina / (double) world.player.maxStamina) * stamWidth) - 3, stamHeight - 3);
    }
  }
}
