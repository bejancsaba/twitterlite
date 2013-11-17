package csaba.bejan.twitterlite.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.User;

/**
 * Unit test for {@link DefaultUserProvider}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserProviderTest {
    private final DefaultUserProvider defaultUserProvider = new DefaultUserProvider();

    @Mock
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;

    @Before
    public void initProvider() {
        defaultUserProvider.setTwitterLiteDataStoreDao(twitterLiteDataStoreDao);
    }

    @Test
    public void shouldReturnExistingUser() {
        User mockUser = mock(User.class);
        when(twitterLiteDataStoreDao.getUserForName("test")).thenReturn(mockUser);
        User returnedUser = defaultUserProvider.getUser("test");
        assertEquals(mockUser, returnedUser);
        verify(twitterLiteDataStoreDao, never()).createUserWithName(anyString());
    }

    @Test
    public void shouldCreateNonExistingUserIfRequested() {
        User mockUser = mock(User.class);
        when(twitterLiteDataStoreDao.getUserForName("test")).thenReturn(null);
        when(twitterLiteDataStoreDao.createUserWithName("test")).thenReturn(mockUser);
        User returnedUser = defaultUserProvider.getUser("test", true);
        assertEquals(mockUser, returnedUser);
    }

    @Test
    public void shouldNotCreateNonExistingUserIfNotRequested() {
        User mockUser = mock(User.class);
        when(twitterLiteDataStoreDao.getUserForName("test")).thenReturn(null);
        User returnedUser = defaultUserProvider.getUser("test");
        assertNull(returnedUser);
        verify(twitterLiteDataStoreDao, never()).createUserWithName(anyString());
    }
}
