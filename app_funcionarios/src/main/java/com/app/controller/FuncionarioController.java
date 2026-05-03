package com.app.controller;

import com.app.dao.FuncionarioDAO;
import com.app.dao.FuncionarioDAOImpl;
import com.app.model.Funcionario;

import java.util.List;

public class FuncionarioController {

    private FuncionarioDAO dao = new FuncionarioDAOImpl();

    public void crear(Funcionario f) throws Exception {
        dao.crear(f);
    }

    public List<Funcionario> listar() throws Exception {
        return dao.listar();
    }

    public void actualizar(Funcionario f) throws Exception {
        dao.actualizar(f);
    }

    public void eliminar(int id) throws Exception {
        dao.eliminar(id);
    }
}