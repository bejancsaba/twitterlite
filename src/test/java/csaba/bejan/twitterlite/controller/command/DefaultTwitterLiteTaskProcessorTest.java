package csaba.bejan.twitterlite.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    @Mock
    private Comparator<Message> messageComparator;

    @Before
    public void initCommand() {
        defaultTwitterLiteTaskProcessor.setTwitterLiteDataStoreDao(twitterLiteDataStoreDao);
        defaultTwitterLiteTaskProcessor.setMessageFormatter(messageFormatter);
        defaultTwitterLiteTaskProcessor.setMessageComparator(messageComparator);
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

    @Test
    public void shouldFollow() {
        User mockUser = mock(User.class);
        User mockFollowsUser = mock(User.class);
        List<String> result = defaultTwitterLiteTaskProcessor.process(aTask()
                .withAction(Action.FOLLOW).withOrigin(mockUser)
                .withTarget(mockFollowsUser).build());
        assertTrue(result.isEmpty());
        verify(mockUser, times(1)).addFollows(mockFollowsUser);
    }

    @Test
    public void shouldShowWall() {
        User mockUser = mock(User.class);
        User mockFollowsUser = mock(User.class);
        when(mockUser.getFollows()).thenReturn(Arrays.asList(mockFollowsUser));
        List<Message> responseMessageListUser = new ArrayList<Message>();
        responseMessageListUser.add(new Message.MessageBuilder().withText("text 1").build());
        responseMessageListUser.add(new Message.MessageBuilder().withText("text 2").build());
        List<Message> responseMessageListFollowsUser = new ArrayList<Message>();
        responseMessageListFollowsUser.add(new Message.MessageBuilder().withText("text 3").build());
        when(messageFormatter.formatWithName(any(Message.class))).thenReturn("");
        when(twitterLiteDataStoreDao.getMessageListForUser(mockUser)).thenReturn(responseMessageListUser);
        when(twitterLiteDataStoreDao.getMessageListForUser(mockFollowsUser)).thenReturn(responseMessageListFollowsUser);
        List<String> result = defaultTwitterLiteTaskProcessor.process(aTask().withAction(Action.WALL).withOrigin(mockUser).build());
        assertEquals(3, result.size());
        verify(twitterLiteDataStoreDao, times(1)).getMessageListForUser(mockUser);
        verify(twitterLiteDataStoreDao, times(1)).getMessageListForUser(mockFollowsUser);
    }

    private TaskBuilder aTask() {
        return new Task.TaskBuilder();
    }
}
