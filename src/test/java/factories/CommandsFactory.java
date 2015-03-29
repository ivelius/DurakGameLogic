package factories;


import commands.SimpleExecutableMockSessionCommand;

/**
 * Created by ybra on 26/01/15.
 */
public class CommandsFactory {

    public static SimpleExecutableMockSessionCommand createSimpleExecutableCommand() {
        return new SimpleExecutableMockSessionCommand();
    }
}
