package csaba.bejan.twitterlite.controller.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.domain.Action;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.domain.User;
import csaba.bejan.twitterlite.service.UserProvider;

/**
 * Unit test for {@link DefaultTwitterLiteInputProcessor}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTwitterLiteInputProcessorTest {
    private final DefaultTwitterLiteInputProcessor defaultTwitterLiteInputProcessor = new DefaultTwitterLiteInputProcessor();

    @Mock
    private UserProvider userProvider;

    @Before
    public void initCommand() {
        defaultTwitterLiteInputProcessor.setUserProvider(userProvider);
    }

    @Test
    public void shouldCreatePostTask() {
        User mockUser = mock(User.class);
        when(userProvider.getUser("Alice", true)).thenReturn(mockUser);
        Task task = defaultTwitterLiteInputProcessor.process("Alice -> I love the weather today");
        verify(userProvider, times(1)).getUser("Alice", true);
        assertEquals(Action.POST, task.getAction());
        assertEquals(mockUser, task.getOrigin());
        assertEquals("I love the weather today", ((Message) task.getTarget()).getMessageText());
    }

    @Test
    public void shouldCreateReadTask() {
        User mockUser = mock(User.class);
        when(userProvider.getUser(anyString())).thenReturn(mockUser);
        Task task = defaultTwitterLiteInputProcessor.process("Alice");
        verify(userProvider, times(1)).getUser("Alice");
        assertEquals(Action.READ, task.getAction());
        assertEquals(mockUser, task.getOrigin());
    }

    @Test
    public void shouldNotCreateTask() {
        Task task = defaultTwitterLiteInputProcessor.process("This is not a valid task");
        assertNull(task);
    }
}
