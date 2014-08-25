package ca.vanzeben.ld30.gfx;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import ca.vanzeben.ld30.Game;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.Vector2D;

public class Camera extends BaseGameClass {

  private final Screen screen;
  protected Vector2D   pos = new Vector2D(0.0f, 0.0f);

  public Camera(Screen screen, Vector2D pos) {
    this.screen = screen;
    this.pos = pos;
  }

  public Vector2D getPosition() {
    return this.pos;
  }

  public int getX() {
    return getPosition().getFloorX();
  }

  public int getY() {
    return getPosition().getFloorY();
  }

  public void update() {
    centerOnPoint(new Point(screen.world.player.getPosition().getFloorX(), 0));
  }

  public void render(Graphics2D g) {}

  public void centerOnPoint(Point point) {
    this.pos.set(point.x, point.y);
  }

  public void centerOnRectangle(Rectangle rect) {
    this.pos.set(rect.x - Game.PIXEL_DIM.width / 2 + rect.width / 2, rect.y - Game.PIXEL_DIM.height / 2 + rect.height / 2);
  }
}
