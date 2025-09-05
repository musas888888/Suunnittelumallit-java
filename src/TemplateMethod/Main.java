package TemplateMethod;

public class Main {
    public static void main(String[] args) {
        Game game = new DiceRaceGame();
        game.play(3); // vaihda pelaajien määrä: 2, 3, 4...
    }
}

