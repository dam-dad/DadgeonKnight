package nx.engine;

public class Camera {

    private double xPos, yPos;

    public Camera move(float x, float y) {
        this.xPos += x;
        this.yPos += y;
        
        return this;
    }

    public Camera setPosition(double x, double y) {
        this.xPos = x;
        this.yPos = y;
        
        return this;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

}
