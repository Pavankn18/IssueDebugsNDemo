package com.example.mockmvctestspringboot3;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
@Configuration
public class MockMvcTestSpringboot3Application {

	@Autowired
	private MyModelRepo repo;

	public static void main(String[] args) {
		SpringApplication.run(MockMvcTestSpringboot3Application.class, args);
	}

	@PostConstruct
	void populateData(){
		repo.save(new MyModel(1, "Pavan"));
		repo.save(new MyModel(2, "Kumar"));
		repo.save(new MyModel(3, "Nagesh"));
	}
}

@EnableJpaRepositories
@Configuration
@Profile("test")
//@Import({HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
class RepositoryConfig {

	@Bean
	public DataSource dataSource(){
		var dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder.url("jdbc:h2:mem:test");
		dataSourceBuilder.username("SA");
		dataSourceBuilder.password("");
		return dataSourceBuilder.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds);
		em.setPackagesToScan("com.example");

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		em.setJpaProperties(properties);

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		return new JpaTransactionManager(emf);
	}
}

@Configuration
//@Profile("test")
class SecurityConfig {
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}
}


@RestController
@RequiredArgsConstructor
class MyController{

	private final MyServiceI service;
	@GetMapping("/models/all")
	List<MyModel> getModels() {
		return service.getModels();
	}

	@GetMapping("/models")
	List<MyModel> getModels(@RequestParam List<Integer> ids) {
		return service.getModelsByIds(ids);
	}
}

interface MyServiceI{
	List<MyModel> getModels();

	List<MyModel> getModelsByIds(List<Integer> ids);
}

@Service
@RequiredArgsConstructor
class MyService implements MyServiceI{
	private final MyModelRepo repo;
	public List<MyModel> getModels() {
		return repo.findAll();
	}

	public List<MyModel> getModelsByIds(List<Integer> ids){
		return repo.findByIdIn(ids);
	}
}

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
class MyModel{
	@Id
	Integer id;

	String name;
}

interface MyModelRepo extends JpaRepository<MyModel, Integer>{
	List<MyModel> findByIdIn(List<Integer> ids);
}