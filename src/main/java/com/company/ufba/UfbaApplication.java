package com.company.ufba;




import com.company.ufba.utils.tools.Delete;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
//@ComponentScan(basePackages = {"com.company.ufba.*"})
@EnableTransactionManagement
public class UfbaApplication {
	public static void main(String[] args)  {
		SpringApplication.run(UfbaApplication.class, args);
		new Delete().start();
	}
}
