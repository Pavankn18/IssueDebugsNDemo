package springdoc.swagger.swaggerdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private Integer id;
	private String name;
}
