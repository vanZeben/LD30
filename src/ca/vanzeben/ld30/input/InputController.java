package ca.vanzeben.ld30.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputController implements KeyListener {

  public static enum KeyChars {
    UP(new int[] { KeyEvent.VK_W, KeyEvent.VK_UP }), DOWN(new int[] { KeyEvent.VK_S, KeyEvent.VK_DOWN }), LEFT(new int[] { KeyEvent.VK_A, KeyEvent.VK_LEFT }), RIGHT(new int[] { KeyEvent.VK_D, KeyEvent.VK_RIGHT }), ENTER(new int[] { KeyEvent.VK_SPACE,
        KeyEvent.VK_ENTER }), ESCAPE(new int[] { KeyEvent.VK_ESCAPE });

    private int[] keys;

    private KeyChars(int[] keys) {
      this.keys = keys;
    }

    public int[] getKeys() {
      return keys;
    }
  }

  private boolean[]              keys        = new boolean[65536];
  private Map<KeyChars, Boolean> keysPressed = new HashMap<KeyChars, Boolean>();

  public InputController() {
    init();
  }

  public void init() {
    keysPressed.put(KeyChars.UP, false);
    keysPressed.put(KeyChars.DOWN, false);
    keysPressed.put(KeyChars.LEFT, false);
    keysPressed.put(KeyChars.RIGHT, false);
    keysPressed.put(KeyChars.ENTER, false);
    keysPressed.put(KeyChars.ESCAPE, false);
  }

  public void update() {
    for (KeyChars c : keysPressed.keySet()) {
      boolean on = false;
      for (int i : c.getKeys()) {
        on = on || keys[i];
      }
      keysPressed.put(c, on);
    }
  }

  @Override public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;
  }

  @Override public void keyReleased(KeyEvent e) {
    keys[e.getKeyCode()] = false;
  }

  @Override public void keyTyped(KeyEvent e) {}

  public void clear() {
    for (int i = 0; i < keys.length; i++) {
      keys[i] = false;
    }
  }

  public List<KeyChars> getPressedKeys() {
    List<KeyChars> retVal = new ArrayList<KeyChars>();
    for (KeyChars k : keysPressed.keySet()) {
      if (keysPressed.get(k)) {
        retVal.add(k);
      }
    }
    return retVal;
  }

  public boolean has(KeyChars k) {
    if (getPressedKeys().contains(k)) {
      for (int i : k.getKeys()) {
        keys[i] = false;
      }
      return true;
    }
    return false;
  }
}
