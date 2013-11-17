package csaba.bejan.twitterlite.controller.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csaba.bejan.twitterlite.domain.Action;
import csaba.bejan.twitterlite.domain.Message;
import csaba.bejan.twitterlite.domain.Task;
import csaba.bejan.twitterlite.domain.User;
import csaba.bejan.twitterlite.service.UserProvider;

/**
 * Default command for the {@link TwitterLiteInputProcessor} interface.
 *
 * @author Csaba Bejan
 *
 */
public class DefaultTwitterLiteInputProcessor implements TwitterLiteInputProcessor {
    private static final Pattern POST_PATTERN = Pattern.compile("([a-zA-Z\\d]+) -> (.+)");
    private static final Pattern READ_PATTERN = Pattern.compile("([a-zA-Z\\d]+)");
    private static final boolean SHOULD_CREATE_USER_IF_NOT_EXISTS = true;

    private UserProvider userProvider;

    @Override
    public Task process(String input) {
        Task task = null;
        Matcher matcher = POST_PATTERN.matcher(input);
        if (matcher.matches()) {
            User originUser = userProvider.getUser(matcher.group(1), SHOULD_CREATE_USER_IF_NOT_EXISTS);
            Message targetMessage = new Message.MessageBuilder().withText(matcher.group(2)).build();
            task = new Task.TaskBuilder().withOrigin(originUser).withTarget(targetMessage).withAction(Action.POST).build();
        }

        if (task == null) {
            matcher = READ_PATTERN.matcher(input);
            if (matcher.matches()) {
                User originUser = userProvider.getUser(matcher.group(1));
                task = new Task.TaskBuilder().withOrigin(originUser).withAction(Action.READ).build();
            }
        }

        return task;
    }

    public void setUserProvider(UserProvider userProvider) {
        this.userProvider = userProvider;
    }
}
