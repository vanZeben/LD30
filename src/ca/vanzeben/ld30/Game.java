package ca.vanzeben.ld30;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import ca.vanzeben.ld30.gfx.World;
import ca.vanzeben.ld30.input.InputController;
import ca.vanzeben.ld30.utils.Images;

@SuppressWarnings("serial") public class Game extends Canvas implements Runnable {
  public static final Dimension DIM             = new Dimension(650, 480);
  public static final String    TITLE           = "Outer Body";
  public static final int       PIXEL_SIZE      = 3;
  public static final Dimension PIXEL_DIM       = new Dimension(DIM.width / PIXEL_SIZE, DIM.height / PIXEL_SIZE);
  public static final int       UPDATE_RATE     = 20;
  public static final int       RENDER_RATE     = 60;
  public static final int       NUM_LEVELS      = 2;

  public static Game            instance;
  public int                    tickCount;
  public int                    fps             = 0;
  public int                    tps             = 0;
  public boolean                isRunning       = false;
  private Thread                thread;
  private static boolean        debug           = true;
  public static InputController inputController = new InputController();
  private JFrame                frame;
  private Image                 imgScreen;
  private World                 world;
  private boolean               isApplet        = false;

  public static enum Level {
    INFO, WARNING, SEVERE;
  }

  public Game(boolean isApplet) {
    instance = this;
    this.isApplet = isApplet;
    addKeyListener(inputController);
  }

  public static void log(Level level, String msg) {
    if (debug) {
      switch (level) {
        case INFO:
          System.out.println(String.format("[%s] %s", TITLE, msg));
          break;
        case WARNING:
          System.out.println(String.format("[WARNING] [%s] %s", TITLE, msg));
          break;
        case SEVERE:
          System.out.println(String.format("[SEVERE] [%s] %s", TITLE, msg));
          instance.stop();
          break;
      }
    }
  }

  public void init() {
    isRunning = true;
    Images.init();
    newWorld();
  }

  public void newWorld() {
    if (world != null) {
      world.stop();
    }
    world = new World();
  }

  public void start() {
    init();
    thread = new Thread(this, "LD30");
    thread.start();
  }

  public void stop() {
    if (world != null) {
      world.stop();
    }
    isRunning = false;
    if (thread != null) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override public void run() {
    double lastUpdateTime = System.nanoTime();
    double lastRenderTime = lastUpdateTime;

    final int ns = 1000000000;
    final double nsPerUpdate = (double) ns / UPDATE_RATE;
    final double nsPerRender = (double) ns / RENDER_RATE;
    final int maxUpdatesBeforeRender = 5;

    int lastSecond = (int) (lastUpdateTime / ns);
    int tickCount = 0;
    int renderCount = 0;
    while (isRunning) {

      long currTime = System.nanoTime();
      int tps = 0;

      while ((currTime - lastUpdateTime) > nsPerUpdate && tps < maxUpdatesBeforeRender) {
        update();
        tickCount++;
        this.tickCount++;
        tps++;
        lastUpdateTime += nsPerUpdate;
      }

      if (currTime - lastUpdateTime > nsPerUpdate) {
        lastUpdateTime = currTime - nsPerUpdate;
      }

      float interpolation = Math.min(1.0F, (float) ((currTime - lastUpdateTime) / nsPerUpdate));
      render(interpolation);
      renderCount++;
      lastRenderTime = currTime;

      int currSecond = (int) (lastUpdateTime / ns);
      if (currSecond > lastSecond) {
        this.tps = tickCount;
        this.fps = renderCount;
        tickCount = 0;
        renderCount = 0;
        lastSecond = currSecond;
        log(Level.INFO, this.tickCount + " ticks, " + this.fps + " fps, " + this.tps + " tps");
      }

      while (currTime - lastRenderTime < nsPerRender && currTime - lastUpdateTime < nsPerUpdate) {
        Thread.yield();
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        currTime = System.nanoTime();
      }
    }
  }

  public void update() {
    tickCount++;
    inputController.update();
    world.update();
  }

  public void render(float interpolation) {
    BufferStrategy strategy = getBufferStrategy();
    if (strategy == null) {
      createBufferStrategy(3);
      requestFocus();
      return;
    }
    if (imgScreen == null) {
      imgScreen = createVolatileImage(PIXEL_DIM.width, PIXEL_DIM.height);
      requestFocus();
      return;
    }
    Graphics2D g = (Graphics2D) imgScreen.getGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, PIXEL_DIM.width, PIXEL_DIM.height);
    world.render(g);

    g.dispose();

    g = (Graphics2D) strategy.getDrawGraphics();
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, DIM.width, DIM.height);
    g.drawImage(imgScreen, 0, 0, DIM.width, DIM.height, null);
    g.dispose();
    strategy.show();
  }

  public static void main(String[] args) {
    Game game = new Game(false);
    Dimension dim = new Dimension(DIM.width - 10, DIM.height - 10);
    game.setPreferredSize(dim);
    game.setMinimumSize(dim);
    game.setMaximumSize(dim);

    game.frame = new JFrame(Game.TITLE);
    game.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    // game.frame.setUndecorated(true);

    game.frame.setLayout(new BorderLayout());
    game.frame.add(game, "Center");
    game.frame.pack();

    game.frame.setResizable(false);
    game.frame.setLocationRelativeTo(null);
    game.frame.setVisible(true);

    game.start();
  }
}
