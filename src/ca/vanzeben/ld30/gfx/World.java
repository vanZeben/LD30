package ca.vanzeben.ld30.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import ca.vanzeben.ld30.Game;
import ca.vanzeben.ld30.ent.Enemy;
import ca.vanzeben.ld30.ent.Player;
import ca.vanzeben.ld30.input.InputController.KeyChars;
import ca.vanzeben.ld30.lvl.Level;
import ca.vanzeben.ld30.lvl.Level.LevelType;
import ca.vanzeben.ld30.lvl.layers.DoorLayer;
import ca.vanzeben.ld30.lvl.layers.FloorLayer;
import ca.vanzeben.ld30.lvl.layers.Layer;
import ca.vanzeben.ld30.lvl.layers.PaintingLayer;
import ca.vanzeben.ld30.lvl.layers.WallpaperLayer;
import ca.vanzeben.ld30.utils.BaseGameClass;
import ca.vanzeben.ld30.utils.JukeBox;

public class World extends BaseGameClass {

  public Player            player;
  public boolean           started = false;

  public ArrayList<Screen> screens = new ArrayList<Screen>();
  public ArrayList<Layer>  layers  = new ArrayList<Layer>();
  public ArrayList<Enemy>  enemies = new ArrayList<Enemy>();
  public GUI               gui;

  public World() {
    init();
  }

  public void init() {
    gui = new GUI(this);
    player = new Player(this);
    screens.add(new Screen(this, Game.PIXEL_DIM.width, Game.PIXEL_DIM.height / Game.NUM_LEVELS, 0, 0, new Color[] { new Color(250F / 255F, 100F / 255F, 100F / 255F, .2F), new Color(150F / 255F, 10F / 255F, 0F, .2F) }, LevelType.OVERWORLD));
    screens.add(new Screen(this, Game.PIXEL_DIM.width, Game.PIXEL_DIM.height / Game.NUM_LEVELS, 0, Game.PIXEL_DIM.height / Game.NUM_LEVELS,
        new Color[] { new Color(255F / 255F, 10F / 255F, 10F / 255F, 0.2F), new Color(150F / 255F, 0F / 255F, 0F / 255F, 0.2F) }, LevelType.DEATH_VALLEY));

    layers.add(new WallpaperLayer(this, 2, 6, 80));
    layers.add(new FloorLayer(this, 3, 6, 80));
    layers.add(new PaintingLayer(this, 4));
    layers.add(new DoorLayer(this, 5));
    JukeBox.load("/sfx/bg.mp3", "bg");
    JukeBox.load("/sfx/start.mp3", "start");
    JukeBox.loop("bg");
  }

  public void stop() {
    JukeBox.stop("bg");
  }

  @Override public void update() {
    if (player == null) {
      init();
    }
    if (!started && Game.inputController.getPressedKeys().contains(KeyChars.ENTER)) {
      started = true;
      JukeBox.play("start");
    }
    if (started) {
      if (player.time % 400 == 0) {
        ((DoorLayer) layers.get(3)).modifier++;
        System.out.println(player.time);
      }
      for (Screen screen : screens) {
        screen.update();
      }
      for (Enemy e : enemies) {
        e.update();
      }
    }
    player.update();
    gui.update();
  }

  @Override public void render(Graphics2D g) {
    for (Screen s : screens) {
      s.render(g);
    }
    for (Enemy e : enemies) {
      e.render(g);
    }
    player.render(g);
    gui.render(g);
  }

  public Screen getScreen(LevelType type) {
    for (Screen s : screens) {
      if (s.level.type == type) { return s; }
    }
    return null;
  }

  public Level getLevel(LevelType type) {
    Screen s = getScreen(type);
    if (s != null) { return s.level; }
    return null;
  }
}
