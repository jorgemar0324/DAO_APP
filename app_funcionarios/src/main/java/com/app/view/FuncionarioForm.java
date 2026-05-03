package com.app.view;

import com.app.controller.FuncionarioController;
import com.app.controller.DepartamentoController;
import com.app.model.Funcionario;
import com.app.model.Departamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FuncionarioForm extends JFrame {

    private JTextField txtNombre, txtApellido, txtEmail, txtSalario;
    private JComboBox<Departamento> comboDepartamento;
    private JTable tabla;
    private DefaultTableModel modelo;

    private FuncionarioController controller = new FuncionarioController();
    private DepartamentoController deptController = new DepartamentoController();

    public FuncionarioForm() {
        setTitle("Gestión de Funcionarios");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== FORMULARIO =====
        JPanel panelForm = new JPanel(new GridLayout(6, 2));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelForm.add(txtApellido);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Salario:"));
        txtSalario = new JTextField();
        panelForm.add(txtSalario);

        panelForm.add(new JLabel("Departamento:"));
        comboDepartamento = new JComboBox<>();
        panelForm.add(comboDepartamento);

        add(panelForm, BorderLayout.NORTH);

        // ===== TABLA =====
        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Apellido", "Email", "Salario", "Dept"}, 0
        );

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ===== BOTONES =====
        JPanel panelBotones = new JPanel();

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(panelBotones, BorderLayout.SOUTH);

        // ===== EVENTOS =====
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        // ===== INICIALIZACIÓN =====
        cargarDepartamentos();
        listar();

        setVisible(true);
    }

    // ===== CARGAR DEPARTAMENTOS DESDE BD =====
    private void cargarDepartamentos() {
        try {
            comboDepartamento.removeAllItems();

            List<Departamento> lista = deptController.listar();
            for (Departamento d : lista) {
                comboDepartamento.addItem(d);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===== GUARDAR =====
    private void guardar() {
        try {
            Funcionario f = new Funcionario();
            f.setNombre(txtNombre.getText());
            f.setApellido(txtApellido.getText());
            f.setEmail(txtEmail.getText());
            f.setSalario(Double.parseDouble(txtSalario.getText()));

            Departamento d = (Departamento) comboDepartamento.getSelectedItem();
            f.setDepartamentoId(d.getId());

            controller.crear(f);
            listar();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Guardado correctamente ✅");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===== LISTAR =====
    private void listar() {
        try {
            modelo.setRowCount(0);

            List<Funcionario> lista = controller.listar();

            for (Funcionario f : lista) {
                modelo.addRow(new Object[]{
                        f.getId(),
                        f.getNombre(),
                        f.getApellido(),
                        f.getEmail(),
                        f.getSalario(),
                        f.getDepartamentoId()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===== ELIMINAR =====
    private void eliminar() {
        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila");
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);

            controller.eliminar(id);
            listar();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Eliminado correctamente ✅");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===== ACTUALIZAR =====
    private void actualizar() {
        try {
            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona una fila");
                return;
            }

            int id = (int) modelo.getValueAt(fila, 0);

            Funcionario f = new Funcionario();
            f.setId(id);
            f.setNombre(txtNombre.getText());
            f.setApellido(txtApellido.getText());
            f.setEmail(txtEmail.getText());
            f.setSalario(Double.parseDouble(txtSalario.getText()));

            Departamento d = (Departamento) comboDepartamento.getSelectedItem();
            f.setDepartamentoId(d.getId());

            controller.actualizar(f);
            listar();
            limpiarCampos();

            JOptionPane.showMessageDialog(this, "Actualizado correctamente ✅");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ===== CARGAR DATOS AL SELECCIONAR FILA =====
    private void cargarDatosSeleccionados() {
        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtApellido.setText(modelo.getValueAt(fila, 2).toString());
            txtEmail.setText(modelo.getValueAt(fila, 3).toString());
            txtSalario.setText(modelo.getValueAt(fila, 4).toString());

            int deptId = (int) modelo.getValueAt(fila, 5);

            for (int i = 0; i < comboDepartamento.getItemCount(); i++) {
                Departamento d = comboDepartamento.getItemAt(i);
                if (d.getId() == deptId) {
                    comboDepartamento.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // ===== LIMPIAR =====
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtSalario.setText("");
        if (comboDepartamento.getItemCount() > 0) {
            comboDepartamento.setSelectedIndex(0);
        }
    }
}