package tests.game;


import com.yan.durak.gamelogic.commands.SessionCommand;
import com.yan.durak.gamelogic.exceptions.NullCommandException;
import com.yan.durak.gamelogic.game.GameSession;
import commands.SimpleExecutableMockSessionCommand;
import factories.CommandsFactory;
import factories.GameSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * GameSession Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jan 26, 2015</pre>
 */
public class GameSessionTest {

    private GameSession mGameSession;

    @Before
    public void before() throws Exception {
        //create game session with 5 clear piles in it
        mGameSession = GameSessionFactory.createGameSessionForThreePlayers();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addCommand(SessionCommand command)
     * <p/>
     * Case : Command is added to game session.
     * Expected : Command is in the game session execution queue
     */
    @Test
    public void testAddCommand() throws Exception {

        SessionCommand command = CommandsFactory.createSimpleExecutableCommand();

        assertTrue("Pending command queue expected to be empty", mGameSession.getPendingCommandsQueue().isEmpty());

        mGameSession.addCommand(command);

        assertTrue("Pending command queue expected to be not empty", !mGameSession.getPendingCommandsQueue().isEmpty());

        SessionCommand addedCommand = mGameSession.getPendingCommandsQueue().peek();
        assertEquals("Command in the pending queue is the one that was added", command, addedCommand);
    }

    /**
     * Method: addCommand(SessionCommand command)
     * <p/>
     * Case : 3 commands are added to game session.
     * Expected : Command queue contains all 3 commands.
     */
    @Test
    public void testAddCommandWithMultipleCommands() throws Exception {

        SessionCommand command1 = CommandsFactory.createSimpleExecutableCommand();
        SessionCommand command2 = CommandsFactory.createSimpleExecutableCommand();
        SessionCommand command3 = CommandsFactory.createSimpleExecutableCommand();

        assertTrue("Pending command queue expected to be empty", mGameSession.getPendingCommandsQueue().isEmpty());

        mGameSession.addCommand(command1);
        mGameSession.addCommand(command2);
        mGameSession.addCommand(command3);

        assertEquals("There is exactly 3 commands in the queue", 3, mGameSession.getPendingCommandsQueue().size());
    }


    /**
     * Method: addCommand(SessionCommand command)
     * <p/>
     * Case : Null is added as a command to game session.
     * Expected : Command execution queue should not contain the null command
     * Expected : {@link NullCommandException} should be thrown
     */
    @Test(expected = NullCommandException.class)
    public void testAddNullCommand() throws Exception {
        assertTrue("Pending command queue expected to be empty", mGameSession.getPendingCommandsQueue().isEmpty());
        mGameSession.addCommand(null);
        assertTrue("Pending command queue expected to be still empty", !mGameSession.getPendingCommandsQueue().isEmpty());
    }

    /**
     * Method: executeCommand(SessionCommand command)
     * Case : Command is executed on the game session.
     * Expected : Command did run the execution method
     */
    @Test
    public void testExecuteCommand() throws Exception {

        SimpleExecutableMockSessionCommand command = CommandsFactory.createSimpleExecutableCommand();
        assertTrue("Command is not yet executed", !command.isExecuted());

        mGameSession.executeCommand(command);
        assertTrue("Command should be executed", command.isExecuted());

    }

    /**
     * Method: executeCommand(SessionCommand command)
     * Case : null is executed as a command on the game session.
     * Expected : {@link NullCommandException} should be thrown
     */
    @Test(expected = NullCommandException.class)
    public void testExecuteNullCommand() throws Exception {
        mGameSession.executeCommand(null);
    }

    /**
     * Method: startSession()
     * Case : 3 commands are added to the game session.Test Start is called.
     * Expected : All 3 commands are executed. No more commands left in the queue.
     */
    @Test
    public void testStartSession() throws Exception {
        SimpleExecutableMockSessionCommand command1 = CommandsFactory.createSimpleExecutableCommand();
        SimpleExecutableMockSessionCommand command2 = CommandsFactory.createSimpleExecutableCommand();
        SimpleExecutableMockSessionCommand command3 = CommandsFactory.createSimpleExecutableCommand();

        assertFalse("Command is not executed yet", command1.isExecuted());
        assertFalse("Command is not executed yet", command2.isExecuted());
        assertFalse("Command is not executed yet", command3.isExecuted());

        assertTrue("Pending command queue expected to be empty", mGameSession.getPendingCommandsQueue().isEmpty());
        mGameSession.addCommand(command1);
        mGameSession.addCommand(command2);
        mGameSession.addCommand(command3);

        mGameSession.startSession();

        assertTrue("Command is executed", command1.isExecuted());
        assertTrue("Command is executed", command2.isExecuted());
        assertTrue("Command is executed", command3.isExecuted());
    }

    /**
     * Method: getPilesStack()
     */
    @Test
    public void testGetPilesStack() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getTrumpSuit()
     */
    @Test
    public void testGetTrumpSuit() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setTrumpSuit(String trumpSuit)
     */
    @Test
    public void testSetTrumpSuit() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getPlayers()
     */
    @Test
    public void testGetPlayers() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getExecutedCommandsStack()
     */
    @Test
    public void testGetExecutedCommandsStack() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: findPileByTag(String tag)
     */
    @Test
    public void testFindPileByTag() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getPendingCommandsQueue()
     */
    @Test
    public void testGetPendingCommandsQueue() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: getGameRules()
     */
    @Test
    public void testGetGameRules() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: setGameRules(GameRules gameRules)
     */
    @Test
    public void testSetGameRules() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: addPreHook(CommandHook commandHook)
     */
    @Test
    public void testAddPreHook() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: addPostHook(CommandHook commandHook)
     */
    @Test
    public void testAddPostHook() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: searchForRecentCommand(Class<T> searchCommandClazz)
     */
    @Test
    public void testSearchForRecentCommand() throws Exception {
        //TODO: Test goes here...
    }

    /**
     * Method: retrieveNextPlayerIndex(int currentPlayerIndex)
     */
    @Test
    public void testRetrieveNextPlayerIndex() throws Exception {
        //TODO: Test goes here...
    }


    /**
     * Method: executeNextCommand()
     */
    @Test
    public void testExecuteNextCommand() throws Exception {
        //TODO: Test goes here...
        /*
        try {
           Method method = GameSession.getClass().getMethod("executeNextCommand");
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
    }

    /**
     * Method: checkForHooks(SessionCommand command, Map<Class<? extends SessionCommand>, CommandHook> hookMap)
     */
    @Test
    public void testCheckForHooks() throws Exception {
        //TODO: Test goes here...
        /*
        try {
           Method method = GameSession.getClass().getMethod("checkForHooks", SessionCommand.class, Map<Class<?.class);
           method.setAccessible(true);
           method.invoke(<Object>, <Parameters>);
        } catch(NoSuchMethodException e) {
        } catch(IllegalAccessException e) {
        } catch(InvocationTargetException e) {
        }
        */
    }

} 
