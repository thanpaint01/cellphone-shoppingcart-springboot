package nlu.fit.cellphoneapp;

import nlu.fit.cellphoneapp.repositories.ExtendedRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = "nlu.fit.cellphoneapp.specification")
public class CellphoneAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CellphoneAppApplication.class, args);
    }

}
