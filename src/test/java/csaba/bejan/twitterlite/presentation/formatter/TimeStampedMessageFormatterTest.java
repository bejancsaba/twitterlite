package csaba.bejan.twitterlite.presentation.formatter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.domain.Message;


/**
 * Unit test for {@link TimeStampedMessageFormatter}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TimeStampedMessageFormatterTest {
    private static final long ONE_SEC = 1000L;
    private static final long TWO_SEC = 2000L;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private final TimeStampedMessageFormatter timeStampedMessageFormatter = spy(new TimeStampedMessageFormatter());

    @Test
    public void oneSecond() {
        Message message = aMessageWith("Text", 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(ONE_SEC);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals("Text (1 second ago)", result);
    }

    @Test
    public void twoSeconds() {
        Message message = aMessageWith("Text", 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(TWO_SEC);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals("Text (2 seconds ago)", result);
    }

    @Test
    public void oneMinute() {
        Message message = aMessageWith("Text", 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(ONE_MINUTE);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals("Text (1 minute ago)", result);
    }

    @Test
    public void oneHour() {
        Message message = aMessageWith("Text", 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(ONE_HOUR);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals("Text (1 hour ago)", result);
    }

    @Test
    public void oneDay() {
        Message message = aMessageWith("Text", 0L);
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(ONE_DAY);
        String result = timeStampedMessageFormatter.format(message);
        assertEquals("Text (1 day ago)", result);
    }

    @Test
    public void withSender() {
        Message message = new Message.MessageBuilder().withText("Text").withTimeStamp(0L).withSenderName("senderName").build();
        when(timeStampedMessageFormatter.getCurrentTimeInMilis()).thenReturn(ONE_SEC);
        String result = timeStampedMessageFormatter.formatWithName(message);
        assertEquals("senderName - Text (1 second ago)", result);
    }

    private Message aMessageWith(String text, long timestamp) {
        return new Message.MessageBuilder().withText(text).withTimeStamp(timestamp).build();
    }
}
