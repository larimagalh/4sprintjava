package br.com.fiap.app;

import java.time.LocalDate;
import java.time.LocalTime;

import model.*;

public class SistemaTelemedicina {

    // Objetos únicos (modelo simplificado para simulação)
    private Paciente paciente;
    private Agendamento agendamento;
    private Consulta consulta;
    private ProblemaRelatado problema;
    private ChatbotInteracao interacao;

    // Cadastra o paciente no sistema
    public void cadastrarPaciente(Paciente paciente) {
        this.paciente = paciente;
        System.out.println("Paciente cadastrado: " + paciente.getNome());
    }

    // Realiza o agendamento
    public void realizarAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        System.out.println("Agendamento realizado para " + agendamento.getDataAgendada());
    }

    // Inicia a consulta
    public void iniciarConsulta() {
        this.consulta = new Consulta(LocalDate.now(), LocalTime.now(), "EM ANDAMENTO", paciente, agendamento);
        System.out.println("Consulta iniciada com o paciente: " + paciente.getNome());
    }

    // Registra um problema durante a consulta
    public void registrarProblema(String tipo, String descricao) {
        this.problema = new ProblemaRelatado(tipo, descricao, consulta);
        System.out.println("Problema registrado: " + tipo + " - " + descricao);
    }

    // Simula uma interação com o chatbot
    public void registrarInteracao(String etapa, boolean sucesso, boolean encaminhado) {
        this.interacao = new ChatbotInteracao(etapa, sucesso, encaminhado, consulta);
        System.out.println("Chatbot interagiu na etapa: " + etapa);
    }

    // Exibe o resumo
    public void exibirResumoConsulta() {
        System.out.println("--- Resumo da Consulta ---");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Data da Consulta: " + consulta.getData());
        System.out.println("Status: " + consulta.getStatus());
        System.out.println("Problema: " + (problema != null ? problema.getTipo() : "Nenhum"));
        System.out.println("Chatbot interagiu? " + (interacao != null ? "Sim" : "Não"));
    }
}


