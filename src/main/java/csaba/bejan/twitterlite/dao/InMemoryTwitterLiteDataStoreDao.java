package csaba.bejan.twitterlite.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.User;

/**
 * Default implementation for {@link TwitterLiteDataStoreDao}.
 *
 * @author Csaba Bejan
 *
 */
public class InMemoryTwitterLiteDataStoreDao implements TwitterLiteDataStoreDao {
    private Map<User, List<Message>> dataStore = new HashMap<User, List<Message>>();

    @Override
    public void addMessageForUser(User user, Message message) {
        if (dataStore.get(user) != null) {
            dataStore.get(user).add(message);
        }
    }

    @Override
    public List<Message> getMessageListForUser(User user) {
        return dataStore.get(user);
    }

    @Override
    public User getUserForName(String userName) {
        User user = null;
        for (User actUser : dataStore.keySet()) {
            if (userName.equals(actUser.getUserName())) {
                user = actUser;
                break;
            }
        }
        return user;
    }

    @Override
    public User createUserWithName(String userName) {
        User user = new User.UserBuilder().withName(userName).build();
        dataStore.put(user, new ArrayList<Message>());
        return user;
    }
}
