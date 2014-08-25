package ca.vanzeben.ld30.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {
  public static BufferedImage DOORS_HELL, DOORS, ENEMIES, FLOOR, FLOOR_HELL, GHOST, PAINTINGS, PAINTINGS_HELL, PLAYER, WALLPAPER, WALLPAPER_HELL, GUI;

  public static void init() {
    try {
      DOORS = ImageIO.read(Images.class.getResource("/img/doors.png"));
      DOORS_HELL = ImageIO.read(Images.class.getResource("/img/doors-hell.png"));
      ENEMIES = ImageIO.read(Images.class.getResource("/img/enemies.png"));
      FLOOR = ImageIO.read(Images.class.getResource("/img/floor.png"));
      FLOOR_HELL = ImageIO.read(Images.class.getResource("/img/floor-hell.png"));
      GHOST = ImageIO.read(Images.class.getResource("/img/ghost.png"));
      PAINTINGS = ImageIO.read(Images.class.getResource("/img/paintings.png"));
      PAINTINGS_HELL = ImageIO.read(Images.class.getResource("/img/paintings-hell.png"));
      PLAYER = ImageIO.read(Images.class.getResource("/img/player.png"));
      WALLPAPER = ImageIO.read(Images.class.getResource("/img/wallpaper.png"));
      WALLPAPER_HELL = ImageIO.read(Images.class.getResource("/img/wallpaper-hell.png"));
      GUI = ImageIO.read(Images.class.getResource("/img/gui.png"));
    } catch (IOException e) {
      e.getStackTrace();
    }
  }
}
