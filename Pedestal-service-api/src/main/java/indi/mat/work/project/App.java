package indi.mat.work.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    // ClassLoader https://blog.csdn.net/briblue/article/details/54973413
    
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
