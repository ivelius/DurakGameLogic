package com.yan.durak.gamelogic.commands.control;


import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.cards.Pile;
import com.yan.durak.gamelogic.commands.core.MoveCardFromPileToPileCommand;
import com.yan.durak.gamelogic.commands.custom.AddPileCommand;
import com.yan.durak.gamelogic.commands.custom.PlayerAttackRequestCommand;
import com.yan.durak.gamelogic.player.Player;

import java.util.ArrayList;

/**
 * Created by Yan-Home on 12/22/2014.
 * <p/>
 * This command will analyze attack command and dispatch new commands accordingly.
 */
public class AttackRequestControlCommand extends BaseControlCommand<PlayerAttackRequestCommand> {

    @Override
    public void execute() {

        //find recent controlled command in the stack
        PlayerAttackRequestCommand attackCommand = searchForRecentControlledCommand();

        //create a pile that will be used as an additional pile on the field
        AddPileCommand addPileCommand = new AddPileCommand();
        addPileCommand.setCards(new ArrayList<Card>());
        //create pile and tag it as field pile
        Pile pile = new Pile();
        pile.addTag(Pile.PileTags.FIELD_PILE);
        addPileCommand.setPile(pile);
        getGameSession().executeCommand(addPileCommand);

        //find attacking player by index
        Player attackingPlayer = getGameSession().getPlayers().get(attackCommand.getAttackingPlayerIndex());

        //remove the card from attacking player pile and move it to relevant field pile
        MoveCardFromPileToPileCommand moveCardFromPileToPileCommand = new MoveCardFromPileToPileCommand();
        moveCardFromPileToPileCommand.setCardToMove(attackCommand.getChosenCardForAttack());
        moveCardFromPileToPileCommand.setFromPileIndex(attackingPlayer.getPileIndex());
        moveCardFromPileToPileCommand.setToPileIndex(getGameSession().getPilesStack().indexOf(pile));
        getGameSession().executeCommand(moveCardFromPileToPileCommand);
    }

    @Override
    protected Class<PlayerAttackRequestCommand> getControlledCommandClass() {
        return PlayerAttackRequestCommand.class;
    }
}
