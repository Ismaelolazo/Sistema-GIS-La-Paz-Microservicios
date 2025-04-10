package main.java.Sistema_GIS_La_Paz_Microservicios.repository;

import Sistema_GIS_La_Paz_Microservicios.model.HistorialTarifaMicro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface HistorialTarifaMicroRepository extends JpaRepository<HistorialTarifaMicro, Long> {
    List<HistorialTarifaMicro> findByTipoTramoAndTipoHorarioOrderByFechaInicioDesc(String tipoTramo, String tipoHorario);
    List<HistorialTarifaMicro> findByFechaInicioBeforeAndFechaFinAfterOrFechaFinIsNull(LocalDateTime fecha, LocalDateTime mismaFecha);
}