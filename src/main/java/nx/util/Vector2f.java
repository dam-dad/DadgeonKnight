package nx.util;

public class Vector2f {

    public float x, y;

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f normalize() {
        float length = (float) Math.sqrt(x*x + y*y);
        return new Vector2f(x / length, y / length);
    }

    public float distance(double x, double y) {
        return (float) Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y));
    }

}
