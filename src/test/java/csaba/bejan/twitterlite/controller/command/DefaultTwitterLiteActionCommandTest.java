package csaba.bejan.twitterlite.controller.command;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.User;
import csaba.bejan.twitterlite.service.UserProvider;

/**
 * Unit test for {@link DefaultTwitterLiteActionCommand}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTwitterLiteActionCommandTest {
    private final DefaultTwitterLiteActionCommand defaultTwitterLiteActionCommand = new DefaultTwitterLiteActionCommand();

    @Mock
    private UserProvider userProvider;
    @Mock
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;

    @Before
    public void initCommand() {
        defaultTwitterLiteActionCommand.setUserProvider(userProvider);
        defaultTwitterLiteActionCommand.setTwitterLiteDataStoreDao(twitterLiteDataStoreDao);
    }

    @Test
    public void shouldAddMessageToDataStore() {
        User mockUser = mock(User.class);
        when(userProvider.getUser(anyString())).thenReturn(mockUser);
        List<String> result = defaultTwitterLiteActionCommand.execute("Alice -> I love the weather today");
        assertNull(result);
        verify(userProvider, times(1)).getUser("Alice");
        verify(twitterLiteDataStoreDao, times(1)).addMessageForUser(mockUser, "I love the weather today");
    }

    @Test
    public void shouldNotAddMessageToDataStore() {
        List<String> result = defaultTwitterLiteActionCommand.execute("Alice");
        assertNull(result);
        verify(userProvider, never()).getUser(anyString());
        verify(twitterLiteDataStoreDao, never()).addMessageForUser(any(User.class), anyString());
    }
}
