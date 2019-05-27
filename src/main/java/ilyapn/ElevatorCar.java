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
    int speed;
    @Value("#{new Integer('${start}')}")
    int start;
    @Value("#{new Integer('${stop}')}")
    int stop;
    private Shaft shaft;
    private ConsoleCar consoleCar;
    private boolean shouldGoToUp;//renamed
    private boolean shouldGoToDown;
    private boolean carStopped;
    private int floorWhereStopped;

    @Autowired
    public ElevatorCar(ConsoleCar consoleCar) {
        floorWhereStopped = 1;
        this.consoleCar = consoleCar;
    }

    public boolean isShouldGoToUp() {
        return shouldGoToUp;
    }

    public boolean isShouldGoToDown() {
        return shouldGoToDown;
    }

    public boolean isCarStopped() {
        return carStopped;
    }

    public int getFloorWhereStopped() {
        return floorWhereStopped;
    }

    public void setShaft(Shaft shaft) {
        this.shaft = shaft;
    }

    public void riseToFloor() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * speed) ;
        floorWhereStopped++;

    }

    public void downToFloor() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * speed) ;
        floorWhereStopped--;
    }

    public void stopCar() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * stop) ;
        shaft.cancelCall(floorWhereStopped);

    }

    public void startCar() {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + 1000 * start) ;

    }

    public void start() {
        while (true) {
            if (shaft.hasCall()) {// есть вызов
                if (!shouldGoToUp && !shouldGoToDown) {// до этого не было движение
                    if (shaft.hasCallOn(floorWhereStopped))//есть вызов на текущем этаже
                        shaft.cancelCall(floorWhereStopped);//сбросить вызов
                    else if (shaft.hasCallsAbove(floorWhereStopped)) {//есть вызов сверху
                        startCar();//старт
                        shouldGoToUp = true;//выставить движение вверх
                        continue;
                    } else if (shaft.hasCallsBelow(floorWhereStopped)) {//есть вызов снизу
                        startCar();// старт
                        shouldGoToDown = true;//выставить движение вниз
                        continue;
                    }
                } else if (shouldGoToUp) {                      //шел вверх
                    if(shaft.hasCallsAbove(floorWhereStopped)){//есть вызов сверху
                        riseToFloor();                          //этаж++
                        if(shaft.hasCallOn(floorWhereStopped)){//на текущем этаже есть вызов
                            stopCar();                          //остановить кабину
                            shaft.cancelCall(floorWhereStopped);//сбросить вызов
                            continue;
                        }

                    }else{                                      // сбросить состояние движения
                        shouldGoToUp = false;
                        shouldGoToDown = false;
                    }
                }else {                                          //не шел вверх
                    if (shaft.hasCallsBelow(floorWhereStopped)){ //есть вызовснизу
                        downToFloor();                           //этаж--
                        if(shaft.hasCallOn(floorWhereStopped)){  //на текущем этаже есть вызов
                            stopCar();                           //остановить кабину
                            shaft.cancelCall(floorWhereStopped); //сбросить вызов
                            continue;
                        }
                    }else {                                      // сбросить состояние движения
                        shouldGoToUp = false;
                        shouldGoToDown = false;
                    }
                }
            }else{
                shouldGoToUp = false;
                shouldGoToDown = false;
            }
        }
    }
}
