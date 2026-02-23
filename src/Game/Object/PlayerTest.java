package Game.Object;

public class PlayerTest {
    private double x, y; // Utiliser double pour plus de précision en 3D plus tard
    private double speed = 5.0;

    public void update(boolean up, boolean down, boolean left, boolean right) {
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;
    }

    // Getters pour que le moteur de rendu puisse lire la position
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}