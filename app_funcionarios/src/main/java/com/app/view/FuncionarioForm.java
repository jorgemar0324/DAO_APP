package com.app.view;

import com.app.controller.FuncionarioController;
import com.app.model.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FuncionarioForm extends JFrame {

    private JTextField txtNombre, txtApellido, txtEmail, txtSalario;
    private JComboBox<String> comboDepartamento;
    private JTable tabla;
    private DefaultTableModel modelo;

    private FuncionarioController controller = new FuncionarioController();

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

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // ===== EVENTOS =====
        btnGuardar.addActionListener(e -> guardar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());

        // ===== INICIALIZACIÓN =====
        cargarDepartamentos();
        listar();

        setVisible(true);
    }

    // ===== CARGAR DEPARTAMENTOS =====
    private void cargarDepartamentos() {
        comboDepartamento.addItem("1 - Sistemas");
        comboDepartamento.addItem("2 - Recursos Humanos");
        comboDepartamento.addItem("3 - Finanzas");
    }

    // ===== GUARDAR =====
    private void guardar() {
        try {
            Funcionario f = new Funcionario();
            f.setNombre(txtNombre.getText());
            f.setApellido(txtApellido.getText());
            f.setEmail(txtEmail.getText());
            f.setSalario(Double.parseDouble(txtSalario.getText()));

            String seleccionado = comboDepartamento.getSelectedItem().toString();
            int idDepartamento = Integer.parseInt(seleccionado.split(" - ")[0]);
            f.setDepartamentoId(idDepartamento);

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

            String seleccionado = comboDepartamento.getSelectedItem().toString();
            int idDepartamento = Integer.parseInt(seleccionado.split(" - ")[0]);
            f.setDepartamentoId(idDepartamento);

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
                String item = comboDepartamento.getItemAt(i);
                if (item.startsWith(deptId + " -")) {
                    comboDepartamento.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // ===== LIMPIAR CAMPOS =====
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtSalario.setText("");
        comboDepartamento.setSelectedIndex(0);
    }
}