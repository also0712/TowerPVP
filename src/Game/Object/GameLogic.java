package Game.Object;

import Game.Common.GameConfig;

import java.util.Random;

public class GameLogic {

    PlayerTest playerTest;

    public GameLogic(){

        playerTest = new PlayerTest();
        setRandomDestination(playerTest);
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

        boolean isArrived = playerTest.forwardToDestination();

        if( isArrived)
        {
            setRandomDestination(playerTest);
        }
    }

    public PlayerTest getPlayerTest() {
        return playerTest;
    }

    public void setPlayerTest(PlayerTest playerTest) {
        this.playerTest = playerTest;
    }
}
