package csaba.bejan.twitterlite.controller.command;

import csaba.bejan.twitterlite.domain.Task;

/**
 * Processes the input.
 *
 * @author Csaba Bejan
 *
 */
public interface TwitterLiteInputProcessor {

    /**
     * Processes the input from console.
     *
     * @param input the input string to be processed
     * @return the action constructed from the input.
     */
    Task process(String input);
}
