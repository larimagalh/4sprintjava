package model;

public class ChatBotInteracao {

    private Long id;
    private String etapa; // exemplo: "confirmação", "reagendamento", "suporte"
    private boolean sucesso;
    private boolean encaminhadoSuporte;
    private Consulta consulta;

    public ChatBotInteracao() {}

    public ChatBotInteracao(Long id, String etapa, boolean sucesso, boolean encaminhadoSuporte, Consulta consulta) {
        this.id = id;
        setEtapa(etapa);
        this.sucesso = sucesso;
        this.encaminhadoSuporte = encaminhadoSuporte;
        setConsulta(consulta);
    }

    // === GETTERS e SETTERS com validação ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) {
        if (etapa == null || etapa.trim().isEmpty()) {
            throw new IllegalArgumentException("A etapa da interação é obrigatória.");
        }
        this.etapa = etapa;
    }

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

    public boolean isEncaminhadoSuporte() { return encaminhadoSuporte; }
    public void setEncaminhadoSuporte(boolean encaminhadoSuporte) { this.encaminhadoSuporte = encaminhadoSuporte; }

    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("A interação deve estar associada a uma consulta.");
        }
        this.consulta = consulta;
    }

    @Override
    public String toString() {
        return "ChatBotInteracao{" +
                "id=" + id +
                ", etapa='" + etapa + '\'' +
                ", sucesso=" + sucesso +
                ", encaminhadoSuporte=" + encaminhadoSuporte +
                ", consultaId=" + (consulta != null ? consulta.getId() : null) +
                '}';
    }
}

