import java.awt.Graphics2D;

public abstract class Sprite {
    private int x, y, width, height;
    public int getX() { return x;}
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public void setWidth(int width) { this.width = width; }

    public void setHeight(int height) { this.height = height; }

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public abstract void update(Keyboard keyboard);
    public abstract void draw(Graphics2D graphics);
}
