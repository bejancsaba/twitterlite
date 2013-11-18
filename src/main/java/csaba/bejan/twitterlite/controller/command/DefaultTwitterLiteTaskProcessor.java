package csaba.bejan.twitterlite.controller.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.domain.User;
import csaba.bejan.twitterlite.presentation.formatter.MessageFormatter;

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
        if (task != null && task.getAction() != null) {
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
                    List<Message> wallResponse = twitterLiteDataStoreDao.getMessageListForUser(task.getOrigin());
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
