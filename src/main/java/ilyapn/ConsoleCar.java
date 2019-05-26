package ilyapn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ilyaP on 25.05.2019.
 */
@Component
public class ConsoleCar {
    private boolean[] floorButtonPressed;

    public ConsoleCar(@Value("#{new Integer('${floors}')}") int numberOfFloors) {
        floorButtonPressed = new boolean[numberOfFloors];
    }

    public void pressButton(int floorNumber){
        if (floorNumber > floorButtonPressed.length || floorNumber < 0 )
            throw new IllegalArgumentException();
        floorButtonPressed[floorNumber + 1] = true;
    }

    public void removePress(int floorNumber){
        if (floorNumber > floorButtonPressed.length || floorNumber < 0 )
            throw new IllegalArgumentException();
        floorButtonPressed[floorNumber + 1] = false;
    }
}
