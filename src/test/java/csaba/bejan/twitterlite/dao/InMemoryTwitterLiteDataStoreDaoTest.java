package csaba.bejan.twitterlite.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.User;

/**
 * Unit test for {@link InMemoryTwitterLiteDataStoreDao}.
 *
 * @author Csaba Bejan
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class InMemoryTwitterLiteDataStoreDaoTest {
    private InMemoryTwitterLiteDataStoreDao inMemoryTwitterLiteDataStoreDao;

    @Before
    public void initDataStore() {
        inMemoryTwitterLiteDataStoreDao = new InMemoryTwitterLiteDataStoreDao();
    }

    @Test
    public void shouldReturnNullForMissingUser() {
        assertNull(inMemoryTwitterLiteDataStoreDao.getUserForName("nonExistingUser"));
    }

    @Test
    public void shouldCreateAndReturnUserForName() {
        User user = inMemoryTwitterLiteDataStoreDao.createUserWithName("testUser");
        assertEquals(user, inMemoryTwitterLiteDataStoreDao.getUserForName("testUser"));
    }

    @Test
    public void shouldUserForName() {
        User user = inMemoryTwitterLiteDataStoreDao.createUserWithName("testUser");
        assertEquals(user, inMemoryTwitterLiteDataStoreDao.getUserForName("testUser"));
    }

    @Test
    public void shouldReturnEmptyMessageListForNewUser() {
        User user = inMemoryTwitterLiteDataStoreDao.createUserWithName("testUser");
        assertTrue(inMemoryTwitterLiteDataStoreDao.getMessageListForUser(user).isEmpty());
    }

    @Test
    public void shouldReturnMessageListForUser() {
        User user = inMemoryTwitterLiteDataStoreDao.createUserWithName("testUser");
        inMemoryTwitterLiteDataStoreDao.addMessageForUser(user, aMessageWithText("text 1"));
        inMemoryTwitterLiteDataStoreDao.addMessageForUser(user, aMessageWithText("text 2"));
        assertEquals(2, inMemoryTwitterLiteDataStoreDao.getMessageListForUser(user).size());
        assertEquals("text 1", inMemoryTwitterLiteDataStoreDao.getMessageListForUser(user).get(0).getMessageText());
        assertEquals("text 2", inMemoryTwitterLiteDataStoreDao.getMessageListForUser(user).get(1).getMessageText());
    }

    @Test
    public void shouldntAddMessageForNonExistingUser() {
        User user = aUserWithName("nonexistent");
        inMemoryTwitterLiteDataStoreDao.addMessageForUser(user, aMessageWithText("message"));
        assertNull(inMemoryTwitterLiteDataStoreDao.getMessageListForUser(user));
    }

    private User aUserWithName(String name) {
        return new User.UserBuilder().withName(name).build();
    }

    private Message aMessageWithText(String text) {
        return new Message.MessageBuilder().withText(text).build();
    }
}
