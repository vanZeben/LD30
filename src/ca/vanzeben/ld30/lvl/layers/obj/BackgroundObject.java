package ca.vanzeben.ld30.lvl.layers.obj;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.Vector2D;

public class BackgroundObject extends BaseGameClass {

  public World            world;
  public String           imagePath;
  protected BufferedImage image;
  protected BufferedImage deathWorldImage;

  public Vector2D         pos;
  public int              width;
  public int              height;
  public Vector2D         center;

  public BackgroundObject(World world, int x, int y, int width, int height) {
    this.world = world;

    pos = new Vector2D(x, y);
    center = new Vector2D(x + (width / 2), y + (height / 2));
    this.width = width;
    this.height = height;
  }

  public boolean isLeftOfFrame() {
    return (world.getScreen(LevelType.OVERWORLD).cam.getX()) > (pos.getFloorX() + width);
  }

  public boolean inFrame() {
    return ((world.getScreen(LevelType.OVERWORLD).cam.getX()) <= pos.getFloorX() + width && pos.getFloorX() <= (world.getScreen(LevelType.OVERWORLD).cam.getX()) + world.getScreen(LevelType.OVERWORLD).width);
  }

  public Vector2D getCenterPos() {
    return center;
  }

  public boolean overLaps(BackgroundObject obj) {
    return ((Math.max(center.getX(), obj.center.getX()) - Math.min(center.getX(), obj.center.getX()) <= width) && (Math.max(center.getY(), obj.center.getY()) - Math.min(center.getY(), obj.center.getY()) <= height));
  }

  public boolean containsPoint(Vector2D vec) {
    return (pos.getFloorX() <= (vec.getFloorX()) + (world.getScreen(LevelType.OVERWORLD).width / 2) && (vec.getFloorX()) + (world.getScreen(LevelType.OVERWORLD).width / 2) <= pos.getFloorX() + width);

  }

  @Override public void update() {}

  @Override public void render(Graphics2D g) {
    if (inFrame()) {
      for (LevelType t : LevelType.values()) {
        g.drawImage(getImage(t), (-world.getScreen(t).cam.getX()) + pos.getFloorX(), world.getScreen(t).yOffs + world.getScreen(t).cam.getY() + pos.getFloorY(), width, height, null);
      }
    }
  }

  public BufferedImage getImage(LevelType t) {
    if (t == LevelType.DEATH_VALLEY) { return deathWorldImage; }
    return image;
  }
}
