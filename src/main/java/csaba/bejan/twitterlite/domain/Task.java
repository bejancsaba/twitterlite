package csaba.bejan.twitterlite.domain;

/**
 * Task describing the actions to do in the back end.
 *
 * @author Csaba Bejan
 *
 */
public class Task {
    private User origin;
    private TwitterLiteEntity target;
    private Action action;

    /**
     * Creates a task from a builder.
     *
     * @param builder the builder to use
     */
    public Task(TaskBuilder builder) {
        this.origin = builder.origin;
        this.target = builder.target;
        this.action = builder.action;
    }

    public User getOrigin() {
        return origin;
    }

    public TwitterLiteEntity getTarget() {
        return target;
    }

    public Action getAction() {
        return action;
    }

    /**
     * Builder for {@link Task}.
     *
     * @author Csaba Bejan
     *
     */
    public static final class TaskBuilder {
        private User origin;
        private TwitterLiteEntity target;
        private Action action;

        /**
         * Sets the initiator of the action.
         *
         * @param origin the user the action is coming from
         * @return this builder instance
         */
        public TaskBuilder withOrigin(User origin) {
            this.origin = origin;
            return this;
        }

        /**
         * Sets the target of the action.
         *
         * @param target the target of the action
         * @return this builder instance
         */
        public TaskBuilder withTarget(TwitterLiteEntity target) {
            this.target = target;
            return this;
        }

        /**
         * Sets the action.
         *
         * @param action the action
         * @return this builder instance
         */
        public TaskBuilder withAction(Action action) {
            this.action = action;
            return this;
        }

        /**
         * Build a task based on this builder.
         *
         * @return the built task
         */
        public Task build() {
            return new Task(this);
        }
    }
}
