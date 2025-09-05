package TemplateMethod;

import java.util.Random;

public class DiceRaceGame extends Game {

    private int[] scores;          // pelaajien pisteet
    private int target = 20;       // maali
    private int winner = -1;       // voittajan indeksi (–1 = ei vielä)
    private int turnCount = 0;     // varmistus, ettei looppi jää ikuiseksi
    private int maxTurns = 200;    // kova raja
    private Random rnd;

    @Override
    public void initializeGame(int numberOfPlayers) {
        if (numberOfPlayers < 2) {
            throw new IllegalArgumentException("Tarvitaan vähintään 2 pelaajaa.");
        }
        scores = new int[numberOfPlayers];
        rnd = new Random(); // satunnaisluku nopalle
        System.out.println("🎲 DiceRace alkaa! Pelaajia: " + numberOfPlayers + ", maali: " + target + " pistettä.");
        System.out.println("----------------------------------------------------------");
    }

    @Override
    public boolean endOfGame() {
        // Loppuu kun on voittaja tai turvaraja täynnä
        return winner != -1 || turnCount >= maxTurns;
    }

    @Override
    public void playSingleTurn(int player) {
        turnCount++;

        int roll = 1 + rnd.nextInt(6); // noppa 1..6
        scores[player] += roll;

        System.out.printf("Pelaaja %d heitti %d -> pisteet nyt %d%n",
                player, roll, scores[player]);

        if (scores[player] >= target) {
            winner = player; // merkataan voittaja
        }
    }

    @Override
    public void displayWinner() {
        System.out.println("----------------------------------------------------------");
        if (winner != -1) {
            System.out.println("🏆 Voittaja on pelaaja " + winner + "!");
        } else {
            // Jos ei kukaan saavuttanut maalia maxTurnsin sisällä → valitaan suurin
            int bestPlayer = 0;
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > scores[bestPlayer]) bestPlayer = i;
            }
            System.out.println("⏱️ Aikaraja täyttyi. Eniten pisteitä pelaajalla " + bestPlayer + ".");
        }

        // Tulosta lopulliset pisteet
        System.out.println("Lopulliset pisteet:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println(" - Pelaaja " + i + ": " + scores[i]);
        }
    }
}

