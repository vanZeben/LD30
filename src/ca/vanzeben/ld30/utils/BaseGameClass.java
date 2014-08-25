package ca.vanzeben.ld30.utils;

import java.awt.Graphics2D;
import java.util.Random;

public abstract class BaseGameClass {

  protected final Random random = new Random();

  public abstract void update();

  public abstract void render(Graphics2D g);
}
