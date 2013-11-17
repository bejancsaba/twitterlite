package csaba.bejan.twitterlite.domain;

/**
 * A simple message.
 *
 * @author Csaba Bejan
 *
 */
public class Message implements TwitterLiteEntity {
    private String messageText;

    /**
     * Creates an empty message.
     */
    public Message() {
    }

    /**
     * Creates a message from a builder.
     *
     * @param builder the builder to use
     */
    public Message(MessageBuilder builder) {
        this.messageText = builder.messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     * Builder for {@link Message}.
     *
     * @author Csaba Bejan
     *
     */
    public static final class MessageBuilder {
        private String messageText;

        /**
         * Sets the messageText.
         *
         * @param id the id
         * @return this builder instance
         */
        public MessageBuilder withText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        /**
         * Build a message based on this builder.
         *
         * @return the built message
         */
        public Message build() {
            return new Message(this);
        }
    }
}
