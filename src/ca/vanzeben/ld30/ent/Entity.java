package ca.vanzeben.ld30.ent;

import java.awt.Rectangle;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.Vector2D;

public abstract class Entity extends BaseGameClass {

  public static final float MOVEMENT_SPEED = 1.0F;

  protected final World     world;
  protected int             stepCount      = 0;
  protected Vector2D        pos            = new Vector2D(0.0F, 0.0f);
  protected Vector2D        vel            = new Vector2D(1.0f, 0.0f);
  protected int             width;
  protected int             height;
  protected Animation       animation;
  protected int             currentAction;
  protected int             previousAction;

  protected boolean         walking;
  protected boolean         jumping;
  protected boolean         falling;

  protected float           moveSpeed;
  protected float           maxSpeed;
  protected float           fallSpeed;
  protected float           maxFallSpeed;
  protected float           jumpSpeed;
  protected float           maxJumpSpeed;

  protected Rectangle       collisionBox;
  protected final int       padding        = 2;

  public Entity(World world) {
    this.world = world;
    animation = new Animation();
  }

  public Vector2D getPosition() {
    return pos;
  }

  public Rectangle getCollisionBox() {
    if (collisionBox == null) {
      collisionBox = new Rectangle();
    }
    return collisionBox;
  }

  public void updateCollision() {
    getCollisionBox().setBounds(pos.getFloorX() + padding, pos.getFloorY(), width - (padding * 2), height);
  }

}
