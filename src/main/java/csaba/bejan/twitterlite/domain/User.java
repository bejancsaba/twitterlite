package csaba.bejan.twitterlite.domain;

/**
 * Information about the user.
 *
 * @author Csaba Bejan
 *
 */
public class User {
    private String userName;

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
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Builder for {@link User}.
     *
     * @author Csaba Bejan
     *
     */
    public static final class UserBuilder {
        private String userName;

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
         * Build a user based on this builder.
         *
         * @return the built user
         */
        public User build() {
            return new User(this);
        }
    }
}
