package csaba.bejan.twitterlite.controller.formatter;

import csaba.bejan.twitterlite.domain.Message;

/**
 * Message Formatter.
 *
 * @author Csaba Bejan
 *
 */
public interface MessageFormatter {

    /**
     * Formats the message.
     *
     * @param message the message to be formatted
     * @return the formatted string message
     */
    String format(Message message);

    /**
     * Formats the message with the name prefix.
     *
     * @param message the message to be formatted
     * @return the formatted string message
     */
    String formatWithName(Message message);
}
