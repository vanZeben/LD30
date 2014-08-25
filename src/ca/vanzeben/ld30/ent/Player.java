package ca.vanzeben.ld30.ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ca.vanzeben.ld30.Game;
import ca.vanzeben.ld30.gfx.Screen;
import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.input.InputController.KeyChars;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.Images;
import ca.vanzeben.ld30.utils.JukeBox;

public class Player extends Entity {

  public int                         health;
  public int                         maxHealth;
  public long                        time;
  public int                         stamina;
  public int                         maxStamina;
  public boolean                     isHiding          = false;
  public boolean                     sitting           = false;
  public boolean                     isDamaged         = false;
  public boolean                     isInOverworld     = true;
  private long                       lastStaminaLost   = 0;
  private long                       lastStaminaGained = 0;

  private ArrayList<BufferedImage[]> sprites;
  private ArrayList<BufferedImage[]> ghostSprites;
  private final int[]                NUM_FRAMES        = { 1, 6, 2, 1 };
  private final int[]                FRAME_WIDTHS      = { 16, 16, 16, 16 };
  private final int[]                FRAME_HEIGHTS     = { 26, 26, 26, 28 };
  private final int[]                SPRITE_DELAYS     = { -1, 2, 6, -1 };

  private static final int           IDLE              = 0;
  private static final int           WALKING           = 1;
  private static final int           HIDING            = 2;
  private static final int           SITTING           = 3;
  private long                       lastToggle        = 0;
  private long                       lastHit           = 0;

  public Player(World world) {
    super(world);
    moveSpeed = 1.6F;
    maxSpeed = 1.6F;
    health = maxHealth = 5;
    stamina = maxStamina = 20;

    BufferedImage spritesheet = Images.PLAYER;
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
    spritesheet = Images.GHOST;
    count = 0;
    ghostSprites = new ArrayList<BufferedImage[]>();
    for (int i = 0; i < NUM_FRAMES.length; i++) {
      BufferedImage[] bi = new BufferedImage[NUM_FRAMES[i]];
      for (int j = 0; j < NUM_FRAMES[i]; j++) {
        bi[j] = spritesheet.getSubimage(j * FRAME_WIDTHS[i], count, FRAME_WIDTHS[i], FRAME_HEIGHTS[i]);
      }
      ghostSprites.add(bi);
      count += FRAME_HEIGHTS[i];
    }
    setAnimation(SITTING);
    JukeBox.load("/sfx/charging.mp3", "charging");
    JukeBox.load("/sfx/playerhit.mp3", "playerhit");
    JukeBox.load("/sfx/hide.mp3", "hide");
  }

  private void setAnimation(int i) {
    currentAction = i;
    animation.setFrames(sprites.get(currentAction));
    animation.setDelay(SPRITE_DELAYS[currentAction]);
    width = FRAME_WIDTHS[currentAction];
    height = FRAME_HEIGHTS[currentAction];
  }

  private void getNextPosition() {
    if (!world.started) {
      vel.set(0F, 0F);
      walking = false;
      isHiding = false;
      sitting = true;
    } else {
      sitting = false;
      walking = true;
    }
    if (isInOverworld && (System.currentTimeMillis() - lastToggle > 500L) && Game.inputController.has(KeyChars.ENTER) && world.getLevel(LevelType.OVERWORLD).inDoor()) {
      lastToggle = System.currentTimeMillis();
      if (!isHiding) {
        JukeBox.play("hide");
        vel.set(0F, 0F);
        walking = false;
        isHiding = true;
      } else {
        walking = true;
        isHiding = false;
      }
    }
    if (!isHiding && !sitting) {
      vel.add(moveSpeed, 0F);
      walking = true;
    }
  }

  private long    damageLength        = 0;

  private long    damageHitAnimLength = 0;
  private boolean damageHitReverse    = false;

