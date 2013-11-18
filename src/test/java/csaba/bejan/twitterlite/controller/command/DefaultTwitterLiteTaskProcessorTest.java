package csaba.bejan.twitterlite.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.Action;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.domain.Task.TaskBuilder;
import csaba.bejan.twitterlite.domain.User;
import csaba.bejan.twitterlite.presentation.formatter.MessageFormatter;

/**
 * Unit test for {@link DefaultTwitterLiteTaskProcessor}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTwitterLiteTaskProcessorTest {
    private final DefaultTwitterLiteTaskProcessor defaultTwitterLiteTaskProcessor = new DefaultTwitterLiteTaskProcessor();

    @Mock
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;
    @Mock
    private MessageFormatter messageFormatter;

    @Before
    public void initCommand() {
        defaultTwitterLiteTaskProcessor.setTwitterLiteDataStoreDao(twitterLiteDataStoreDao);
        defaultTwitterLiteTaskProcessor.setMessageFormatter(messageFormatter);
    }

    @Test
    public void shouldReturnEmptyResponseWithInvalidAction() {
        List<String> result = defaultTwitterLiteTaskProcessor.process(aTask().build());
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldAddMessageToDataStore() {
        User mockUser = mock(User.class);
        Message mockMessage = mock(Message.class);
        List<String> result = defaultTwitterLiteTaskProcessor.process(aTask().withAction(Action.POST).withOrigin(mockUser).withTarget(mockMessage).build());
        assertTrue(result.isEmpty());
        verify(twitterLiteDataStoreDao, times(1)).addMessageForUser(mockUser, mockMessage);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldReadMessageFromDataStore() {
        User mockUser = mock(User.class);
        List<Message> responseMessageList = new ArrayList<Message>();
        responseMessageList.add(new Message.MessageBuilder().withText("text 1").build());
        responseMessageList.add(new Message.MessageBuilder().withText("text 2").build());
        when(messageFormatter.format(any(Message.class))).thenReturn("");
        when(twitterLiteDataStoreDao.getMessageListForUser(mockUser)).thenReturn(responseMessageList);
        List<String> result = defaultTwitterLiteTaskProcessor.process(aTask().withAction(Action.READ).withOrigin(mockUser).build());
        assertEquals(2, result.size());
        verify(twitterLiteDataStoreDao, times(1)).getMessageListForUser(mockUser);
    }

    private TaskBuilder aTask() {
        return new Task.TaskBuilder();
    }
}
