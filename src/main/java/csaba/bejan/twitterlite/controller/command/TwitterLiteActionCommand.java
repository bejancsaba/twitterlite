package csaba.bejan.twitterlite.controller.command;

import java.util.List;

/**
 * A post action to be executed.
 *
 * @author Csaba Bejan
 *
 */
public interface TwitterLiteActionCommand {

    /**
     * Executes the command.
     *
     * @param input the input string to be handled
     * @return the result to be displayed
     */
    List<String> execute(String input);
}
