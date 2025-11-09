package model;

public class ProblemaRelatado {
    private String tipo;
    private String descricao;
    private Consulta consulta;

    public ProblemaRelatado(String tipo, String descricao, Consulta consulta) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.consulta = consulta;
    }

    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public Consulta getConsulta() { return consulta; }

    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
}


