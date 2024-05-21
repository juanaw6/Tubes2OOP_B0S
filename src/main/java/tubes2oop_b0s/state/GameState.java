package tubes2oop_b0s.state;

import java.util.ArrayList;

public class GameState {
    private int currentPlayerIndex;
    private final ArrayList<Player> players;
    private int turn;

    private GameState() {
        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());
        turn = 0;
        currentPlayerIndex = 0;
    }

    public static GameState createGameState() {
        return new GameState();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
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
