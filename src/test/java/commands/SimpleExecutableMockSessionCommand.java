package commands;


import com.yan.durak.gamelogic.commands.BaseSessionCommand;

/**
 * Created by ybra on 26/01/15.
 */
public class SimpleExecutableMockSessionCommand extends BaseSessionCommand {

    private boolean mExecuted;

    @Override
    public void execute() {
        mExecuted = true;
    }

    public boolean isExecuted() {
        return mExecuted;
    }

}
