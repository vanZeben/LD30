package ca.vanzeben.ld30.lvl;

import java.awt.Graphics2D;

import ca.vanzeben.ld30.gfx.Screen;
import ca.vanzeben.ld30.lvl.layers.ObjectLayer;
import ca.vanzeben.ld30.utils.BaseGameClass;

public class Level extends BaseGameClass {

  public final Screen screen;
  public int          levelBase = 2;

  public enum LevelType {
    OVERWORLD, DEATH_VALLEY;
  }

  public LevelType type;

  public Level(Screen screen, LevelType type) {
    this.screen = screen;
    this.type = type;
  }

  @Override public void update() {}

  @Override public void render(Graphics2D g) {}

  public boolean inDoor() {
    if (screen.world.layers.size() > 2) {
      ObjectLayer layer = (ObjectLayer) screen.world.layers.get(3);
      return layer.hasObjectAt(screen.world.player.getPosition());
    }
    return false;
  }
}
