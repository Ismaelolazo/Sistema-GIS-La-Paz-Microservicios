package main.java.Sistema_GIS_La_Paz_Microservicios.repository;

import Sistema_GIS_La_Paz_Microservicios.model.TarifaMicro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaMicroRepository extends JpaRepository<TarifaMicro, Long> {
    
    // Buscar tarifa por horario, tramo y condición de usuario
    Optional<TarifaMicro> findByHorarioAndTramoAndCondicionUsuario(String horario, String tramo, String condicionUsuario);
    
    // Buscar tarifa por horario y tramo (para tarifa general)
    Optional<TarifaMicro> findByHorarioAndTramoAndTipoVehiculo(String horario, String tramo, String tipoVehiculo);
    
    // Listar todas las tarifas para micros/minibuses
    List<TarifaMicro> findByTipoVehiculo(String tipoVehiculo);
    
    // Método para compatibilidad con el código anterior
    default Optional<TarifaMicro> findByTipoTramoAndTipoHorario(String tipoTramo, String tipoHorario) {
        return findByHorarioAndTramoAndTipoVehiculo(tipoHorario, tipoTramo, "Minibús");
    }
}