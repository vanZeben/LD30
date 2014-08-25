package ca.vanzeben.ld30.lvl.layers;

import ca.vanzeben.ld30.ent.Enemy;
import ca.vanzeben.ld30.ent.Enemy.EnemyType;
import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.lvl.layers.obj.BackgroundObject;
import ca.vanzeben.ld30.lvl.layers.obj.Door;
import ca.vanzeben.ld30.lvl.layers.obj.Door.DoorType;
import ca.vanzeben.ld30.utils.Images;
import ca.vanzeben.ld30.utils.Vector2D;

public class DoorLayer extends ObjectLayer {

  private int lastDoorX = 0;
  public int  modifier  = 0;

  public DoorLayer(World world, int zIndex) {
    super(world, zIndex);
    add(new Door(world, -5, 0, DoorType.OPEN));

  }

  public void loadImage() {
    this.image = Images.DOORS;
    this.  deathWorldImage = Images.DOORS_HELL;
  }

  protected void init() {
    int minX = (world.getScreen(LevelType.OVERWORLD).cam.getX()) + world.getScreen(LevelType.OVERWORLD).width;
    int maxX = (world.getScreen(LevelType.OVERWORLD).cam.getX()) + world.getScreen(LevelType.OVERWORLD).width * 2;

    if (minX >= lastDoorX + 100 - (modifier * 25)) {
      Door door = new Door(world, random.nextInt(maxX - minX) + minX, 0, (random.nextInt(100 - 1) + 1 > (40 - (modifier * 10))) ? DoorType.OPEN : DoorType.CLOSED);
      lastDoorX = door.pos.getFloorX();
      add(door);
      if (random.nextBoolean() && door.type == DoorType.OPEN) {
        world.enemies.add(new Enemy(world, EnemyType.BASE2, door.pos.clone()));
      }
    }
  }

  public boolean hasObjectAt(Vector2D vec) {
    for (BackgroundObject obj : getBgObjects()) {
      if (((Door) obj).type == DoorType.OPEN && obj.containsPoint(vec)) { return true; }
    }
    return false;
  }
}