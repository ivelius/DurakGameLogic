package tests.commands.core;

import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.commands.core.MoveCardFromPileToPileCommand;
import com.yan.durak.gamelogic.exceptions.CardNotFoundException;
import com.yan.durak.gamelogic.exceptions.PileNotFoundException;
import com.yan.durak.gamelogic.game.GameSession;
import factories.GameSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tests.BaseTest;


public class MoveCardFromPileToPileCommandTest extends BaseTest {

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
     * Moving one card from pile that contains one card to pile that contains zero cards
     * <p/>
     * Expected : card removed from pile "0" and placed to pile "1"
     *
     * @throws Exception
     */
    @Test
    public void testMoveCardFromOnePileToAnotherEmptyPile() throws Exception {

        //put one card to pile zero
        Card card = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        mGameSession.getPilesStack().get(0).addCardToPile(card);

        //make the move from pile 0 to pile 1
        MoveCardFromPileToPileCommand command = new MoveCardFromPileToPileCommand();
        command.setFromPileIndex(0);
        command.setToPileIndex(1);
        command.setCardToMove(card);
        mGameSession.executeCommand(command);

        //assert sizes
        Assert.assertEquals("Expected size of pile 0 after move is 0 ", mGameSession.getPilesStack().get(0).getCardsInPile().size(), 0);
        Assert.assertEquals("Expected size of pile 1 after move is 1 ", mGameSession.getPilesStack().get(1).getCardsInPile().size(), 1);

        //assert the card is the same
        Assert.assertEquals("Card expected to be " + card, card, mGameSession.getPilesStack().get(1).getCardsInPile().get(0));
    }

    /**
     * Moving one card from pile that contains zero cards to another pile
     * <p/>
     * Expected : card not found exception is thrown
     *
     * @throws Exception
     */
    @Test(expected = CardNotFoundException.class)
    public void testMoveCardFromOneEmptyPileToAnotherEmptyPile() throws Exception {

        //create card to be moved
        Card card = new Card(Card.Rank.ACE, Card.Suit.CLUBS);

        //make the move from pile 0 to pile 1
        MoveCardFromPileToPileCommand command = new MoveCardFromPileToPileCommand();
        command.setFromPileIndex(0);
        command.setToPileIndex(1);
        command.setCardToMove(card);
        mGameSession.executeCommand(command);
    }

    /**
     * Moving from or to pile that doesn't exist
     * <p/>
     * Expected : pile not found Exception thrown
     *
     * @throws Exception
     */
    @Test(expected = PileNotFoundException.class)
    public void testNonExistentPiles() throws Exception {

        //create card to be moved
        Card card = new Card(Card.Rank.ACE, Card.Suit.CLUBS);

        //to non existent pile
        MoveCardFromPileToPileCommand command = new MoveCardFromPileToPileCommand();
        command.setFromPileIndex(0);
        command.setToPileIndex(20);
        command.setCardToMove(card);
        mGameSession.executeCommand(command);

        //from non existent pile
        command.setFromPileIndex(17);
        command.setToPileIndex(20);
        command.setCardToMove(card);
        mGameSession.executeCommand(command);
    }


}