package csaba.bejan.twitterlite.command.comparator;

import java.util.Comparator;

import csaba.bejan.twitterlite.domain.Message;

/**
 * Compares message time stamps.
 *
 * @author Csaba Bejan
 *
 */
public class MessagePostTimeComparator implements Comparator<Message> {

    @Override
    public int compare(Message msg1, Message msg2) {
        int retValue = 0;
        if (msg1.getTimeStamp() < msg2.getTimeStamp()) {
            retValue = 1;
        } else if (msg1.getTimeStamp() > msg2.getTimeStamp()) {
            retValue = -1;
        }
        return retValue;
    }
}
