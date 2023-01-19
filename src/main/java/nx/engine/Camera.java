package nx.engine;

public class Camera {

    private double xPos, yPos;

    public void move(float x, float y) {
        this.xPos += x;
        this.yPos += y;
    }

    public void setPosition(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

}
