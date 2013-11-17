package csaba.bejan.twitterlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import csaba.bejan.twitterlite.controller.command.DefaultTwitterLiteActionCommand;
import csaba.bejan.twitterlite.dao.InMemoryTwitterLiteDataStoreDao;
import csaba.bejan.twitterlite.service.DefaultUserProvider;

/**
 * Main class for TwitterLite.
 *
 * @author Csaba Bejan
 *
 */
public final class TwitterLite {
    private static final InMemoryTwitterLiteDataStoreDao DATA_STORE_DAO = new InMemoryTwitterLiteDataStoreDao();
    private static final DefaultUserProvider USER_PROVIDER = new DefaultUserProvider();
    private static final DefaultTwitterLiteActionCommand COMMAND = new DefaultTwitterLiteActionCommand();

    private TwitterLite() {
        //not called
    }

    public static void main(String[] args) {
        // initialize
        USER_PROVIDER.setTwitterLiteDataStoreDao(DATA_STORE_DAO);
        COMMAND.setTwitterLiteDataStoreDao(DATA_STORE_DAO);
        COMMAND.setUserProvider(USER_PROVIDER);

        while (true) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.print("> ");
                String input = inputReader.readLine();
                List<String> result = COMMAND.execute(input);
                if (result != null) {
                    for (String formattedMessage : result) {
                        System.out.println(formattedMessage);
                    }
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println("Exception during input, this shouldn't happen");
            }
        }
    }
}
