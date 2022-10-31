import java.awt.*;

public class ColoredBox extends Sprite{
    private int health;
    private  Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Color getColor() {
        return color;
    }

    public ColoredBox(int x, int y, int width, int height, Color color, int health) {
        super(x,y,width,height);
        this.color=color;
        this.health=health;
    }

    @Override
    public void update(Keyboard keyboard) {
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.drawRect(getX(),getY(),getWidth(),getHeight());

    }
}
