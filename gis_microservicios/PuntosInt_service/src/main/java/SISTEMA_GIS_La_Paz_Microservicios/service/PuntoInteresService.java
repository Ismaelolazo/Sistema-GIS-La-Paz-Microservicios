package SISTEMA_GIS_La_Paz_Microservicios.service;

import SISTEMA_GIS_La_Paz_Microservicios.dto.PuntoInteresDTO;
import SISTEMA_GIS_La_Paz_Microservicios.entity.PuntoInteres;
import SISTEMA_GIS_La_Paz_Microservicios.repository.PuntoInteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuntoInteresService {

    @Autowired
    private PuntoInteresRepository puntoInteresRepository;

    public List<PuntoInteresDTO> listarPuntosInteres() {
        return puntoInteresRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PuntoInteresDTO> buscarPorId(int id) {
        return puntoInteresRepository.findById(id).map(this::convertToDTO);
    }

    public Optional<PuntoInteresDTO> buscarPorNombre(String nombre) {
        return puntoInteresRepository.findAll().stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .map(this::convertToDTO);
    }

    public PuntoInteresDTO agregarPuntoInteres(PuntoInteresDTO nuevoPunto) {
        PuntoInteres punto = convertToEntity(nuevoPunto);
        // No establecer manualmente el ID, dejar que la base de datos lo genere
        return convertToDTO(puntoInteresRepository.save(punto));
    }

    public Optional<PuntoInteresDTO> actualizarPuntoInteres(int id, PuntoInteresDTO datosActualizados) {
        return puntoInteresRepository.findById(id).map(punto -> {
            punto.setNombre(datosActualizados.getNombre());
            punto.setDescripcion(datosActualizados.getDescripcion());
            punto.setLatitud(datosActualizados.getLatitud());
            punto.setLongitud(datosActualizados.getLongitud());
            punto.setCategoria(datosActualizados.getCategoria());
            return convertToDTO(puntoInteresRepository.save(punto));
        });
    }

    public boolean eliminarPuntoInteres(int id) {
        if (puntoInteresRepository.existsById(id)) {
            puntoInteresRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PuntoInteresDTO convertToDTO(PuntoInteres punto) {
        return new PuntoInteresDTO(
                punto.getId(),
                punto.getNombre(),
                punto.getDescripcion(),
                punto.getLatitud(),
                punto.getLongitud(),
                punto.getCategoria()
        );
    }

    private PuntoInteres convertToEntity(PuntoInteresDTO dto) {
        PuntoInteres punto = new PuntoInteres();
        punto.setId(dto.getId());
        punto.setNombre(dto.getNombre());
        punto.setDescripcion(dto.getDescripcion());
        punto.setLatitud(dto.getLatitud());
        punto.setLongitud(dto.getLongitud());
        punto.setCategoria(dto.getCategoria());
        return punto;
    }
}
