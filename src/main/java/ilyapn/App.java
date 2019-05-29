package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Component
public class App {
    @Autowired
    private CommandLine commandLine;

    @Autowired
    public void app() {
        commandLine.start();

    }
}
