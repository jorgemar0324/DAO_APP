package com.app.dao;

import com.app.connection.ConexionDB;
import com.app.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    @Override
    public void crear(Funcionario f) throws Exception {
        String sql = "INSERT INTO funcionario(nombre, apellido, email, salario, departamento_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNombre());
            ps.setString(2, f.getApellido());
            ps.setString(3, f.getEmail());
            ps.setDouble(4, f.getSalario());
            ps.setInt(5, f.getDepartamentoId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al crear funcionario", e);
        }
    }

    @Override
    public List<Funcionario> listar() throws Exception {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNombre(rs.getString("nombre"));
                f.setApellido(rs.getString("apellido"));
                f.setEmail(rs.getString("email"));
                f.setSalario(rs.getDouble("salario"));
                f.setDepartamentoId(rs.getInt("departamento_id"));

                lista.add(f);
            }

        } catch (SQLException e) {
            throw new Exception("Error al listar funcionarios", e);
        }

        return lista;
    }

    @Override
    public void actualizar(Funcionario f) throws Exception {
        String sql = "UPDATE funcionario SET nombre=?, apellido=?, email=?, salario=?, departamento_id=? WHERE id=?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, f.getNombre());
            ps.setString(2, f.getApellido());
            ps.setString(3, f.getEmail());
            ps.setDouble(4, f.getSalario());
            ps.setInt(5, f.getDepartamentoId());
            ps.setInt(6, f.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al actualizar funcionario", e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM funcionario WHERE id=?";

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error al eliminar funcionario", e);
        }
    }
}