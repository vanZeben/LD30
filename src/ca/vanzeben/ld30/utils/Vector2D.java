package ca.vanzeben.ld30.utils;

public class Vector2D {
  protected float x = 0.0f;
  protected float y = 0.0f;

  public Vector2D(float x, float y) {
    set(x, y);
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public int getFloorX() {
    return (int) Math.floor(x);
  }

  public int getFloorY() {
    return (int) Math.floor(y);
  }

  public Vector2D setX(float x) {
    this.x = x;
    return this;
  }

  public Vector2D setY(float y) {
    this.y = y;
    return this;
  }

  public Vector2D set(float x, float y) {
    setX(x);
    setY(y);
    return this;
  }

  public Vector2D add(float x, float y) {
    this.x += x;
    this.y += y;
    return this;
  }

  public Vector2D sub(float x, float y) {
    this.x -= x;
    this.y -= y;
    return this;
  }

  public Vector2D mul(float x, float y) {
    this.x *= x;
    this.y *= y;
    return this;
  }

  public Vector2D div(float x, float y) {
    this.x /= x;
    this.y /= y;
    return this;
  }

  public Vector2D mod(float x, float y) {
    this.x %= x;
    this.y %= y;
    return this;
  }

  public Vector2D set(Vector2D vec) {
    return set(vec.getX(), vec.getY());
  }

  public Vector2D add(Vector2D vec) {
    return add(vec.getX(), vec.getY());
  }

  public Vector2D sub(Vector2D vec) {
    return sub(vec.getX(), vec.getY());
  }

  public Vector2D mul(Vector2D vec) {
    return mul(vec.getX(), vec.getY());
  }

  public Vector2D div(Vector2D vec) {
    return div(vec.getX(), vec.getY());
  }

  public Vector2D mod(Vector2D vec) {
    return mod(vec.getX(), vec.getY());
  }

  public boolean equals(float x, float y) {
    return (getX() == x && getY() == y);
  }

  public boolean equals(Vector2D vec) {
    return equals(vec.getX(), vec.getY());
  }

  public boolean equals(Object obj) {
    if (obj instanceof Vector2D) { return equals((Vector2D) obj); }
    return false;
  }

  public String toString() {
    return x + "," + y;
  }

  public boolean equalsZeroed(Vector2D vector) {
    return clone().zero().equals(vector.zero());
  }

  public boolean greaterThanY(float y) {
    return this.y > y;
  }

  public boolean greaterThanEqualY(float y) {
    return this.y >= y;
  }

  public boolean lessThanY(float y) {
    return this.y < y;
  }

  public boolean lessThanEqualY(float y) {
    return this.y <= y;
  }

  public Vector2D zero() {
    this.x = ((int) this.x);
    this.y = ((int) this.y);
    return this;
  }

  public Vector2D clear() {
    this.x = 0.0F;
    this.y = 0.0F;
    return this;
  }

  public Vector2D clearX() {
    this.x = 0.0F;
    return this;
  }

  public Vector2D clearY() {
    this.y = 0.0F;
    return this;
  }

  public Vector2D clone() {
    return new Vector2D(this.x, this.y);
  }

  public Vector2D opposite() {
    return new Vector2D(0.0F - getX(), 0.0F - getY());
  }
}
