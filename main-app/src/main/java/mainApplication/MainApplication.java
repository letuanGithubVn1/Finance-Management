package mainApplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "mainApplication",
	    "com.qlct.budgetmanagement",
	    "com.qlct.expenseManagement"
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
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
