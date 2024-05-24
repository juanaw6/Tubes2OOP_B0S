package backend_test.state;

import app.state.GameState;
import app.state.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private GameState gameState;

    @BeforeEach
    public void setUp() {
        gameState = GameState.getInstance();
        GameState.reset();
    }

    @Test
    public void testSingletonInstance() {
        GameState anotherInstance = GameState.getInstance();
        assertSame(gameState, anotherInstance);
    }

    @Test
    public void testInitialState() {
        assertNotNull(gameState.getPlayer1());
        assertNotNull(gameState.getPlayer2());
        assertEquals(1, gameState.getTurn());
        assertEquals(0, gameState.getCurrentPlayerIndex());
    }

    @Test
    public void testNextTurn() {
        gameState.nextTurn();
        assertEquals(1, gameState.getCurrentPlayerIndex());
        assertEquals(2, gameState.getTurn());
        gameState.nextTurn();
        assertEquals(0, gameState.getCurrentPlayerIndex());
        assertEquals(3, gameState.getTurn());
    }

    @Test
    public void testGetCurrentPlayer() {
        Player currentPlayer = gameState.getCurrentPlayer();
        assertEquals("Player 1", currentPlayer.getName());

        gameState.nextTurn();
        currentPlayer = gameState.getCurrentPlayer();
        assertEquals("Player 2", currentPlayer.getName());
    }

    @Test
    public void testGetEnemyPlayer() {
        Player enemyPlayer = gameState.getEnemyPlayer();
        assertEquals("Player 2", enemyPlayer.getName());

        gameState.nextTurn();
        enemyPlayer = gameState.getEnemyPlayer();
        assertEquals("Player 1", enemyPlayer.getName());
    }

    @Test
    public void testSetTurn() {
        gameState.setTurn(5);
        assertEquals(5, gameState.getTurn());
    }

    @Test
    public void testGetWinner() {
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();
        player1.setGulden(10);
        player2.setGulden(5);
        assertEquals(1, gameState.getWinner());

        player1.setGulden(0);
        player2.setGulden(10);
        assertEquals(2, gameState.getWinner());

        player1.setGulden(10);
        player2.setGulden(10);
        assertEquals(0, gameState.getWinner());
    }

    @Test
    public void testSetAndGetPlayers() {
        Player newPlayer1 = new Player("New Player 1");
        Player newPlayer2 = new Player("New Player 2");

        gameState.setPlayer1(newPlayer1);
        gameState.setPlayer2(newPlayer2);

        assertEquals(newPlayer1, gameState.getPlayer1());
        assertEquals(newPlayer2, gameState.getPlayer2());
    }

    @Test
    public void testResetGameState() {
        Player newPlayer1 = new Player("New Player 1");
        Player newPlayer2 = new Player("New Player 2");

        gameState.setPlayer1(newPlayer1);
        gameState.setPlayer2(newPlayer2);
        gameState.setTurn(10);

        GameState.reset();

        assertEquals("Player 1", gameState.getPlayer1().getName());
        assertEquals("Player 2", gameState.getPlayer2().getName());
        assertEquals(1, gameState.getTurn());
        assertEquals(0, gameState.getCurrentPlayerIndex());
    }

    @Test
    public void testChangePlayerGulden() {
        Player player1 = gameState.getPlayer1();
        Player player2 = gameState.getPlayer2();

        player1.setGulden(50);
        player2.setGulden(100);

        assertEquals(50, gameState.getPlayer1().getGulden());
        assertEquals(100, gameState.getPlayer2().getGulden());
    }

    @Test
    public void testPlayerGuldenAfterTurnChange() {
        Player player1 = gameState.getPlayer1();
        player1.setGulden(50);

        gameState.nextTurn();
        assertEquals(50, gameState.getPlayer1().getGulden());

        Player player2 = gameState.getPlayer2();
        player2.setGulden(100);
        gameState.nextTurn();
        assertEquals(100, gameState.getPlayer2().getGulden());
    }

    @Test
    public void testSetNegativeTurn() {
        gameState.setTurn(-5);
        assertEquals(-5, gameState.getTurn());
    }

    @Test
    public void testTurnIncrement() {
        int initialTurn = gameState.getTurn();
        gameState.nextTurn();
        assertEquals(initialTurn + 1, gameState.getTurn());
    }

    @Test
    public void testCurrentPlayerIndexAfterMultipleTurns() {
        gameState.nextTurn();
        assertEquals(1, gameState.getCurrentPlayerIndex());

        gameState.nextTurn();
        assertEquals(0, gameState.getCurrentPlayerIndex());

        gameState.nextTurn();
        assertEquals(1, gameState.getCurrentPlayerIndex());
    }

    @Test
    public void testPlayerNamesAfterReset() {
        gameState.getPlayer1().setName("New Player 1");
        gameState.getPlayer2().setName("New Player 2");

        GameState.reset();

        assertEquals("Player 1", gameState.getPlayer1().getName());
        assertEquals("Player 2", gameState.getPlayer2().getName());
    }

    @Test
    public void testPlayerGuldenAfterReset() {
        gameState.getPlayer1().setGulden(50);
        gameState.getPlayer2().setGulden(100);

        GameState.reset();

        assertEquals(0, gameState.getPlayer1().getGulden());
        assertEquals(0, gameState.getPlayer2().getGulden());
    }
}