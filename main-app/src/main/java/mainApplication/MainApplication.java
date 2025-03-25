package mainApplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "mainApplication",
	    "com.qlct.budgetmanagement",
	    "com.qlct.expenseManagement",
	    "com.qlct.core"
	})
@EnableJpaRepositories(basePackages = {
	    "com.qlct.budgetmanagement.repository",
	    "com.qlct.expenseManagement.repository",
	    "com.qlct.core.repository"
	})
@EntityScan(basePackages = {
	    "com.qlct.budgetmanagement.entity",
	    "com.qlct.expenseManagement.entity",
	    "com.qlct.core.entity"
	})
@PropertySource("classpath:application.properties")
@Component
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
