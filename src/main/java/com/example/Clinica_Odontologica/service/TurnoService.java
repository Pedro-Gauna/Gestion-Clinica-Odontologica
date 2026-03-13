package com.example.Clinica_Odontologica.service;

import com.example.Clinica_Odontologica.dto.*;
import com.example.Clinica_Odontologica.model.*;
import com.example.Clinica_Odontologica.repository.IOdontologoRepository;
import com.example.Clinica_Odontologica.repository.IPacienteRepository;
import com.example.Clinica_Odontologica.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService implements ITurnoService{

    @Autowired
    private ITurnoRepository turnoRepo;
    @Autowired
    private IPacienteRepository pacienteRepo;
    @Autowired
    private IOdontologoRepository odontoRepo;

    @Override
    public TurnoResponseDTO createTurno(Long id_odontologo, Long id_paciente, TurnoRequestDTO turnoDto){

        Turno turno = new Turno();

        //1. Validar los horarios del odontologo
        LocalTime hora = turnoDto.getHoraTurno();
        if(hora.isBefore(LocalTime.of(9,0)) || hora.isAfter(LocalTime.of(18,0))){
          throw new RuntimeException("El Odontólogo solo atiende de 9hs a 18hs.");
        }

        //2. Verificar que sea una fecha valida
        LocalDate fecha = turnoDto.getFechaTurno();
        if(fecha.isBefore(LocalDate.now())){
            throw new RuntimeException("La fecha es invalida.");
        }

        //3. Verificar si ya existe turno en esa fecha y hora para ese Odontologo

        turnoRepo.findByUnOdontologoIdAndFechaTurnoAndHoraTurno(id_odontologo, fecha,hora)
                .ifPresent(t ->
                {throw new RuntimeException("El Odontólogo ya tiene turnos para esa fecha y hora asignada."); });

        //4. Buscar Paciente y Odontologo

        Paciente paciente = pacienteRepo.findById(id_paciente)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Odontologo odontologo = odontoRepo.findById(id_odontologo)
                .orElseThrow(() -> new RuntimeException("Odontólogo no encontrado"));

        //5. Asignar relaciones
        turno.setHoraTurno(hora);
        turno.setFechaTurno(fecha);
        turno.setEstado("RESERVADO");
        turno.setUnPaciente(paciente);
        turno.setUnOdontologo(odontologo);

        Turno nuevoTurno = turnoRepo.save(turno);
        return responseTurno(nuevoTurno);
    }

    //Metodo ResponseDTO con Getters y Setters
    private TurnoResponseDTO responseTurno(Turno nuevoTurno) {
        TurnoResponseDTO turnoResp = new TurnoResponseDTO();
        turnoResp.setNombrePac(nuevoTurno.getUnPaciente().getNombre());
        turnoResp.setApellidoPac(nuevoTurno.getUnPaciente().getApellido());
        turnoResp.setTipo_tratamiento(nuevoTurno.getUnPaciente().getTipo_tratamiento());

        turnoResp.setHoraTurno(nuevoTurno.getHoraTurno());
        turnoResp.setFechaTurno(nuevoTurno.getFechaTurno());
        turnoResp.setEstadoTurno(nuevoTurno.getEstado());

        turnoResp.setNombreOdonto(nuevoTurno.getUnOdontologo().getNombre());
        return turnoResp;
    }

    @Override
    public List<TurnoResponseDTO>getTurnosActivos(){
        List<Turno> activos = turnoRepo.findByEstado("RESERVADO");
        List<TurnoResponseDTO> dto = new ArrayList<>();

        for(Turno t: activos){
            dto.add(mapToDTO(t));
        }

        return dto;
    }

    @Override
    public TurnoResponseDTO cancelTurno(Long id_turno, String motivo) {

        //1.Buscar el turno
        Turno turno = turnoRepo.findById(id_turno)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));

        //2. Validar reglas de función
        if("CANCELADO".equalsIgnoreCase(turno.getEstado())){
            throw new RuntimeException("Este turno ya fue cancelado.");
        }
        if("ATENDIDO".equalsIgnoreCase(turno.getEstado())){
            throw new RuntimeException("No se puede cancelar un turno que ya fue atendido.");
        }
        if(turno.getFechaTurno().isBefore(LocalDate.now())){
            throw new RuntimeException("No se pueden cancelar turnos pasados.");
        }

        turno.setEstado("CANCELADO");
        if(motivo != null && !motivo.isBlank()){
            turno.setMotivoCancelacion(motivo.trim());
        }

        Turno nuevoTurno = turnoRepo.save(turno);
        return mapToDTO(nuevoTurno);
    }

    //Metodo ResponseDTO con Constructores
    @Override
    public TurnoResponseDTO mapToDTO(Turno turno) {

        return new TurnoResponseDTO(
                turno.getUnPaciente().getNombre(),
                turno.getUnPaciente().getApellido(),
                turno.getUnPaciente().getTipo_tratamiento(),
                turno.getHoraTurno(),
                turno.getFechaTurno(),
                turno.getEstado(),
                turno.getUnOdontologo().getNombre()
        );
    }

    @Override
    public List<TurnoResponseDTO> listaTurnosFuturos(Long id_odontologo) {
        List<Turno> turnos = turnoRepo.findByUnOdontologoIdAndFechaTurnoGreaterThanEqual
                (id_odontologo, LocalDate.now());

        List<TurnoResponseDTO> turnosDTO = new ArrayList<>();
            for(Turno t: turnos){
                turnosDTO.add(mapToDTO(t));
            }
        return turnosDTO;
    }

    @Override
    public List<TurnoResponseDTO> listaTurnosPorDia(Long id_odontologo, LocalDate fecha) {
        List<Turno> turnos = turnoRepo.findByUnOdontologoIdAndFechaTurno(id_odontologo, fecha);

        List<TurnoResponseDTO> turnoDTO = new ArrayList<>();
        for(Turno t: turnos){
            turnoDTO.add(mapToDTO(t));
        }
        return turnoDTO;
    }

    @Override
    public List<TurnoResponseDTO> getTurnosOdontologo(Long id_odonto) {
        List<Turno> turnos = turnoRepo.findByUnOdontologoId(id_odonto);

        List<TurnoResponseDTO> OdontologoDTO = new ArrayList<>();

        for(Turno t: turnos){
            OdontologoDTO.add(mapToDTO(t));
        }

        return OdontologoDTO;
    }

    @Override
    public observacionTurnoDTO registrarObservacion(Long id_turno, RegistrarObservacionDTO reg) {
        Turno turno = turnoRepo.findById(id_turno)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado."));

        if("CANCELADO".equalsIgnoreCase(turno.getEstado())){
            throw new IllegalArgumentException("No se puede agregar una observación sobre un turno cancelado");
        }
        if("ATENDIDO".equalsIgnoreCase(turno.getEstado())){
            throw new IllegalArgumentException("El turno ya fue atendido. No se puede modificar la observación");
        }

        turno.setObservacion(reg.getObservacion());
        turno.setEstado("ATENDIDO");
        turnoRepo.save(turno);

        return new observacionTurnoDTO(
                turno.getFechaTurno(),
                turno.getHoraTurno(),
                turno.getUnPaciente().getNombre(),
                turno.getUnPaciente().getApellido(),
                turno.getObservacion()
        );
    }

    @Override
    public void eliminarTurno(Long id_turno) {
        Turno turno = turnoRepo.findById(id_turno)
                .orElseThrow(() -> new IllegalArgumentException("El turno no se ha encontrado"));
        Odontologo odonto = turno.getUnOdontologo();
        Paciente pac = turno.getUnPaciente();

        odonto.getListaTurnos().remove(turno);
        pac.getListaTurnos().remove(turno);

        odontoRepo.save(odonto);
    }

    @Override
    public CantPacientesPorDiaDTO cantPacientePorDia(LocalDate fecha) {

        int cantidad = turnoRepo.countByFechaTurnoAndEstado(fecha, "ATENDIDO");

        return new CantPacientesPorDiaDTO(fecha,cantidad);
    }

}
