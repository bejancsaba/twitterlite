package csaba.bejan.twitterlite.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about the user.
 *
 * @author Csaba Bejan
 *
 */
public class User implements TwitterLiteEntity {
    private String userName;
    private List<User> follows = new ArrayList<User>();;

    /**
     * Creates an empty user.
     */
    public User() {
    }

    /**
     * Creates a user from a builder.
     *
     * @param builder the builder to use
     */
    public User(UserBuilder builder) {
        this.userName = builder.userName;
        this.follows = builder.follows;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getFollows() {
        return follows;
    }

    public void addFollows(User user) {
        this.follows.add(user);
    }

    /**
     * Builder for {@link User}.
     *
     * @author Csaba Bejan
     *
     */
    public static final class UserBuilder {
        private String userName;
        private List<User> follows = new ArrayList<User>();

        /**
         * Sets the userName.
         *
         * @param userName the name of the user
         * @return this builder instance
         */
        public UserBuilder withName(String userName) {
            this.userName = userName;
            return this;
        }

        /**
         * Adds a person to follow.
         *
         * @param user the user which is followed
         * @return this builder instance
         */
        public UserBuilder withFollows(User user) {
            this.follows.add(user);
            return this;
        }

        /**
         * Build a user based on this builder.
         *
         * @return the built user
         */
        public User build() {
            return new User(this);
        }
    }
}
