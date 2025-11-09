package br.com.fiap;


import model.*;


import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        // Criando o sistema
        SistemaTelemedicina sistema = new SistemaTelemedicina();

        // Criando um paciente
        Paciente paciente = new Paciente(1, "João da Silva", "11999999999", "BAIXA");
        sistema.cadastrarPaciente(paciente);

        // Realizando um agendamento
        Agendamento agendamento = new Agendamento(
                LocalDate.of(2025, 5, 20),
                LocalTime.of(14, 30),
                "Aplicativo",
                paciente
        );
        sistema.realizarAgendamento(agendamento);

        // Iniciando a consulta
        sistema.iniciarConsulta();

        // Registrando uma interação com o chatbot
        sistema.registrarInteracao("Acesso à Sala", true, false);

        // Registrando um problema durante a consulta
        sistema.registrarProblema("CONEXÃO", "Paciente teve dificuldade de áudio");

        // Exibindo o resumo da consulta
        sistema.exibirResumoConsulta();
    }
}