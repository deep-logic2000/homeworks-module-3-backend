package com.example.homework_module3.Homework03;

import com.example.homework_module3.Homework01.domain.Customer;
import com.example.homework_module3.Homework03.domain.dto.CustomerDtoRequest;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@SpringBootApplication
//@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setFieldMatchingEnabled(true)
				.setSkipNullEnabled(true)
				.setFieldAccessLevel(PRIVATE);

//		mapper.createTypeMap(CustomerDtoRequest.class, Customer.class)
//				.addMapping(CustomerDtoRequest::getName, Customer::setName)
//				.addMapping(CustomerDtoRequest::getEmail, Customer::setEmail)
//				.addMapping(CustomerDtoRequest::getAge, Customer::setAge)
//				.addMapping(CustomerDtoRequest::getPhoneNumber, Customer::setPhoneNumber)
//				.addMapping(CustomerDtoRequest::getPassword, Customer::setPassword);
//		mapper.createTypeMap(Employee.class, EmployeeDto.class)
//				.addMapping(Employee::getFirstName, EmployeeDto::setFirstName)
//				.addMapping(Employee::getLastName, EmployeeDto::setLastName);

		return mapper;
	}
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("EIS API")
						.description("Employee Information System sample application")
						.version("v0.0.1")
						.license(new License().name("Apache 2.0").url("http://springdoc.org"))
						.description("SpringShop Wiki Documentation")
						.contact(new Contact().email("test@test.com").url("http://fullstackcode.dev")))
				;
	}

}
