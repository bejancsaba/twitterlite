package csaba.bejan.twitterlite.controller.command;

import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.service.UserProvider;

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

    /**
     * Set the user provider.
     *
     * @param userProvider the userProvider to be used
     */
    void setUserProvider(UserProvider userProvider);
}
