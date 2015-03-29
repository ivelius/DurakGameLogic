package factories;


import com.yan.durak.gamelogic.cards.Pile;

/**
 * Created by Yan-Home on 1/22/2015.
 */
public class PileFactory {

    public static Pile createEmptyPile() {
        return new Pile();
    }

    public static Pile createEmptyPileWithTag(String tag) {
        Pile pile = createEmptyPile();
        pile.addTag(tag);
        return pile;
    }
}
