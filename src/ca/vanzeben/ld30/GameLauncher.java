package ca.vanzeben.ld30;

import java.applet.Applet;
import java.awt.BorderLayout;

@SuppressWarnings("serial") public class GameLauncher extends Applet {

  public static Game game;

  public void init() {
    game = new Game(true);
    setMinimumSize(Game.DIM);
    setMaximumSize(Game.DIM);
    setPreferredSize(Game.DIM);
    setSize(Game.DIM);

    setLayout(new BorderLayout());
    add(game, "Center");
  }

  public void start() {
    game.start();
  }

  public void stop() {
    game.stop();
  }

}
