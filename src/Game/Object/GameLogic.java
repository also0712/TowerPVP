package Game.Object;

import Game.Common.GameConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private int nbPlayer = 100;
    List<PlayerTest> playerTests = new ArrayList<PlayerTest>();


    public GameLogic(){

        PlayerTest p;
        for (int i = 0; i < nbPlayer; i++) {

            p = new PlayerTest("warrior");
            playerTests.add(p);
            setRandomDestination(p);

            p = new PlayerTest("scout");
            playerTests.add(p);
            setRandomDestination(p);

            p = new PlayerTest("tank");
            playerTests.add(p);
            setRandomDestination(p);
        }
    }

    public void setRandomDestination(PlayerTest p)
    {
        Random rand = new Random();

        // Génère un nombre entre 0.0 et GameConfig.WORLD_W (100.0)
        double randomX = rand.nextDouble() * GameConfig.WORLD_WIDTH_METERS;

        // Génère un nombre entre 0.0 et GameConfig.WORLD_H (56.25)
        double randomY = rand.nextDouble() * GameConfig.WORLD_HEIGHT_METERS;
        p.setDestination(randomX, randomY);
    }

    public void update() {


        for (int i = 0; i < nbPlayer; i++) {
            PlayerTest p = playerTests.get(i);
            boolean isArrived = p.forwardToDestination();
            if( isArrived)
            {
                setRandomDestination(p);
            }
        }
    }

    public PlayerTest getPlayerTest(int index) {
        return playerTests.get(index);
    }



    public int getNbPlayer() {
        return nbPlayer;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }
}
