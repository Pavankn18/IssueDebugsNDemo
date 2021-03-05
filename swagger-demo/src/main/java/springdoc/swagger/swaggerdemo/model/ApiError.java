package springdoc.swagger.swaggerdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	String errorMessage;
	Integer status;
}
