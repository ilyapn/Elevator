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
    @Autowired
    public ElevatorCar(ConsoleCar consoleCar) {
        this.consoleCar = consoleCar;
    }

    public void setShaft(Shaft shaft) {
        this.shaft = shaft;
    }
}
