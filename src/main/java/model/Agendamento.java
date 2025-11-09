package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {

    private int id; // importante para CRUD (identificação do agendamento)
    private LocalDate dataAgendada;
    private LocalTime horaAgendada;
    private String canal; // exemplo: "presencial", "online", "telefone"
    private Paciente paciente;

    public Agendamento() {}

    public Agendamento(int id, LocalDate dataAgendada, LocalTime horaAgendada, String canal, Paciente paciente) {
        this.id = id;
        this.dataAgendada = dataAgendada;
        this.horaAgendada = horaAgendada;
        this.canal = canal;
        this.paciente = paciente;
    }

    // === GETTERS E SETTERS ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDataAgendada() { return dataAgendada; }
    public void setDataAgendada(LocalDate dataAgendada) {
        if (dataAgendada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data do agendamento não pode ser anterior à data atual.");
        }
        this.dataAgendada = dataAgendada;
    }

    public LocalTime getHoraAgendada() { return horaAgendada; }
    public void setHoraAgendada(LocalTime horaAgendada) {
        if (horaAgendada == null) {
            throw new IllegalArgumentException("O horário do agendamento é obrigatório.");
        }
        this.horaAgendada = horaAgendada;
    }

    public String getCanal() { return canal; }
    public void setCanal(String canal) {
        if (canal == null || canal.trim().isEmpty()) {
            throw new IllegalArgumentException("O canal do agendamento é obrigatório.");
        }
        this.canal = canal;
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("O paciente do agendamento é obrigatório.");
        }
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "id=" + id +
                ", dataAgendada=" + dataAgendada +
                ", horaAgendada=" + horaAgendada +
                ", canal='" + canal + '\'' +
                ", paciente=" + (paciente != null ? paciente.getNome() : "sem paciente") +
                '}';
    }
}


