package csaba.bejan.twitterlite.controller.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import csaba.bejan.twitterlite.controller.formatter.MessageFormatter;
import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.domain.User;

/**
 * Default command for the {@link TwitterLiteTaskProcessor} interface.
 *
 * @author Csaba Bejan
 *
 */
public class DefaultTwitterLiteTaskProcessor implements TwitterLiteTaskProcessor {
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;
    private MessageFormatter messageFormatter;
    private Comparator<Message> messageComparator;

    @Override
    public List<String> process(Task task) {
        List<String> response = new ArrayList<String>();
        if (task != null && task.getAction() != null && task.getOrigin() != null) {
            switch (task.getAction()) {
                case POST :
                    twitterLiteDataStoreDao.addMessageForUser(task.getOrigin(), (Message) task.getTarget());
                    break;
                case READ :
                    List<Message> messageResponse = twitterLiteDataStoreDao.getMessageListForUser(task.getOrigin());
                    Collections.sort(messageResponse, messageComparator);
                    for (Message actMessage : messageResponse) {
                        response.add(messageFormatter.format(actMessage));
                    }
                    break;
                case FOLLOW :
                    task.getOrigin().addFollows((User) task.getTarget());
                    break;
                case WALL :
                    List<Message> wallResponse = new ArrayList<Message>();
                    wallResponse.addAll(twitterLiteDataStoreDao.getMessageListForUser(task.getOrigin()));
                    for (User actUser : task.getOrigin().getFollows()) {
                        wallResponse.addAll(twitterLiteDataStoreDao.getMessageListForUser(actUser));
                    }
                    Collections.sort(wallResponse, messageComparator);
                    for (Message actMessage : wallResponse) {
                        response.add(messageFormatter.formatWithName(actMessage));
                    }
                    break;
                default:
                    break;
            }
        }
        return response;
    }

    /**
     * Factory method for creating DefaultTwitterLiteTaskProcessor.
     *
     * @param dataStoreDao the dataStoreDao
     * @param messageFormatter the messageFormatter
     * @param messageComparator the messageComparator
     * @return the created DefaultTwitterLiteTaskProcessor
     */
    public static DefaultTwitterLiteTaskProcessor create(
            TwitterLiteDataStoreDao dataStoreDao,
            MessageFormatter messageFormatter,
            Comparator<Message> messageComparator) {
        DefaultTwitterLiteTaskProcessor taskProcessor = new DefaultTwitterLiteTaskProcessor();
        taskProcessor.setTwitterLiteDataStoreDao(dataStoreDao);
        taskProcessor.setMessageFormatter(messageFormatter);
        taskProcessor.setMessageComparator(messageComparator);
        return taskProcessor;
    }

    public void setTwitterLiteDataStoreDao(TwitterLiteDataStoreDao twitterLiteDataStoreDao) {
        this.twitterLiteDataStoreDao = twitterLiteDataStoreDao;
    }

    public void setMessageFormatter(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    public void setMessageComparator(Comparator<Message> messageComparator) {
        this.messageComparator = messageComparator;
    }
}
