package ca.vanzeben.ld30.utils;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RenderingUtils {

  public static void drawGradient(Graphics2D g, Color start, Color end, int x, int y, int w, int h, int d) {
    BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    GradientPaint grad = null;
    switch (d) {
      default:
      case 0:
        grad = new GradientPaint(0, 0, start, 0, h, end);
        break;
      case 1:
        grad = new GradientPaint(0, 0, end, 0, h, start);
        break;
      case 2:
        grad = new GradientPaint(0, 0, start, w, 0, end);
        break;
      case 3:
        grad = new GradientPaint(0, 0, end, w, 0, start);
        break;
    }
    Graphics2D g2 = (Graphics2D) img.getGraphics();
    g2.setPaint(grad);
    g2.fillRect(0, 0, w, h);
    g2.dispose();
    g.drawImage(img, x, y, w, h, null);
  }

}
