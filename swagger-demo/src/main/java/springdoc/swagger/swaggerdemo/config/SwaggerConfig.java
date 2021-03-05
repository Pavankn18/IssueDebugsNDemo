package springdoc.swagger.swaggerdemo.config;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import springdoc.swagger.swaggerdemo.model.ApiError;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI configureOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("User api"))
				.components(new Components()
						.addResponses("500", new ApiResponse()
								.content(new Content()
										.addMediaType("*/*", new MediaType()
												.schema(new Schema<ApiError>().$ref(ApiError.class.getSimpleName()))))));
	}
	
	@Bean
	public OpenApiCustomiser openApiCustomiser() {
		return openApi -> {
			openApi.getPaths().forEach((path, pathItem) -> pathItem.readOperations()
					.forEach(o -> o.getResponses()
							.addApiResponse("500", new ApiResponse().$ref("500"))
							.addApiResponse("default", new ApiResponse().$ref("500"))));
			
			openApi.schema(ApiError.class.getSimpleName(), 
					ModelConverters.getInstance()
					 .read(ApiError.class)
					 .get(ApiError.class.getSimpleName()));
		};
	}
}
