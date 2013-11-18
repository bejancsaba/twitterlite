package csaba.bejan.twitterlite.domain;

/**
 * A simple message.
 *
 * @author Csaba Bejan
 *
 */
public class Message implements TwitterLiteEntity {
    private String messageText;
    private String senderName;
    private long timeStamp;

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
        this.timeStamp = builder.timeStamp;
        this.senderName = builder.senderName;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * Builder for {@link Message}.
     *
     * @author Csaba Bejan
     *
     */
    public static final class MessageBuilder {
        private String messageText;
        private String senderName;
        private long timeStamp;

        /**
         * Sets the messageText.
         *
         * @param messageText the messageText
         * @return this builder instance
         */
        public MessageBuilder withText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        /**
         * Sets the senderName.
         *
         * @param senderName the senderName
         * @return this builder instance
         */
        public MessageBuilder withSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }


        /**
         * Sets the timeStamp of the message.
         *
         * @param id the id
         * @return this builder instance
         */
        public MessageBuilder withTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
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
