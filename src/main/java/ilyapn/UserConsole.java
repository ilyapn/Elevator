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
public class UserConsole {
    @Autowired
    private Scanner scanner;
    @Value("#{new Integer('${floors}')}")
    private int numberOfFloors;
    @Autowired
    private Shaft shaft;
    @Autowired
    private ConsoleCar consoleCar;

    public void start(){
        while (true){
            if (scanner.hasNextLine())
                handler(scanner.nextLine());
        }
    }

    private void handler(String command){
        switch (command){
            case "помощь":
                System.out.println("команда \"вызвать\" позволит выбрать этаж с которого нужно вызвать лифт." +
                        "Можно указать несколько этажей через пробел");
                System.out.println("команда \"поехать\" позволит выбрать на какой этаж поехать." +
                        "Можно указать несколько этажей через пробел");
                System.out.println("команда \"состояние\" показываети состояние лифта");
                break;
            case "вызвать" :
                call();
                break;
            case "поехать" :
                go();
                break;
            case "состояние" :
                state();
                break;
            default:
                System.out.println("такой команды нет вызовите команду \"помощь\"");
        }

    }

    private void state() {

    }

    private void go() {
        System.out.println("всего этажей "+ numberOfFloors);
        System.out.print("этаж(ы): ");
        while (scanner.hasNextLine());
        parser(scanner.nextLine());
    }

    private void call(){
        System.out.println("всего этажей "+ numberOfFloors);
        System.out.print("этаж(ы): ");
        while (scanner.hasNextLine());
        parser(scanner.nextLine());


    }

    private int[] parser(String floors){
        return Arrays.stream(floors.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
}
