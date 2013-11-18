package csaba.bejan.twitterlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import csaba.bejan.twitterlite.command.comparator.MessagePostTimeComparator;
import csaba.bejan.twitterlite.controller.command.DefaultTwitterLiteInputProcessor;
import csaba.bejan.twitterlite.controller.command.DefaultTwitterLiteTaskProcessor;
import csaba.bejan.twitterlite.dao.InMemoryTwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.presentation.formatter.TimeStampedMessageFormatter;
import csaba.bejan.twitterlite.service.DefaultUserProvider;

/**
 * Main class for TwitterLite.
 *
 * @author Csaba Bejan
 *
 */
public final class TwitterLite {
    private static final InMemoryTwitterLiteDataStoreDao DATA_STORE_DAO = new InMemoryTwitterLiteDataStoreDao();
    private static final TimeStampedMessageFormatter TIME_STAMPED_MESSAGE_FORMATTER = new TimeStampedMessageFormatter();
    private static final DefaultUserProvider USER_PROVIDER = new DefaultUserProvider();
    private static final DefaultTwitterLiteTaskProcessor TASK_PROCESSOR = new DefaultTwitterLiteTaskProcessor();
    private static final DefaultTwitterLiteInputProcessor INPUT_PROCESSOR = new DefaultTwitterLiteInputProcessor();
    private static final MessagePostTimeComparator COMPARATOR = new MessagePostTimeComparator();

    private TwitterLite() {
        //not called
    }

    public static void main(String[] args) {
        // initialize
        USER_PROVIDER.setTwitterLiteDataStoreDao(DATA_STORE_DAO);
        INPUT_PROCESSOR.setUserProvider(USER_PROVIDER);
        TASK_PROCESSOR.setTwitterLiteDataStoreDao(DATA_STORE_DAO);
        TASK_PROCESSOR.setMessageFormatter(TIME_STAMPED_MESSAGE_FORMATTER);
        TASK_PROCESSOR.setMessageComparator(COMPARATOR);

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
