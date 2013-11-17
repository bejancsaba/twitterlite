package csaba.bejan.twitterlite.controller.command;

import java.util.List;

import csaba.bejan.twitterlite.domain.Task;

/**
 * Processes the task.
 *
 * @author Csaba Bejan
 *
 */
public interface TwitterLiteTaskProcessor {

    /**
     * Processes the task.
     *
     * @param task the task to be handled
     * @return the result to be displayed
     */
    List<String> process(Task task);
}
