package service;

import model.Consulta;
import model.Paciente;
import model.StatusConsulta;

import java.time.LocalDateTime;
import java.util.UUID;

public class AgendamentoService {

    /**
     * Valida e cria uma Consulta (simplesmente popula o objeto).
     * Regras:
     *  - Não permite agendar em datas passadas.
     *  - Se paciente tem baixa afinidade digital, agenda em horário preferencial (ex: manhã)
     */
    public Consulta agendarConsulta(Paciente paciente, LocalDateTime dataHora, String motivo) {
        LocalDateTime agora = LocalDateTime.now();
        if (dataHora.isBefore(agora.plusMinutes(5))) {
            throw new IllegalArgumentException("Data/hora inválida: deve ser pelo menos 5 minutos no futuro.");
        }
        if (paciente == null) throw new IllegalArgumentException("Paciente obrigatório.");

        // regra: se baixa afinidade digital e horário não matutino (8-12), sugerir mover para 9:00
        if (paciente.isBaixaAfinidadeDigital()) {
            int hour = dataHora.getHour();
            if (hour < 8 || hour > 12) {
                dataHora = dataHora.withHour(9).withMinute(0).withSecond(0).withNano(0);
            }
        }

        Consulta c = new Consulta(paciente.getId(), dataHora, motivo);
        c.setStatus(StatusConsulta.AGENDADA);
        return c;
    }

    /**
     * Tenta iniciar a consulta usando as regras do agregado.
     */
    public String iniciarConsulta(Consulta consulta) {
        return consulta.iniciar(LocalDateTime.now());
    }

    /**
     * Reagendamento automático simples: empurra para +24h
     */
    public Consulta reagendarPorFalha(Consulta consulta) {
        consulta.setDataHora(consulta.getDataHora().plusDays(1));
        consulta.setStatus(StatusConsulta.AGENDADA);
        consulta.setTentativasConexao(0);
        consulta.setProblemaTecnicoReportado(false);
        return consulta;
    }

    /**
     * Simula envio de lembrete — para pacientes com baixa afinidade digital, faz 2 lembretes (SMS+ligação simulada).
     * Retorna texto com qual canal foi utilizado.
     */
    public String enviarLembrete(Paciente paciente, Consulta consulta) {
        if (paciente.isBaixaAfinidadeDigital()) {
            return "Enviado SMS e ligação automática para " + paciente.getTelefone();
        } else {
            return "Enviado email para " + paciente.getEmail();
        }
    }
}

