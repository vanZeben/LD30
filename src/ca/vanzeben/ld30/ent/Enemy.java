package ca.vanzeben.ld30.ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.vanzeben.ld30.gfx.Screen;
import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.Images;
import ca.vanzeben.ld30.utils.Vector2D;

public class Enemy extends Entity {

  public int                         health;
  public int                         maxHealth;

  private ArrayList<BufferedImage[]> sprites;
  private final int[]                NUM_FRAMES    = { 3, 6, 2, 1 };
  private final int[]                FRAME_WIDTHS  = { 16, 16, 16, 16 };
  private final int[]                FRAME_HEIGHTS = { 26, 26, 26, 28 };
  private final int[]                SPRITE_DELAYS = { -1, 2, 6, -1 };
  private boolean                    walkingLeft   = false;
  private boolean                    walkingRight  = false;
  private Vector2D                   homePos;
  private static final int           MAX_DIST      = 100;

  public enum EnemyType {
    BASE, BASE2;
  }

  public Enemy(World world, EnemyType type, Vector2D pos) {
    super(world);
    moveSpeed = 1.6F;
    maxSpeed = 1.6F;
    health = maxHealth = 5;
    this.pos = pos;
    this.homePos = pos.clone();
    BufferedImage spritesheet = Images.ENEMIES;
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
    setAnimation(type.ordinal());
  }

  private void setAnimation(int i) {
    currentAction = i;
    animation.setFrames(sprites.get(currentAction));
    animation.setDelay(SPRITE_DELAYS[currentAction]);
    width = FRAME_WIDTHS[currentAction];
    height = FRAME_HEIGHTS[currentAction];
  }

  private void getNextPosition() {
    if (!walking) {
      vel.set(0F, 0F);
    } else {
      if (walkingLeft) {
        vel.set(moveSpeed, 0F);
        walking = true;
      } else {
        vel.set(-moveSpeed, 0F);
        walking = true;
      }
    }
  }

  private void walk(int dir) {
    switch (dir) {
      default:
      case 0:
        walking = false;
        walkingLeft = false;
        walkingRight = false;
        break;
      case 1:
        walking = true;
        walkingRight = false;
        walkingLeft = true;
        break;
      case 2:
        walking = true;
        walkingRight = true;
        walkingLeft = false;
        break;
    }
  }

  private void changeDir() {
    int chance = random.nextInt(100 - 1) + 1;
    if (pos.getFloorX() <= homePos.getFloorX() - MAX_DIST) {
      walk(1);
      return;
    } else if (pos.getFloorX() >= homePos.getFloorX() + MAX_DIST) {
      walk(2);
      return;
    }

    if (chance > 70) {
      walk(2);
    } else if (chance > 40) {
      walk(1);
    } else {
      walk(0);
    }
  }

  @Override public void update() {
    if (!world.started) { return; }
    if (random.nextBoolean() && random.nextBoolean() && random.nextInt(100 - 1) + 1 > 60) {
      changeDir();
    }
    updateCollision();
    getNextPosition();

    if (vel.getX() == 0) pos.setX(pos.getFloorX());

    pos.add(vel);
    vel.set(0, 0);
    animation.update();
  }

  private BufferedImage getImage(LevelType t) {
    return animation.getImage();
  }

  @Override public void render(Graphics2D g) {
    Screen screen = world.getScreen(LevelType.DEATH_VALLEY);
    g.drawImage(getImage(screen.level.type), (-screen.cam.getX()) + pos.getFloorX(), screen.yOffs + (screen.cam.getY() + screen.height - height) + pos.getFloorY(), width, height, null);
  }
}
