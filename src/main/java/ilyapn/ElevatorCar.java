package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ilyaP on 23.05.2019.
 */
@Component
public class ElevatorCar {
    @Value("#{new Integer('${speed}')}")
    private int speed;
    @Value("#{new Integer('${start}')}")
    private int start;
    @Value("#{new Integer('${stop}')}")
    private int stop;
    private Shaft shaft;
    private boolean toUp;
    private boolean toDown;
    private volatile int currentFloor = 1;

    @Autowired
    public ElevatorCar() {}

    public boolean isToUp() {
        return toUp;
    }

    public boolean isToDown() {
        return toDown;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setShaft(Shaft shaft) {
        this.shaft = shaft;
    }

    public void riseToFloor() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * speed);
        currentFloor++;

    }

    public void downToFloor() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * speed);
        currentFloor--;
    }

    public void stopCar() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * stop);

    }

    public void startCar() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * start);

    }

    public synchronized void start() {
        while (true) {
            if (shaft.hasCall()) {// есть вызов
                if (!toUp && !toDown) {// до этого не было движение
                    if (shaft.hasCallOn(currentFloor))//есть вызов на текущем этаже
                        shaft.cancelCall(currentFloor);//сбросить вызов
                    else if (shaft.hasCallsAbove(currentFloor)) {//есть вызов сверху
                        startCar();//старт
                        toUp = true;//выставить движение вверх
                        continue;
                    } else if (shaft.hasCallsBelow(currentFloor)) {//есть вызов снизу
                        startCar();// старт
                        toDown = true;//выставить движение вниз
                        continue;
                    }
                } else if (toUp) {                      //шел вверх
                    if (shaft.hasCallsAbove(currentFloor)) {//есть вызов сверху
                        riseToFloor();                          //этаж++
                        if (shaft.hasCallOn(currentFloor)) {//на текущем этаже есть вызов
                            stopCar();                          //остановить кабину
                            shaft.cancelCall(currentFloor);//сбросить вызов
                            continue;
                        }

                    } else {                                      // сбросить состояние движения
                        toUp = false;
                        toDown = false;
                    }
                } else {                                          //не шел вверх
                    if (shaft.hasCallsBelow(currentFloor)) { //есть вызов снизу
                        downToFloor();                           //этаж--
                        if (shaft.hasCallOn(currentFloor)) {  //на текущем этаже есть вызов
                            stopCar();                           //остановить кабину
                            shaft.cancelCall(currentFloor); //сбросить вызов
                            continue;
                        }
                    } else {                                      // сбросить состояние движения
                        toUp = false;
                        toDown = false;
                    }
                }
            } else {
                toUp = false;
                toDown = false;
            }
        }
    }
}
