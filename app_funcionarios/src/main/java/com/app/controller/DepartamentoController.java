package com.app.controller;

import com.app.dao.DepartamentoDAO;
import com.app.dao.DepartamentoDAOImpl;
import com.app.model.Departamento;

import java.util.List;

public class DepartamentoController {

    private DepartamentoDAO dao = new DepartamentoDAOImpl();

    public List<Departamento> listar() throws Exception {
        return dao.listar();
    }
}