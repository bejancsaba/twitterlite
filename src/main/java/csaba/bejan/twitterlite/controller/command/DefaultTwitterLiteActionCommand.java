package csaba.bejan.twitterlite.controller.command;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csaba.bejan.twitterlite.dao.TwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.service.UserProvider;

/**
 * Default command for the {@link TwitterLiteActionCommand} interface.
 *
 * @author Csaba Bejan
 *
 */
public class DefaultTwitterLiteActionCommand implements TwitterLiteActionCommand {
    private static final Pattern POST_PATTERN = Pattern.compile("(.+) -> (.+)");

    private UserProvider userProvider;
    private TwitterLiteDataStoreDao twitterLiteDataStoreDao;

    @Override
    public List<String> execute(String input) {
        Matcher matcher = POST_PATTERN.matcher(input);
        if (matcher.matches()) {
            twitterLiteDataStoreDao.addMessageForUser(userProvider.getUser(matcher.group(1)), matcher.group(2));
        }
        return null;
    }

    public void setUserProvider(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    public void setTwitterLiteDataStoreDao(TwitterLiteDataStoreDao twitterLiteDataStoreDao) {
        this.twitterLiteDataStoreDao = twitterLiteDataStoreDao;
    }
}
