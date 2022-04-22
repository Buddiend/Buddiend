package com.buddiend.buddiend;

//import net.bytebuddy.utility.RandomString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class BuddiendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BuddiendApplication.class, args);
    }
}
