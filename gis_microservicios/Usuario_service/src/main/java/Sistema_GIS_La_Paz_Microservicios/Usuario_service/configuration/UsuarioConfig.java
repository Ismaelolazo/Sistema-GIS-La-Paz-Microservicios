package Sistema_GIS_La_Paz_Microservicios.Usuario_service.configuration;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioService UsuarioBean() {
        return new UsuarioService();
    }

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

}