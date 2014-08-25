package ca.vanzeben.ld30.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import ca.vanzeben.ld30.lvl.Level;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.lvl.layers.Layer;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.RenderingUtils;
import ca.vanzeben.ld30.utils.Vector2D;

public class Screen extends BaseGameClass {

  public Camera      cam;
  public final World world;
  public Level       level;
  public int         width;
  public int         height;
  public int         xOffs;
  public int         yOffs;
  private Color[]    gradientColour;

  public Screen(World world, int width, int height, int xOffs, int yOffs, Color[] gradientColour, LevelType type) {
    this.world = world;
    this.width = width;
    this.height = height;
    this.xOffs = xOffs;
    this.yOffs = yOffs;
    this.gradientColour = gradientColour;
    this.level = new Level(this, type);
    this.cam = new Camera(this, new Vector2D(0.0f, 0.0f));

  }

  @Override public void update() {
    cam.update();
    level.update();
    for (Layer l : world.layers) {
      l.update();
    }
  }

  @Override public void render(Graphics2D g) {
    level.render(g);

    g.setColor(Color.BLACK);
    g.fillRect(xOffs, yOffs + height - level.levelBase, width, level.levelBase);
    @SuppressWarnings("unchecked") List<Layer> layers = (List<Layer>) world.layers.clone();
    while (!layers.isEmpty()) {
      Layer smallest = layers.get(0);
      int si = 0;
      for (int i = 0; i < layers.size(); i++) {
        Layer l = layers.get(i);
        if (smallest.zIndex >= l.zIndex) {
          smallest = l;
          si = i;
        }
      }
      smallest.render(g);
      layers.remove(si);
    }

    RenderingUtils.drawGradient(g, gradientColour[0], gradientColour[1], xOffs, yOffs, width, height, 0);
    g.setColor(Color.BLACK);

    g.setStroke(new BasicStroke(1));
    g.drawRect(xOffs, yOffs, width - 1, height - 1);

  }

  public Level getLevel() {
    return level;
  }

}
