package model;

public class Paciente {

    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private boolean baixaAfinidadeDigital; // true = precisa de ajuda com tecnologia

    public Paciente() {}

    public Paciente(Long id, String nome, String telefone, String email, boolean baixaAfinidadeDigital) {
        this.id = id;
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        this.baixaAfinidadeDigital = baixaAfinidadeDigital;
    }

    public Paciente(String nome, String telefone, String email, boolean baixaAfinidadeDigital) {
        this(null, nome, telefone, email, baixaAfinidadeDigital);
    }

    // === GETTERS e SETTERS com validação ===

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do paciente é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do paciente é obrigatório.");
        }
        this.telefone = telefone.trim();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email.trim().toLowerCase();
    }

    public boolean isBaixaAfinidadeDigital() { return baixaAfinidadeDigital; }
    public void setBaixaAfinidadeDigital(boolean baixaAfinidadeDigital) {
        this.baixaAfinidadeDigital = baixaAfinidadeDigital;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", baixaAfinidadeDigital=" + baixaAfinidadeDigital +
                '}';
    }
}
