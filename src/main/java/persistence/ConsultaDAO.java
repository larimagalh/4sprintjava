package persistence;

import model.Consulta;
import model.StatusConsulta;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public Consulta create(Consulta c) throws SQLException {
        String sql = "INSERT INTO consulta (paciente_id, data_hora, status, motivo, problema_tecnico, tentativas) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, c.getPacienteId());
            ps.setTimestamp(2, Timestamp.valueOf(c.getDataHora()));
            ps.setString(3, c.getStatus().name());
            ps.setString(4, c.getMotivo());
            ps.setBoolean(5, c.isProblemaTecnicoReportado());
            ps.setInt(6, c.getTentativasConexao());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.setId(rs.getLong(1));
                }
            }
        }
        return c;
    }

    public Consulta findById(Long id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    public List<Consulta> findAll() throws SQLException {
        List<Consulta> list = new ArrayList<>();
        String sql = "SELECT * FROM consulta";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public boolean update(Consulta c) throws SQLException {
        String sql = "UPDATE consulta SET paciente_id = ?, data_hora = ?, status = ?, motivo = ?, problema_tecnico = ?, tentativas = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, c.getPacienteId());
            ps.setTimestamp(2, Timestamp.valueOf(c.getDataHora()));
            ps.setString(3, c.getStatus().name());
            ps.setString(4, c.getMotivo());
            ps.setBoolean(5, c.isProblemaTecnicoReportado());
            ps.setInt(6, c.getTentativasConexao());
            ps.setLong(7, c.getId());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean delete(Long id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private Consulta map(ResultSet rs) throws SQLException {
        Consulta c = new Consulta();
        c.setId(rs.getLong("id"));
        c.setPacienteId(rs.getLong("paciente_id"));
        Timestamp ts = rs.getTimestamp("data_hora");
        if (ts != null) c.setDataHora(ts.toLocalDateTime());
        c.setStatus(StatusConsulta.valueOf(rs.getString("status")));
        c.setMotivo(rs.getString("motivo"));
        c.setProblemaTecnicoReportado(rs.getBoolean("problema_tecnico"));
        c.setTentativasConexao(rs.getInt("tentativas"));
        return c;
    }
}
