import java.awt.*;
import java.lang.Math;
public class Ball extends Sprite {
    private int VelX;
    private int VelY;

    public int getVelX() {
        return VelX;
    }

    public int getVelY() {
        return VelY;
    }

    public void setVelX(int velX) {
        VelX = velX;
    }

    public void setVelY(int velY) {
        VelY = velY;
    }

    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 800;
    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        //Randomized angle of start (45  or 135 degrees)
        double angle= Math.random();
        if(angle>0.5){
            this.VelX=-5;
        } else{
            this.VelX=5;
        }

        this.VelY=-5;
    }

    @Override
    public void update(Keyboard keyboard) {
        //Movement of the balls
        setX(getX()+VelX);
        setY(getY()+VelY);
        //Bounce on the wall
        if(getX()>WINDOW_WIDTH-2*getWidth() || getX()<0){
            VelX=-VelX;
        }
        if( getY()<0){
            VelY=-VelY;
        }


    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.blue);
        graphics.fillOval(getX(),getY(),getWidth(),getHeight());

    }
}
