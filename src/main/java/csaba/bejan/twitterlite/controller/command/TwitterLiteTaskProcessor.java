package csaba.bejan.twitterlite.controller.command;

import java.util.Comparator;
import java.util.List;

import csaba.bejan.twitterlite.controller.formatter.MessageFormatter;
import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Message;
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

    /**
     * Set the data store dao.
     *
     * @param dataStoreDao the dataStoreDao
     */
    void setTwitterLiteDataStoreDao(TwitterLiteDataStoreDao dataStoreDao);

    /**
     * Set the message formatter.
     *
     * @param messageFormatter the messageFormatter
     */
    void setMessageFormatter(MessageFormatter messageFormatter);

    /**
     * Set the comparator.
     *
     * @param comparator the comparator
     */
    void setMessageComparator(Comparator<Message> comparator);
}
