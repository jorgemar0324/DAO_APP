package com.app.dao;

import com.app.connection.ConexionDB;
import com.app.model.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAOImpl implements DepartamentoDAO {

    @Override
    public List<Departamento> listar() throws Exception {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM departamento";

        try (Connection con = ConexionDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Departamento d = new Departamento();
                d.setId(rs.getInt("id"));
                d.setNombre(rs.getString("nombre"));
                lista.add(d);
            }

        } catch (SQLException e) {
            throw new Exception("Error al listar departamentos", e);
        }

        return lista;
    }
}