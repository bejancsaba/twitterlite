package csaba.bejan.twitterlite;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

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
 * Integration test covering the provided scenarios in the AC.
 *
 * @author Csaba Bejan
 *
 */
public class TwitterLiteIntegrationTest {
    private static final TwitterLiteDataStoreDao DATA_STORE_DAO = new InMemoryTwitterLiteDataStoreDao();
    private static final MessageFormatter TIME_STAMPED_MESSAGE_FORMATTER = new TimeStampedMessageFormatter();
    private static final Comparator<Message> COMPARATOR = new MessagePostTimeComparator();
    private static final UserProvider USER_PROVIDER = DefaultUserProvider.create(DATA_STORE_DAO);
    private static final TwitterLiteTaskProcessor TASK_PROCESSOR = DefaultTwitterLiteTaskProcessor
            .create(DATA_STORE_DAO, TIME_STAMPED_MESSAGE_FORMATTER, COMPARATOR);
    private static final TwitterLiteInputProcessor INPUT_PROCESSOR = DefaultTwitterLiteInputProcessor.create(USER_PROVIDER);

    @Test
    public void shouldSatisfyACRequirements() throws InterruptedException {
        Map<String, List<String>> inputResponseMap = new LinkedHashMap<String, List<String>>();
        inputResponseMap.put("Alice -> I love the weather today", Collections.<String>emptyList());
        inputResponseMap.put("Bob -> at least it's sunny", Collections.<String>emptyList());
        inputResponseMap.put("Bob -> Oh, we lost!", Collections.<String>emptyList());
        inputResponseMap.put("Alice", Arrays.asList("I love the weather today"));
        inputResponseMap.put("Bob", Arrays.asList("Oh, we lost!", "at least it's sunny"));
        inputResponseMap.put("Charlie -> I'm in New York today! Anyone wants to have a coffee?", Collections.<String>emptyList());
        inputResponseMap.put("Charlie follows Alice", Collections.<String>emptyList());
        inputResponseMap.put("Charlie wall",
                Arrays.asList("Charlie - I'm in New York today! Anyone wants to have a coffee?", "Alice - I love the weather today"));
        inputResponseMap.put("Charlie follows Bob", Collections.<String>emptyList());
        for (String command : inputResponseMap.keySet()) {
            Task task = INPUT_PROCESSOR.process(command);
            List<String> result = TASK_PROCESSOR.process(task);
            // Needed so we can be sure that the messages has different time stamps
            Thread.sleep(1);
            verifyResult(inputResponseMap.get(command), result);
        }

        // Had to separate due to collosion in map keys
        Task task = INPUT_PROCESSOR.process("Charlie wall");
        List<String> result = TASK_PROCESSOR.process(task);
        Thread.sleep(1);
        verifyResult(Arrays.asList(
                "Charlie - I'm in New York today! Anyone wants to have a coffee?",
                "Bob - Oh, we lost!",
                "Bob - at least it's sunny",
                "Alice - I love the weather today"), result);
    }

    private void verifyResult(List<String> expected, List<String> returned) {
        for (int index = 0; index < returned.size(); index++) {
            assertTrue(returned.get(index).startsWith(expected.get(index)));
        }
    }
}
