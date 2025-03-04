package tec.ic660.pagination.infraestructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS en todas las rutas
                .allowedOrigins("*") // Permite todas las fuentes. Puedes cambiar "*" por dominios específicos.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite los métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(false); // Si necesitas enviar cookies o credenciales de autenticación
    }
}