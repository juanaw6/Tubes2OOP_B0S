package tubes2oop_b0s.state;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateTest {

    private GameState gameState;

    @Before
    public void setUp() {
        gameState = GameState.getInstance();
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
        gameState.nextTurn();
        assertEquals(2, gameState.getTurn());
        assertEquals(0, gameState.getCurrentPlayerIndex());
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

        player1.setGulden(0); // Set player1 gulden to 0
        player2.setGulden(10); // Set player2 gulden to 10
        assertEquals(2, gameState.getWinner());

        player1.setGulden(10); // Set player1 gulden to 10
        assertEquals(0, gameState.getWinner());
    }
}
