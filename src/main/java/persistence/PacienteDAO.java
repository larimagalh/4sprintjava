package persistence;

import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public Paciente create(Paciente p) throws SQLException {
        String sql = "INSERT INTO paciente (nome, telefone, email, baixa_afinidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getTelefone());
            ps.setString(3, p.getEmail());
            ps.setBoolean(4, p.isBaixaAfinidadeDigital());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getLong(1));
                }
            }
        }
        return p;
    }

    public Paciente findById(Long id) throws SQLException {
        String sql = "SELECT id, nome, telefone, email, baixa_afinidade FROM paciente WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Paciente> findAll() throws SQLException {
        List<Paciente> list = new ArrayList<>();
        String sql = "SELECT id, nome, telefone, email, baixa_afinidade FROM paciente";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public boolean update(Paciente p) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, telefone = ?, email = ?, baixa_afinidade = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getTelefone());
            ps.setString(3, p.getEmail());
            ps.setBoolean(4, p.isBaixaAfinidadeDigital());
            ps.setLong(5, p.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Paciente map(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setTelefone(rs.getString("telefone"));
        p.setEmail(rs.getString("email"));
        p.setBaixaAfinidadeDigital(rs.getBoolean("baixa_afinidade"));
        return p;
    }
}

