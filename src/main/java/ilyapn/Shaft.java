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
        Arrays.stream(floors).forEach(n -> this.floors[n-1].doCall());
    }

    public boolean hasCall(){
        return Arrays.stream(floors).anyMatch(Floor::isCall);
    }

    public int[] getCalls(){
        return Arrays.stream(floors).filter(Floor::isCall).mapToInt(Floor::getNumber).toArray();
    }

    public boolean hasCallsAbove(int numberFloor){
        return Arrays.stream(getCalls()).anyMatch(n -> n > numberFloor);
    }

    public boolean hasCallsBelow(int numberFloor){
        return Arrays.stream(getCalls()).anyMatch(n -> n < numberFloor);
    }

    public boolean hasCallOn(int floorNumber){
        return floors[floorNumber - 1].isCall();
    }

    public void cancelCall(int floorNumber){
        floors[floorNumber - 1].cancelCall();
    }


}
