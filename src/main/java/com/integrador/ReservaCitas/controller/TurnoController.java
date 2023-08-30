package com.integrador.ReservaCitas.controller;

import com.integrador.ReservaCitas.entity.Turno;
import com.integrador.ReservaCitas.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("turno")
public class TurnoController {
    private final IService<Turno> turnoService;
    private static final Logger logger = Logger.getLogger(TurnoController.class);

    @Autowired
    public TurnoController(IService<Turno> turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listar(){
        try{
            List<Turno> turnos = turnoService.buscarTodos();
            if(turnos.isEmpty()){
                logger.error("No se encontraron turnos");
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(turnos);
        }catch(Exception e){
            logger.error("Error al buscar todos los turnos", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/odontologo/{matricula}/paciente/{dni}/turno")
    public ResponseEntity<Turno> guardar(@PathVariable String matricula, @PathVariable String dni, @Validated @RequestBody Turno turno){
        try{
            Turno turnoGuardado = turnoService.guardar(turno);
            logger.info("Turno con id: " + turnoGuardado.getId() + " guardado correctamente");
            return ResponseEntity.ok(turnoGuardado);
        }catch(DataAccessException e){
            logger.error("Error al guardar el turno: " + e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarPorId(@PathVariable Integer id){
        try{
            String idT = String.valueOf(id);
            Turno turno = turnoService.buscar(idT);
            if(turno == null){
                logger.error("No se encontró el turno con id: " + id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(turno);
        }catch(DataAccessException e){
            logger.error("Error al buscar el turno con id: " + id, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Integer id, @Validated @RequestBody Turno turno){
        try{
            String idT = String.valueOf(id);
            Turno turnoEncontrado = turnoService.buscar(idT);
            if(turnoEncontrado == null){
                logger.error("No se encontró el turno con id: " + id);
                return ResponseEntity.notFound().build();
            }
            turnoEncontrado.setFecha(turno.getFecha());
            turnoEncontrado.setHora(turno.getHora());
            turnoEncontrado.setPaciente(turno.getPaciente());
            turnoEncontrado.setOdontologo(turno.getOdontologo());
            turnoService.guardar(turnoEncontrado);
            logger.info("Turno con id: " + id + " actualizado correctamente");
            return ResponseEntity.ok("Se ha actualizado el turno: " + turnoEncontrado);
        }catch(DataAccessException e){
            logger.error("Error al actualizar el turno con id: " + id, e);
            return ResponseEntity.badRequest().body("Error al actualizar el turno");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id){
        try{
            String idT = String.valueOf(id);
            Turno turnoEncontrado = turnoService.buscar(idT);
            if(turnoEncontrado == null){
                logger.error("No se encontró el turno con id: " + id);
                return ResponseEntity.notFound().build();
            }
            turnoService.eliminar(idT);
            logger.info("Turno con id: " + id + " eliminado correctamente");
            return ResponseEntity.ok("Se ha eliminado el turno con id: " + id);
        }catch(DataAccessException e){
            logger.error("Error al eliminar el turno con id: " + id, e);
            return ResponseEntity.badRequest().body("Error al eliminar el turno");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
