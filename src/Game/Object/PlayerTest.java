package Game.Object;

import Game.Common.GameConfig;

public class PlayerTest {
    private  double posX, posY; // Utiliser double pour plus de précision en 3D plus tard
    private double height, width; // dimensions en metre du joueur

    public double speed = 5.0; // 5m/s

    public double destX,destY;

    // Getters pour que le moteur de rendu puisse lire la position
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }



    public PlayerTest(){
        posX = 10.0;
        posY = 10.0;
        height = 3.0;
        width = 3.0;

        destX=500.0;
        destY=500.0;
    }

    public void setDestination(double x, double y) {
        destX = x;
        destY = y;
    }


    public boolean forwardToDestination() {
        // à la vitesse de speed , quelle distance parcourt-on ?  produit en croix
        // 5m = 1 sec  donc en 16ms je parcours 5*16 / 1000  note 60fps = 16ms pour chaque frame

        double disanceParcourue = speed * GameConfig.DELAY_BETWEEN_FRAMES / 1000;

        // Je suis a un point x1,y1 et je vais vers un point x2,y2.
        // On peut représenter un triangle rectange entre les deux point où la disance parcourue est l'hypotenuse
        double triangleSideX = destX - posX;
        double triangleSideY = destY - posY;
        double triangleHypothenuse = Math.sqrt( (triangleSideX * triangleSideX) + (triangleSideY * triangleSideY));

        if( disanceParcourue<triangleHypothenuse ) { // on n'a pas dépassé notre cible
            // à cette vitesse on ne peut parcourir qu'une fraction de l'hypothénuse
            double ratio = disanceParcourue / triangleHypothenuse;
            posX += triangleSideX * ratio; // on applique le nouveau posX
            posY += triangleSideY * ratio; // on applique le nouveau Y
            System.out.println(posX);
            return  false; // pas arrivé à destination
        }
        else {
            posX = destX;
            posY = destY;

            return true; // arrivé à destination
        }
    }

    // getter setters
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}