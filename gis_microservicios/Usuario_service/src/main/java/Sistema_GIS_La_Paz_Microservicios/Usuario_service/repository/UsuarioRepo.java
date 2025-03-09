package Sistema_GIS_La_Paz_Microservicios.Usuario_service.repository;
import Sistema_GIS_La_Paz_Microservicios.Usuario_service.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

}