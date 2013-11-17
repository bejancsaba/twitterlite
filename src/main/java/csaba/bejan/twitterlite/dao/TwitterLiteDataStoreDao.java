package csaba.bejan.twitterlite.dao;

import java.util.List;

import csaba.bejan.twitterlite.domain.User;

/**
 * Dao for handling the messages and users concerning the TwitterLite application.
 *
 * @author Csaba Bejan
 *
 */
public interface TwitterLiteDataStoreDao {

    /**
     * Adds the Message to the users message list.
     * If the user doesn't exist it will be added to the store.
     *
     * @param user the user we want to add the message for
     * @param message the message to be added
     * @return list of messages posted by the user
     */
    void addMessageForUser(User user, String message);

    /**
     * Returns the available messages for a given user.
     * If there are no messages posted for this user the list will be empty.
     *
     * @param user the user we want the messages for
     * @return list of messages posted by the user
     */
    List<String> getMessageListForUser(User user);

    /**
     * Returns the user for the name if exists.
     *
     * @param userName the name of the user we are looking for
     * @return the User object which represents the user or null if it doesn't exist
     */
    User getUserForName(String userName);

    /**
     * Creates a new user in the DataStore (with empty message list).
     *
     * @param userName the name of the user to be created
     * @return the User object which represents the new user
     */
    User createUserWithName(String userName);
}
