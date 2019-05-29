package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by ilyaP on 24.05.2019.
 */
@Service
public class CommandLine {
    @Autowired
    private Scanner scanner;
    @Value("#{new Integer('${floors}')}")
    private int numberOfFloors;
    @Autowired
    private Shaft shaft;

    public void start() {
        while (true)
            if (scanner.hasNextLine())
                handler(scanner.nextLine());
    }

    private void handler(String command) {
        switch (command) {
            case "помощь":
                System.out.println("команда \"вызвать\" позволит выбрать этаж с которого нужно вызвать лифт." +
                        "Можно указать несколько этажей через пробел");
                System.out.println("команда \"состояние\" показываети состояние лифта");
                break;
            case "вызвать":
                call();
                break;
            case "состояние":
                state();
                break;
            default:
                System.out.println("такой команды нет вызовите команду \"помощь\"");
        }

    }

    private void state() {
        System.out.println("состояние:");
        System.out.println("лифт находится на этаже " + shaft.getElevatorCar().getCurrentFloor());
        System.out.println("время потраченое на путь " + shaft.getElevatorCar().getSecondsSpentMoving() + " секунд");
        System.out.println("время потраченое на старт " + shaft.getElevatorCar().getSecondsSpentStarting() + " секунд");
        System.out.println("время потраченое на остановку " + shaft.getElevatorCar().getSecondsSpentStopping() + " секунд");
    }

    private void call() {
        System.out.println("всего этажей " + numberOfFloors);
        System.out.println("этаж(ы): ");
        while (!scanner.hasNextLine()) ;
        String floors = scanner.nextLine();
        if (floors.matches("[0-9\\s-]+")) {
            int[] floorsNumbers = parser(floors);
            for (int floorNumber : floorsNumbers) {
                if (floorNumber < 1 || floorNumber > numberOfFloors) {
                    System.out.println("этажа " + floorNumber + " не существует \nкоманда прервана");
                    return;
                }
            }
            shaft.doCalls(floorsNumbers);
            System.out.println("готово");
        } else
            System.out.println("не корректный ввод");

    }

    public int[] parser(String floors) {
        return Arrays.stream(floors.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
