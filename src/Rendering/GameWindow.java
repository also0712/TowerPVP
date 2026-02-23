// inspiré de  - https://stackoverflow.com/a/1963684
// on sépare la couche gameLogic du rendering
// la gameloop et le render sont appelés ici


package Rendering;

import Game.Common.GameConfig;
import Game.Object.GameLogic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame implements Runnable {
    private boolean isRunning = true;
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D backgroundGraphics;
    private Graphics2D graphics;
    private GameRenderer currentRenderer;
    private int width = 1280;
    private int height = 768;
    private int scale = 1;


    private GameLogic logic ;

    private GraphicsConfiguration config =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();


    // create a hardware accelerated image
    public final BufferedImage create(final int width, final int height,
                                      final boolean alpha) {
        return config.createCompatibleImage(width, height, alpha
                ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    // Setup
    public GameWindow() {
        // définition du renderer (2D ou Isometric)
        currentRenderer = new Renderer2D();

        // JFrame initialisation
        setTitle("Towers PvP");
        addWindowListener(new FrameClose());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width * scale, height * scale);
        setResizable(false);
        setVisible(true);


        // Canvas
        canvas = new Canvas(config);
        canvas.setSize(width * scale, height * scale);
        add(canvas, 0); // add canvas to frame

        // Background & Buffer
        background = create(width, height, false);
        canvas.createBufferStrategy(2);
        do {
            strategy = canvas.getBufferStrategy();
        } while (strategy == null);//attente que le buffer strategy est bien initialisé

        System.out.println("Le jeu est initialisé !");
        Thread threadGame = new Thread(this);
        threadGame.start();

        logic = new GameLogic();

    }

    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (IllegalStateException e) {
                return null;
            }
        }
        return graphics;
    }

    private boolean updateScreen() {
        graphics.dispose();
        graphics = null;
        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());

        } catch (NullPointerException e) {
            return true;

        } catch (IllegalStateException e) {
            return true;
        }
    }

    public void run() {
        backgroundGraphics = (Graphics2D) background.getGraphics();

        //on nomme la boucle mainLoop pour utiliser le nom dans le break
        mainLoop:
        while (isRunning) {
            long renderStart = System.nanoTime();

            // Update game logic
            logic.update();

            // Update Graphics
            do {
                Graphics2D bg = getBuffer();
                if (!isRunning) {
                    break mainLoop;
                }
                renderGame(backgroundGraphics); // this calls your draw method
                // thingy
                if (scale != 1) {
                    bg.drawImage(background, 0, 0, width * scale, height
                            * scale, 0, 0, width, height, null);
                } else {
                    bg.drawImage(background, 0, 0, null);
                }
                bg.dispose();
            } while (!updateScreen());

            // Better do some FPS limiting here
            long renderTimeMs = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, GameConfig.DELAY_BETWEEN_FRAMES - renderTimeMs));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTimeMs = (System.nanoTime() - renderStart) / 1000000;

        }
        dispose(); // dispose frame
    }

    public void updateGame() {
        // update game logic here
        System.out.println("Le jeu tourne !");
    }


    public void renderGame(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        ///currentRenderer.render(g, player);

    }
}
