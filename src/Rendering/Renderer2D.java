package Rendering;

import Game.Common.GameConfig;
import Game.Object.GameLogic;
import Game.Object.PlayerTest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Renderer2D implements GameRenderer {
    // Le dictionnaire qui stocke les images
    private static final Map<String, BufferedImage> sprites = new HashMap<>();
    private GameLogic gameLogic;

    public Renderer2D()
    {
        LoadSprites();
    }
    @Override
    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    private void LoadSprites()    {

        loadSprite("background","ressources/background.png");
        loadSprite("player","ressources/player.png"


        );
    }

    public static void loadSprite(String name, String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            sprites.put(name, img);
            System.out.println("Sprite chargé : " + name);
        } catch (Exception e) {
            System.err.println("Impossible de charger le sprite : " + filePath);
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(String name) {
        return sprites.get(name);
    }

    public void render(Graphics2D g) {

        renderBackgoundImage(g);

        // les coordonnées du jeu sont en metres, le terrain occupe 100m posX 56.25m  (16/9)
        // on va prendre en compte la résolution de l'écran pour convertir les metres en pixels

        for (int i = 0; i < gameLogic.getNbPlayer(); i++) {
            PlayerTest player = gameLogic.getPlayerTest(i);
            renderPlayer(g, player);
        }

    }

    private void renderBackgoundImage(Graphics2D g) {

        BufferedImage sprite = getSprite("background");

        g.drawImage(sprite, 0, 0, GameConfig.WORLD_WIDTH_PIXEL, GameConfig.WORLD_HEIGHT_PIXEL, null);

    }

    public void renderPlayer(Graphics2D g, PlayerTest player)
    {
        // 2. Calcul des facteurs d'échelle (Pixels par Mètre)
        // Monde = 100m x 56.25m
        double scaleX = GameConfig.WORLD_WIDTH_PIXEL / GameConfig.WORLD_WIDTH_METERS;
        double scaleY = GameConfig.WORLD_HEIGHT_PIXEL / GameConfig.WORLD_HEIGHT_METERS;

        BufferedImage sprite = getSprite("player");

        if (sprite != null) {
            // 1. Calcul de la taille en pixels
            int pWidth = (int) (player.getWidth() * scaleX);
            int pHeight = (int) (player.getHeight() * scaleY);

            // 2. Conversion de la position avec décalage pour centrer
            // On calcule le pixel du centre, puis on retire la moitié de la taille du sprite
            int px = (int) (player.getPosX() * scaleX) - (pWidth / 2);// on recentre car le point donné n'est pas le point haut gauche mais le centre
            int py = (int) (player.getPosY() * scaleY) - (pHeight / 2);

            // 3. Rendu
            g.drawImage(sprite, px, py, pWidth, pHeight, null);
        }
    }


}
