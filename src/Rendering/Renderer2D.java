package Rendering;

import Game.Object.PlayerTest;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Renderer2D implements GameRenderer {
    private BufferedImage sprite;

    public void render(Graphics2D g, PlayerTest player) {

        // les coordonnées du jeu sont en metres, le terrain occupe 100m x 56.25m  (16/9)
        // on va prendre en compte la résolution de l'écran pour convertir les metres en pixels

        g.drawImage(sprite, (int)player.getX(), (int)player.getY(), null);
    }
}
