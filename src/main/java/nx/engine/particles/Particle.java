package nx.engine.particles;

import nx.util.Vector2f;

public class Particle {

    private float posX, posY;
    private Vector2f direction;
    private double timeAlive;

    public Particle(float posX, float posY, Vector2f direction) {
        this.posX = posX;
        this.posY = posY;
        this.direction = direction;
    }

    public void update(double delta) {
        posX += direction.x * delta;
        posY += direction.y * delta;

        timeAlive += delta;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public double getTimeAlive() {
        return timeAlive;
    }

}
