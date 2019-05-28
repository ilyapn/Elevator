package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Component
public class App {
    @Autowired
    private CommandLine commandLine;
    @Autowired
    private ElevatorCar elevatorCar;

    @Autowired
    public void app() {
        ExecutorService userThread = Executors.newSingleThreadExecutor();
        ExecutorService elevatorThread = Executors.newSingleThreadExecutor();

        elevatorThread.submit(() -> elevatorCar.start());
        userThread.submit(() -> commandLine.start());

    }
}
