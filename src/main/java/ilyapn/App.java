package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Service
public class App {
    @Autowired
    UserConsole userConsole;
    @Autowired
    ElevatorCar elevatorCar;

    public void app(){
        ExecutorService userThread = Executors.newSingleThreadExecutor();
        ExecutorService elevatorThread = Executors.newSingleThreadExecutor();

        elevatorThread.submit(() -> elevatorCar.start());
        userThread.submit(() -> userConsole.start());

    }
}
