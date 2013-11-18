package csaba.bejan.twitterlite.service;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.domain.User;

/**
 * Provides user entity.
 *
 * @author Csaba Bejan
 *
 */
public interface UserProvider {

    /**
     * Returns a user for the given name, if it doesn't exist it creates it if requested.
     *
     * @param name the name of the user to be returned
     * @return the created user
     */
    User getUser(String name, boolean createIfNotExist);

    /**
     * Returns a user for the given name.
     *
     * @param name the name of the user to be returned
     * @return the created user
     */
    User getUser(String name);

    /**
     * Set the data store.
     *
     * @param dataStoreDao the dataStoreDao
     */
    void setTwitterLiteDataStoreDao(TwitterLiteDataStoreDao dataStoreDao);
}
