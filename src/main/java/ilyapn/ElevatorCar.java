package ilyapn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Component
public class ElevatorCar {
    @Value("#{new Integer('${speed}')}")
    private int carSpeedSeconds;
    @Value("#{new Integer('${start}')}")
    private int secondsToStart;
    @Value("#{new Integer('${stop}')}")
    private int secondsToStop;
    private Shaft shaft;
    private boolean toUp;
    private boolean toDown;
    private int currentFloor = 1;
    private int stopCounter = 0;
    private int speedCounter = 0;

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setShaft(Shaft shaft) {
        this.shaft = shaft;
    }

    public int getSecondsSpentStarting() {
        return stopCounter * secondsToStart;
    }

    public int getSecondsSpentStopping() {
        return stopCounter * secondsToStop;                    //counter of starts is equal stops
    }

    public int getSecondsSpentMoving() {
        return speedCounter * carSpeedSeconds;
    }

    public void riseToFloor() {
        speedCounter++;
        currentFloor++;
    }

    public void downToFloor() {
        speedCounter++;
        currentFloor--;
    }

    public synchronized void start() {
        while (shaft.hasCall()) {
            if (!toUp && !toDown) {                             // до этого не было движение
                if (shaft.hasCallOn(currentFloor))              //есть вызов на текущем этаже
                    shaft.cancelCall(currentFloor);             //сбросить вызов
                else if (shaft.hasCallsAbove(currentFloor)) {   //есть вызов сверху
                    toUp = true;                                //выставить движение вверх
                    continue;
                } else if (shaft.hasCallsBelow(currentFloor)) { //есть вызов снизу
                    toDown = true;                              //выставить движение вниз
                    continue;
                }
            } else if (toUp) {                                  //шел вверх
                if (shaft.hasCallsAbove(currentFloor)) {        //есть вызов сверху
                    riseToFloor();                              //этаж++
                    if (shaft.hasCallOn(currentFloor)) {        //на текущем этаже есть вызов
                        stopCounter++;
                        shaft.cancelCall(currentFloor);         //сбросить вызов
                        continue;
                    }

                } else {                                        // сбросить состояние движения
                    toUp = false;
                    toDown = false;
                }
            } else {                                            //не шел вверх
                if (shaft.hasCallsBelow(currentFloor)) {        //есть вызов снизу
                    downToFloor();                              //этаж--
                    if (shaft.hasCallOn(currentFloor)) {        //на текущем этаже есть вызов
                        stopCounter++;
                        shaft.cancelCall(currentFloor);         //сбросить вызов
                        continue;
                    }
                } else {                                        // сбросить состояние движения
                    toUp = false;
                    toDown = false;
                }
            }
        }
        toUp = false;
        toDown = false;
    }
}
