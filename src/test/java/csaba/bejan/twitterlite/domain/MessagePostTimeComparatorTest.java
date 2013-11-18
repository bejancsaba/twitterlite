package csaba.bejan.twitterlite.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link MessagePostTimeComparator}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MessagePostTimeComparatorTest {
    private final MessagePostTimeComparator messagePostTimeComparator = new MessagePostTimeComparator();

    @Test
    public void smaller() {
        Message message1 = aMessageWithTimeStamp(0L);
        Message message2 = aMessageWithTimeStamp(1L);
        assertEquals(1, messagePostTimeComparator.compare(message1, message2));
    }

    @Test
    public void bigger() {
        Message message1 = aMessageWithTimeStamp(1L);
        Message message2 = aMessageWithTimeStamp(0L);
        assertEquals(-1, messagePostTimeComparator.compare(message1, message2));
    }

    @Test
    public void equal() {
        Message message1 = aMessageWithTimeStamp(1L);
        Message message2 = aMessageWithTimeStamp(1L);
        assertEquals(0, messagePostTimeComparator.compare(message1, message2));
    }

    private Message aMessageWithTimeStamp(long timestamp) {
        return new Message.MessageBuilder().withTimeStamp(timestamp).build();
    }
}
