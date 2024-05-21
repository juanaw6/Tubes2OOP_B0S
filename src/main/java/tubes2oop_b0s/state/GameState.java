package tubes2oop_b0s.state;

import java.util.ArrayList;

public class GameState {
    private int currentPlayerIndex;
    private final ArrayList<Player> players;
    private int turn;

    private GameState() {
        players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        turn = 0;
        currentPlayerIndex = 0;
    }

    private static class GameStateHolder {
        private static final GameState INSTANCE = new GameState();
    }

    public static GameState getInstance() {
        return GameStateHolder.INSTANCE;
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