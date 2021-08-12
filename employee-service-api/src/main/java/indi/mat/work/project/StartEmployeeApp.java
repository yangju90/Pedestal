package indi.mat.work.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class StartEmployeeApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(StartEmployeeApp.class, args);
    }
}
