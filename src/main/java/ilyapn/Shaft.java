package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Component
public class Shaft {
    Floor[] floors;
    ElevatorCar elevatorCar;
    @Autowired
    public Shaft(@Value("#{new Integer('${floors}')}") int numberOfFloors, ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
        elevatorCar.setShaft(this);
        floors = new Floor[numberOfFloors];
        IntStream.range(0, numberOfFloors).forEach(n -> floors[n] = new Floor(n+1));
    }

    void doCalls(int[] floors){
        Arrays.stream(floors).forEach(n -> this.floors[n].doCall());
    }




}
