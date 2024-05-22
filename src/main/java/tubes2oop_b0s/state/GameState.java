package tubes2oop_b0s.state;

import tubes2oop_b0s.store.Store;

import java.util.ArrayList;

public class GameState {
    private static GameState instance = null;
    private int currentPlayerIndex;
    private final ArrayList<Player> players;
    private int turn;

    private GameState() {
        players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        turn = 1;
        currentPlayerIndex = 0;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public void setPlayer1(Player player1) {
        players.set(0, player1);
    }

    public void setPlayer2(Player player2) {
        players.set(1, player2);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getEnemyPlayer() {
        int enemyPlayerIndex = (currentPlayerIndex + 1) % 2;
        return players.get(enemyPlayerIndex);
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1);
        if (currentPlayerIndex == 2) {
            turn++;
            currentPlayerIndex %= 2;
        }
    }

    public int getWinner() {
        if (players.get(0).getGulden() > players.get(1).getGulden()) {
            return 1;
        } else if (players.get(0).getGulden() < players.get(1).getGulden()) {
            return 2;
        } else {
            return 0;
        }
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}