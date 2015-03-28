package com.yan.durak.gamelogic.commands.custom;


import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.cards.Pile;
import com.yan.durak.gamelogic.commands.BaseSessionCommand;

import java.util.Collection;

/**
 * Created by ybra on 19.12.2014.
 * <p/>
 * Adds given pile fills it with given cards and pushes it into the
 * Stack of piles in game session.
 * <p/>
 * Can be used for stock pile initialization , or users hands initialization.
 */
public class AddPileCommand extends BaseSessionCommand {

    private Pile mPile;
    private Collection<Card> mCardsCollection;

    public void setPile(Pile pile) {
        mPile = pile;
    }

    public void setCards(Collection<Card> cardsCollection) {
        mCardsCollection = cardsCollection;
    }

    @Override
    public void execute() {
        for (Card card : mCardsCollection) {
            mPile.addCardToPile(card);
        }

        //add initialized pile to the session stack of piles
        getGameSession().getPilesStack().add(mPile);
    }
}
