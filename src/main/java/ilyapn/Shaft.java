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
    private Floor[] floors;
    private ElevatorCar elevatorCar;

    @Autowired
    public Shaft(@Value("#{new Integer('${floors}')}") int numberOfFloors, ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
        elevatorCar.setShaft(this);
        floors = new Floor[numberOfFloors];
        IntStream.range(0, numberOfFloors).forEach(n -> floors[n] = new Floor(n + 1));
    }

    public void doCalls(int[] floorsNumbers) {
        for (int floorNumber : floorsNumbers) {
            if (floorNumber < 1 || floorNumber > floors.length)
                throw new IllegalArgumentException("gross number of floor");
        }
        Arrays.stream(floorsNumbers).forEach(n -> this.floors[n - 1].doCall());
    }

    public boolean hasCall() {
        return Arrays.stream(floors).anyMatch(Floor::isCall);
    }

    public int[] getCalls() {
        return Arrays.stream(floors).filter(Floor::isCall).mapToInt(Floor::getNumber).toArray();
    }

    public boolean hasCallsAbove(int numberFloor) {
        return Arrays.stream(getCalls()).anyMatch(n -> n > numberFloor);
    }

    public boolean hasCallsBelow(int numberFloor) {
        return Arrays.stream(getCalls()).anyMatch(n -> n < numberFloor);
    }

    public boolean hasCallOn(int numberFloor) {
        return floors[numberFloor - 1].isCall();
    }

    public void cancelCall(int numberFloor) {
        if (numberFloor < 1 || numberFloor > floors.length + 1)
            throw new IllegalArgumentException("gross number of floor");
        floors[numberFloor - 1].cancelCall();
    }


    public ElevatorCar getElevatorCar() {
        return elevatorCar;
    }
}
