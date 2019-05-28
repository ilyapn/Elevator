package ilyapn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ElevatorApplication implements CommandLineRunner {
    @Autowired
    private App app;

    public static void main(String[] args) {
        SpringApplication.run(ElevatorApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        app.app();
    }

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }


}
