package com.example.mockmvctestspringboot3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
class MockMvcTestSpringboot3ApplicationTests {

	@Test
	void contextLoads() {
	}

}


@WebMvcTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import(RepositoryConfig.class)
class MyControllerTests{

	@Autowired
	MockMvc mvc;

	@MockBean
	MyServiceI myService;

	@Test
	void testController() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/models"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	//@Configuration
	static class SecurityConfig {

		//@Bean
		public SecurityFilterChain configure(HttpSecurity http) throws Exception {
			return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
					.build();
		}

		@Bean
		public MyModelRepo repo(){
			return Mockito.mock(MyModelRepo.class);
		}
	}
}

