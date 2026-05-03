package com.app.dao;

import com.app.model.Funcionario;
import java.util.List;

public interface FuncionarioDAO {

    void crear(Funcionario funcionario) throws Exception;

    List<Funcionario> listar() throws Exception;

    void actualizar(Funcionario funcionario) throws Exception;

    void eliminar(int id) throws Exception;
}