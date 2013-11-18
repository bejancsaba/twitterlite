package csaba.bejan.twitterlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

import csaba.bejan.twitterlite.controller.command.DefaultTwitterLiteInputProcessor;
import csaba.bejan.twitterlite.controller.command.DefaultTwitterLiteTaskProcessor;
import csaba.bejan.twitterlite.controller.command.TwitterLiteInputProcessor;
import csaba.bejan.twitterlite.controller.command.TwitterLiteTaskProcessor;
import csaba.bejan.twitterlite.controller.formatter.MessageFormatter;
import csaba.bejan.twitterlite.controller.formatter.TimeStampedMessageFormatter;
import csaba.bejan.twitterlite.dao.InMemoryTwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.MessagePostTimeComparator;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.service.DefaultUserProvider;
import csaba.bejan.twitterlite.service.UserProvider;

/**
 * Main class for TwitterLite.
 *
 * @author Csaba Bejan
 *
 */
public final class TwitterLite {
    private static final TwitterLiteDataStoreDao DATA_STORE_DAO = new InMemoryTwitterLiteDataStoreDao();
    private static final MessageFormatter TIME_STAMPED_MESSAGE_FORMATTER = new TimeStampedMessageFormatter();
    private static final Comparator<Message> COMPARATOR = new MessagePostTimeComparator();
    private static final UserProvider USER_PROVIDER = DefaultUserProvider.create(DATA_STORE_DAO);
    private static final TwitterLiteTaskProcessor TASK_PROCESSOR = DefaultTwitterLiteTaskProcessor
            .create(DATA_STORE_DAO, TIME_STAMPED_MESSAGE_FORMATTER, COMPARATOR);
    private static final TwitterLiteInputProcessor INPUT_PROCESSOR = DefaultTwitterLiteInputProcessor.create(USER_PROVIDER);

    private TwitterLite() {
        //not called
    }

    public static void main(String[] args) {
        while (true) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.print("> ");
                Task task = INPUT_PROCESSOR.process(inputReader.readLine());
                List<String> result = TASK_PROCESSOR.process(task);
                if (result != null && !result.isEmpty()) {
                    System.out.println();
                    for (String formattedMessage : result) {
                        System.out.println(formattedMessage);
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Exception during input, this shouldn't happen");
            }
        }
    }
}