  @Override public void update() {
    if (walking && !isHiding) {
      time++;
    }
    if (currentAction == HIDING) {
      if (stamina < maxStamina && System.currentTimeMillis() - lastStaminaGained >= 2500) {
        if (isInOverworld) {
          stamina++;
          lastStaminaGained = System.currentTimeMillis();
        }
      }
    } else {
      if (Game.inputController.getPressedKeys().contains(KeyChars.UP)) {
        if (stamina > 0) {
          if (isInOverworld) {
            isInOverworld = false;
          } else if (System.currentTimeMillis() - lastStaminaLost >= 500) {
            stamina--;
            lastStaminaLost = System.currentTimeMillis();
          }
        }
      } else {
        if (!isInOverworld) {
          isInOverworld = true;
        }
      }
    }
    if (stamina <= 0 && !isInOverworld) {
      isInOverworld = true;
    }
    if (!isInOverworld && !loopingCharge) {
      JukeBox.loop("charging");
      loopingCharge = true;
    } else if (isInOverworld && loopingCharge) {
      JukeBox.stop("charging");
      loopingCharge = false;
    }

    if (currentAction != HIDING && checkCollision()) {
      isDamaged = true;
      damageLength = System.currentTimeMillis();
      health--;
      if (health <= 0) {
        JukeBox.play("playerhit");
        JukeBox.play("playerhit");
        JukeBox.play("playerhit");
        Game.instance.newWorld();
      } else {
        JukeBox.play("playerhit");
      }
    }

    if (isDamaged) {
      if (System.currentTimeMillis() - damageHitAnimLength > 25) {
        damageHitReverse = !damageHitReverse;
        damageHitAnimLength = System.currentTimeMillis();
      }
      if (System.currentTimeMillis() - damageLength > 750) {
        damageLength = System.currentTimeMillis();
        isDamaged = false;
        damageHitReverse = false;
        damageHitAnimLength = 0;
      }
    }
    getNextPosition();

    if (vel.getX() == 0) pos.setX(pos.getFloorX());

    if (sitting) {
      if (currentAction != SITTING) {
        setAnimation(SITTING);
      }
    } else if (isHiding) {
      if (currentAction != HIDING) {
        setAnimation(HIDING);
      }
    } else if (walking) {
      if (currentAction != WALKING) {
        setAnimation(WALKING);
      }
    } else {
      if (currentAction != IDLE) {
        setAnimation(IDLE);
      }
    }
    pos.add(vel);
    vel.set(0, 0);
    animation.update();
  }

  private boolean loopingCharge = false;

  public void updateCollision() {
    getCollisionBox().setBounds(pos.getFloorX() + (world.getScreen(LevelType.OVERWORLD).width / 2) + padding, pos.getFloorY(), width - (padding * 2), height);
  }

  private boolean checkCollision() {
    if (isInOverworld && System.currentTimeMillis() - lastHit > 500L) {
      lastHit = System.currentTimeMillis();
      updateCollision();
      for (Entity e : world.enemies) {
        if (e.getCollisionBox().intersects(getCollisionBox())) { return true; }
      }
    }
    return false;
  }

  private BufferedImage getImage(LevelType t) {
    if (isInOverworld) {
      if (damageHitReverse) {
        if (t == LevelType.OVERWORLD) { return ghostSprites.get(currentAction)[animation.getFrame()]; }
        return animation.getImage();
      } else {
        if (t == LevelType.DEATH_VALLEY) { return ghostSprites.get(currentAction)[animation.getFrame()]; }
        return animation.getImage();
      }
    } else {
      if (t == LevelType.DEATH_VALLEY) { return animation.getImage(); }
      return ghostSprites.get(currentAction)[animation.getFrame()];
    }
  }

  @Override public void render(Graphics2D g) {
    for (Screen screen : world.screens) {
      g.drawImage(getImage(screen.level.type), (int) (screen.width / 2 - width / 2) + screen.xOffs, (int) (screen.height - screen.level.levelBase - height) + screen.yOffs, null);
    }
  }
}
