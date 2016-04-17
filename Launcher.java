package game;

public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("MyGame", 1600, 900);
        game.start();
    }
}
