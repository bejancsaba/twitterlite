package csaba.bejan.twitterlite.controller.formatter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import csaba.bejan.twitterlite.domain.Message;

/**
 * Formats the message with proper time stamp.
 *
 * @author Csaba Bejan
 *
 */
public class TimeStampedMessageFormatter implements MessageFormatter {
    private static final String FORMAT = "%s (%d %s%s ago)";
    private static final String NAME_PREFIXED_FORMAT = "%s - %s";

    @Override
    public String format(Message message) {
        int timeUnitCount = 0;
        String timeUnitName = "second";
        String plural = "";
        long elapsedTime = getCurrentTimeInMilis() - message.getTimeStamp();
        if (TimeUnit.MILLISECONDS.toDays(elapsedTime) > 0) {
            timeUnitCount = (int) TimeUnit.MILLISECONDS.toDays(elapsedTime);
            timeUnitName = "day";
        } else if (TimeUnit.MILLISECONDS.toHours(elapsedTime) > 0) {
            timeUnitCount = (int) TimeUnit.MILLISECONDS.toHours(elapsedTime);
            timeUnitName = "hour";
        } else if (TimeUnit.MILLISECONDS.toMinutes(elapsedTime) > 0) {
            timeUnitCount = (int) TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
            timeUnitName = "minute";
        } else if (TimeUnit.MILLISECONDS.toSeconds(elapsedTime) > 0) {
            timeUnitCount = (int) TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
            timeUnitName = "second";
        }

        if (timeUnitCount > 1) {
            plural = "s";
        }
        return String.format(FORMAT, message.getMessageText(), timeUnitCount, timeUnitName, plural);
    }

    @Override
    public String formatWithName(Message message) {
        return String.format(NAME_PREFIXED_FORMAT, message.getSenderName(), format(message));
    }

    protected long getCurrentTimeInMilis() {
        return new Date().getTime();
    }
}
