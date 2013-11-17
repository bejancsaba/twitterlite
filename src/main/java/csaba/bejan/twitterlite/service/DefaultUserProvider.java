package csaba.bejan.twitterlite.service;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.User;

/**
 * Default implementation for the {@link UserProvider} interface.
 *
 * @author Csaba Bejan
 *
 */
public class DefaultUserProvider implements UserProvider {
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;

    @Override
    public User getUser(String name) {
        User user = twitterLiteDataStoreDao.getUserForName(name);
        if (user == null) {
            user = twitterLiteDataStoreDao.createUserWithName(name);
        }
        return user;
    }

    public void setTwitterLiteDataStoreDao(TwitterLiteDataStoreDao twitterLiteDataStoreDao) {
        this.twitterLiteDataStoreDao = twitterLiteDataStoreDao;
    }
}
