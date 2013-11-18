package csaba.bejan.twitterlite.domain;

import java.util.Comparator;

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
