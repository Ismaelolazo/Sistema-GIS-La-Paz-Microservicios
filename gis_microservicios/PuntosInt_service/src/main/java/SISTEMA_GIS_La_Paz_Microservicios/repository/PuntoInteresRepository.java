package SISTEMA_GIS_La_Paz_Microservicios.repository;

import SISTEMA_GIS_La_Paz_Microservicios.entity.PuntoInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntoInteresRepository extends JpaRepository<PuntoInteres, Integer> {
}
