package com.app.dao;

import com.app.model.Departamento;
import java.util.List;

public interface DepartamentoDAO {
    List<Departamento> listar() throws Exception;
}