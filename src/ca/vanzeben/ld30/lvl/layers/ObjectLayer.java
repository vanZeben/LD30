package ca.vanzeben.ld30.lvl.layers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.layers.obj.BackgroundObject;
import ca.vanzeben.ld30.utils.Vector2D;

public abstract class ObjectLayer extends Layer {

  private List<BackgroundObject>     objs         = new ArrayList<BackgroundObject>();
  protected static Map<String, Long> lastAddition = new HashMap<String, Long>();

  public ObjectLayer(World level, int zIndex) {
    super(level, zIndex);
  }

  @Override public void update() {
    init();
    cleanup();
    for (BackgroundObject obj : this.objs) {
      obj.update();
    }
  }

  protected void add(BackgroundObject obj) {
    objs.add(obj);
  }

  protected List<BackgroundObject> getBgObjects() {
    return objs;
  }

  protected abstract void init();

  @Override public void render(Graphics2D g) {
    for (BackgroundObject obj : this.objs) {
      obj.render(g);
    }
  }

  public void cleanup() {
    List<BackgroundObject> objs = new ArrayList<BackgroundObject>(this.objs);
    ArrayList<Integer> iToR = new ArrayList<Integer>();
    for (int i = 0; i < objs.size(); i++) {
      BackgroundObject obj = objs.get(i);
      if (obj.isLeftOfFrame()) {
        iToR.add(i);
        continue;
      }
    }
    int removed = 0;
    for (Integer i : iToR) {
      objs.remove(i - removed);
      removed++;
    }
  }

  public boolean hasObjectAt(Vector2D vec) {
    for (BackgroundObject obj : objs) {
      if (obj.containsPoint(vec)) { return true; }
    }
    return false;
  }

}
