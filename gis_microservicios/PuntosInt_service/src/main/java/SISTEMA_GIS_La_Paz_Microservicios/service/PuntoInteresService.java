package SISTEMA_GIS_La_Paz_Microservicios.service;

import SISTEMA_GIS_La_Paz_Microservicios.dto.PuntoInteresDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Servicio para la gestión de puntos de interés")
public class PuntoInteresService {


    private final List<PuntoInteresDTO> puntosInteres = new ArrayList<>();

    public PuntoInteresService() {
        puntosInteres.add(new PuntoInteresDTO(1, "Plaza Murillo", "Plaza histórica en La Paz", -16.5000, -68.1500, "Histórico"));
        puntosInteres.add(new PuntoInteresDTO(2, "Teleférico Rojo", "Estación central del teleférico", -16.495, -68.133, "Transporte"));
    }

    public List<PuntoInteresDTO> listarPuntosInteres() {
        return puntosInteres;
    }

    public Optional<PuntoInteresDTO> buscarPorId(int id) {
        return puntosInteres.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    public Optional<PuntoInteresDTO> buscarPorNombre(String nombre) {
        return puntosInteres.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    public List<PuntoInteresDTO> filtrarPorCategoria(String categoria) {
        return puntosInteres.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    public PuntoInteresDTO agregarPuntoInteres(PuntoInteresDTO nuevoPunto) {
        nuevoPunto.setId(puntosInteres.size() + 1);
        puntosInteres.add(nuevoPunto);
        return nuevoPunto;
    }

    public Optional<PuntoInteresDTO> actualizarPuntoInteres(int id, PuntoInteresDTO datosActualizados) {
        for (PuntoInteresDTO punto : puntosInteres) {
            if (punto.getId() == id) {
                punto.setNombre(datosActualizados.getNombre());
                punto.setDescripcion(datosActualizados.getDescripcion());
                punto.setLatitud(datosActualizados.getLatitud());
                punto.setLongitud(datosActualizados.getLongitud());
                punto.setCategoria(datosActualizados.getCategoria());
                return Optional.of(punto);
            }
        }
        return Optional.empty();
    }

    public boolean eliminarPuntoInteres(int id) {
        return puntosInteres.removeIf(p -> p.getId() == id);
    }
}
