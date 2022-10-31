import java.awt.*;

public class Player extends Sprite{
    private int speed;
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Player(int x) {
        super(x, 525, 250, 10);
        this.speed=20;
    }
    @Override
    public void update(Keyboard keyboard) {
        //Movement with the keys
        if(keyboard.isKeyDown(Key.Left) && getX()>0){
            setX(getX()-speed);
        }
        if(keyboard.isKeyDown(Key.Right) && getX()<WINDOW_WIDTH-getWidth()){
            setX(getX()+speed);
        }


    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(getX(),getY(),getWidth(),getHeight());
    }
}
