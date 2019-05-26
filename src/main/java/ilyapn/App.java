package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Service
public class App {
    @Autowired
    UserConsole userConsole;

    public void app(){
        userConsole.start();
    }
}
