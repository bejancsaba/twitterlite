package csaba.bejan.twitterlite.controller.formatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;

import csaba.bejan.twitterlite.domain.Message;


/**
 * Unit test for {@link TimeStampedMessageFormatter}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(Parameterized.class)
public class TimeStampedMessageFormatterTest {
    private static final long ONE_SEC = 1000L;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final String TEXT = "Text";
    private static final String SENDER_NAME = "senderName";
    private TimeStampedMessageFormatter timeStampedMessageFormatter;
    private String expectedMessage;
    private String expectedMessageWithSender;
    private Long interval;

    @Before
    public void initialize() {
        timeStampedMessageFormatter = spy(new TimeStampedMessageFormatter());
    }

    public TimeStampedMessageFormatterTest(String expectedMessage, Long interval) {
       this.expectedMessage = expectedMessage;
       this.expectedMessageWithSender = SENDER_NAME + " - " + expectedMessage;
       this.interval = interval;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> messages() {
       return Arrays.asList(new Object[][] {
          {TEXT + " (1 second ago)", ONE_SEC },
          {TEXT + " (2 seconds ago)", ONE_SEC * 2 },
          {TEXT + " (1 minute ago)", ONE_MINUTE },
          {TEXT + " (2 minutes ago)", ONE_MINUTE * 2 },
          {TEXT + " (1 hour ago)", ONE_HOUR },
          {TEXT + " (2 hours ago)", ONE_HOUR * 2 },
          {TEXT + " (1 day ago)", ONE_DAY },
          {TEXT + " (2 days ago)", ONE_DAY * 2 }
       });
    }

    @Test
    public void testTimeStampedMessageFormatter() {
        Message message = aMessageWith(TEXT, 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(interval);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals(expectedMessage, result);
    }

    @Test
    public void withSender() {
        Message message = new Message.MessageBuilder().withText(TEXT).withTimeStamp(0L).withSenderName(SENDER_NAME).build();
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(interval);
        String result = timeStampedMessageFormatter.formatWithName(message);
        assertEquals(expectedMessageWithSender, result);
    }

    private Message aMessageWith(String text, long timestamp) {
        return new Message.MessageBuilder().withText(text).withTimeStamp(timestamp).build();
    }
}
