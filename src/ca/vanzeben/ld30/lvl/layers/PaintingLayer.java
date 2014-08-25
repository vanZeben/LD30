package ca.vanzeben.ld30.lvl.layers;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.lvl.layers.obj.BackgroundObject;
import ca.vanzeben.ld30.lvl.layers.obj.Painting;
import ca.vanzeben.ld30.lvl.layers.obj.Painting.PaintingType;
import ca.vanzeben.ld30.utils.Images;

public class PaintingLayer extends ObjectLayer {

  private int lastGenLocation = 0;

  public PaintingLayer(World level, int zIndex) {
    super(level, zIndex);
    // add(new Painting(level, 20, 20, PaintingType.MED));
    add(new Painting(level, 115, 10, PaintingType.STAMINA_PAINTING));
    add(new Painting(level, 2, 10, PaintingType.HEALTH_PAINTING));
    add(new Painting(level, 12, 40, PaintingType.DOOR));
    add(new Painting(level, (level.getScreen(LevelType.OVERWORLD).width / 2) - 50, -12, PaintingType.WELCOME));
    add(new Painting(level, 96, 50, PaintingType.PLAYER));
  }

  public void loadImage() {
    this.image = Images.PAINTINGS;
    this.deathWorldImage = Images.PAINTINGS_HELL;
  }

  protected void init() {
    int minX = (world.getScreen(LevelType.OVERWORLD).cam.getX()) + world.getScreen(LevelType.OVERWORLD).width;
    int maxX = (world.getScreen(LevelType.OVERWORLD).cam.getX()) + world.getScreen(LevelType.OVERWORLD).width * 2;
    int minY = 10;
    int maxY = world.getScreen(LevelType.OVERWORLD).height - 30;

    if (minX >= lastGenLocation + 50 && random.nextBoolean() && random.nextBoolean()) {
      int x = random.nextInt(maxX - minX) + minX;
      int y = random.nextInt(maxY - minY) + minY;
      PaintingType type = PaintingType.values()[random.nextInt(2)];
      if (random.nextInt(100 - 1) + 1 < Painting.PAINTING_RARITY[type.ordinal()] && (!lastAddition.containsKey(type.name()) || (lastAddition.containsKey(type.name()) && System.currentTimeMillis() - lastAddition.get(type.name()) >= 5 * 1000))) {
        Painting obj = new Painting(world, x, y, type);
        lastGenLocation = x;
        boolean err = false;
        for (BackgroundObject obj1 : getBgObjects()) {
          if (obj.overLaps(obj1)) {
            err = true;
            break;
          }
        }
        if (!err) {
          add(obj);
          lastAddition.put(type.name(), System.currentTimeMillis());
        }
      }
    }
  }
}