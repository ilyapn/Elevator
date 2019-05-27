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
public class UserConsole{
    @Autowired
    private Scanner scanner;
    @Value("#{new Integer('${floors}')}")
    private int numberOfFloors;
    @Autowired
    private Shaft shaft;

    public void start(){
        while (true){
            System.out.println("1");
            if (scanner.hasNextLine())
                handler(scanner.nextLine());
        }
    }

    private void handler(String command){
        switch (command){
            case "помощь":
                System.out.println("команда \"вызвать\" позволит выбрать этаж с которого нужно вызвать лифт." +
                        "Можно указать несколько этажей через пробел");
/*                System.out.println("команда \"поехать\" позволит выбрать на какой этаж поехать." +
                        "Можно указать несколько этажей через пробел");*/
                System.out.println("команда \"состояние\" показываети состояние лифта");
                break;
            case "вызвать" :
                call();
                break;
/*            case "поехать" :
                go();
                break;*/
            case "состояние" :
                state();
                break;
            default:
                System.out.println("такой команды нет вызовите команду \"помощь\"");
        }

    }

    private void state() {
        System.out.println("состояние:");
        if (!shaft.elevatorCar.isShouldGoToDown() && !shaft.elevatorCar.isShouldGoToDown())
            System.out.println("лифт стоит на " + shaft.elevatorCar.getFloorWhereStopped() + " этаже");
        if (shaft.elevatorCar.isShouldGoToDown())
            System.out.println("лифт движется с " + shaft.elevatorCar.getFloorWhereStopped()+
            " на " + (shaft.elevatorCar.getFloorWhereStopped() - 1) + " этаж");
        if (shaft.elevatorCar.isShouldGoToUp())
            System.out.println("лифт движется с " + shaft.elevatorCar.getFloorWhereStopped()+
                    " на " + (shaft.elevatorCar.getFloorWhereStopped() + 1) + " этаж");
        if (!shaft.hasCall())
            System.out.println("лифт нигде не вызван");
        else {
            System.out.print("лифт вызван на этажах ");
            for (int numberFloor : shaft.getCalls())
                System.out.print(numberFloor + " ");
        }
    }

/*    private void go() {
        System.out.println("всего этажей "+ numberOfFloors);
        System.out.print("этаж(ы): ");
        while (scanner.hasNextLine());
        parser(scanner.nextLine());
    }*/

    private void call(){
        System.out.println("всего этажей "+ numberOfFloors);
        System.out.println("этаж(ы): ");
        while (!scanner.hasNextLine()){}
        int[] i = parser(scanner.nextLine());
        shaft.doCalls(i);


    }

    public int[] parser(String floors){
        return Arrays.stream(floors.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
