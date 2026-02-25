package Game.Common;

public class GameConfig {

    public static final long DELAY_BETWEEN_FRAMES = (long) (1.0 / 60.0 * 1000); // 60 fps
    public static final int WORLD_WIDTH_PIXEL = 1280;
    public static final int WORLD_HEIGHT_PIXEL = 720;
    public static final double WORLD_WIDTH_METERS = 100.0;
    public static final double WORLD_HEIGHT_METERS = WORLD_HEIGHT_PIXEL * WORLD_WIDTH_METERS / WORLD_WIDTH_PIXEL;


}
