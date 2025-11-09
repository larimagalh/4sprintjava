package model;

import java.time.LocalDateTime;
import java.time.Duration;

public class Consulta {
    private Long id;
    private Long pacienteId;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String motivo;
    private boolean problemaTecnicoReportado;
    private int tentativasConexao; // contador simples

    public Consulta() {}

    public Consulta(Long id, Long pacienteId, LocalDateTime dataHora, StatusConsulta status, String motivo) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.dataHora = dataHora;
        this.status = status;
        this.motivo = motivo;
        this.problemaTecnicoReportado = false;
        this.tentativasConexao = 0;
    }

    public Consulta(Long pacienteId, LocalDateTime dataHora, String motivo) {
        this(null, pacienteId, dataHora, StatusConsulta.AGENDADA, motivo);
    }

    // Getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public boolean isProblemaTecnicoReportado() { return problemaTecnicoReportado; }
    public void setProblemaTecnicoReportado(boolean problemaTecnicoReportado) { this.problemaTecnicoReportado = problemaTecnicoReportado; }

    public int getTentativasConexao() { return tentativasConexao; }
    public void setTentativasConexao(int tentativasConexao) { this.tentativasConexao = tentativasConexao; }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", pacienteId=" + pacienteId +
                ", dataHora=" + dataHora +
                ", status=" + status +
                ", motivo='" + motivo + '\'' +
                ", problemaTecnicoReportado=" + problemaTecnicoReportado +
                ", tentativasConexao=" + tentativasConexao +
                '}';
    }

    // ======= Métodos de lógica de negócio (várias regras) =======

    /**
     * Verifica se a consulta está dentro da janela aceitável para iniciar (ex: +/- 15 minutos)
     */
    public boolean podeIniciarAgora(LocalDateTime agora) {
        Duration diff = Duration.between(agora, this.dataHora).abs();
        return diff.toMinutes() <= 15 && (this.status == StatusConsulta.AGENDADA || this.status == StatusConsulta.CONFIRMADA);
    }

    /**
     * Marca como iniciada se possível; retorna mensagem e altera status conforme regras:
     * - Só inicia se estiver na janela e não estiver cancelada.
     */
    public String iniciar(LocalDateTime agora) {
        if (this.status == StatusConsulta.CANCELADA) {
            return "Consulta cancelada — não pode iniciar.";
        }
        if (!podeIniciarAgora(agora)) {
            return "Fora da janela de início (±15 min) ou status inválido: " + this.status;
        }
        this.status = StatusConsulta.EM_ANDAMENTO;
        return "Consulta iniciada com sucesso.";
    }

    /**
     * Reporta um problema técnico. Se houver muitas tentativas (>=3), sugere reagendamento automático.
     */
    public String reportarProblemaTecnico(String descricao) {
        this.problemaTecnicoReportado = true;
        this.tentativasConexao++;
        if (this.tentativasConexao >= 3) {
            this.status = StatusConsulta.CANCELADA;
            return "Múltiplas falhas detectadas. Consulta cancelada e paciente será reagendado automaticamente.";
        }
        return "Problema técnico registrado: " + descricao + ". Tentativa " + this.tentativasConexao;
    }

    /**
     * Verifica risco de desistência com base em afinidade digital e hora da consulta:
     * retorna score 0..100 (quanto maior, maior risco)
     */
    public int avaliarRiscoDesistencia(Paciente paciente, LocalDateTime agora) {
        int score = 10;
        if (paciente.isBaixaAfinidadeDigital()) score += 50;
        Duration until = Duration.between(agora, this.dataHora);
        long minutes = until.toMinutes();
        if (minutes < 30 && minutes > -60) score += 20; // perto ou já atrasado
        if (this.problemaTecnicoReportado) score += 20;
        if (this.tentativasConexao >= 2) score += 10;
        return Math.min(score, 100);
    }
}
