package csaba.bejan.twitterlite.service;

import csaba.bejan.twitterlite.domain.User;

/**
 * Provides user entity.
 *
 * @author Csaba Bejan
 *
 */
public interface UserProvider {

    /**
     * Returns a user for the given name, if it doesn't exist it creates it.
     *
     * @param name the name of the user to be created
     * @return the created user
     */
    User getUser(String name);
}
