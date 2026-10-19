package agantes;

import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Agentes extends JFrame implements ActionListener {

    JMenuBar mB;
    JMenu mArch, mDB;
    JMenuItem mDB_C, mDB_R, mDB_U, mDB_D;

    private JLabel lbMain, lbId, lbAgent, lbPhone, lbStatus, lbAptm;
    private JTextField txtName, txtId, txtPhone, txtStatus, txtAptm;
    private JButton create, read, update, delate;
    private Persona p;

    public Agentes() {

        //hola profe voy a tomar notas pq me va a preguntar
        //creacion de la ventana
        this.setBounds(150, 50, 800, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        //textos fijos
        this.lbMain = new JLabel("Welcome to agents");
        this.lbMain.setBounds(300, 20, 250, 30);
        this.add(lbMain);

        this.lbAgent = new JLabel("Agent:");
        this.lbAgent.setBounds(200, 50, 100, 50);
        this.add(lbAgent);

        this.lbPhone = new JLabel("Phone:");
        this.lbPhone.setBounds(200, 80, 100, 50);
        this.add(lbPhone);

        this.lbStatus = new JLabel("Status:");
        this.lbStatus.setBounds(200, 110, 100, 50);
        this.add(lbStatus);

        this.lbAptm = new JLabel("Apartment:");
        this.lbAptm.setBounds(200, 140, 100, 50);
        this.add(lbAptm);
        
        this.lbId = new JLabel("ID: ");
        this.lbId.setBounds(200, 230, 100, 50);
        this.add(lbId);
        

        //recuadros para escribir (JTextField)
        this.txtName = new JTextField();
        this.txtName.setBounds(275, 60, 200, 30);
        this.add(txtName);

        this.txtPhone = new JTextField();
        this.txtPhone.setBounds(275, 90, 200, 30);
        this.add(txtPhone);

        this.txtStatus = new JTextField();
        this.txtStatus.setBounds(275, 120, 200, 30);
        this.add(txtStatus);

        this.txtAptm = new JTextField();
        this.txtAptm.setBounds(275, 150, 200, 30);
        this.add(txtAptm);
        
        this.txtId = new JTextField();
        this.txtId.setBounds(275, 240, 200, 30);
        this.add(txtId);

        //botones        
        this.create = new JButton("Create");
        this.create.setBounds(400, 190, 110, 30);
        this.create.addActionListener(this);
        this.add(this.create);

        this.read = new JButton("read");
        this.read.setBounds(285, 190, 110, 30);
        this.read.addActionListener(this);
        this.add(this.read);

        this.update = new JButton("update");
        this.update.setBounds(400, 280, 110, 30);
        this.update.addActionListener(this);
        this.add(this.update);
        
        this.delate = new JButton("Delate");
        this.delate.setBounds(285, 280,110,30);
        this.delate.addActionListener(this);
        this.add(this.delate);
        
        // menuBar
        this.mB = new JMenuBar();
        
        this.mArch = new JMenu("Archive");
        this.mB.add(mArch);
        
        this.mDB = new JMenu("Data Base");
        this.mB.add(mDB);
        
        
        
        this.mDB_C = new JMenuItem("Create");
        this.mDB_C.addActionListener(this);
        this.mDB.add(mDB_C);
        
        this.mDB_R = new JMenuItem("Read");
        this.mDB_R.addActionListener(this);
        this.mDB.add(mDB_R);
        
        this.mDB_U = new JMenuItem("Update");
        this.mDB_U.addActionListener(this);
        this.mDB.add(mDB_U);
        
        this.mDB_D = new JMenuItem("Delate");
        this.mDB_D.addActionListener(this);
        this.mDB.add(mDB_D);
        
        this.setJMenuBar(mB);

        

    }

    public static void main(String[] args) {
        Agentes ventana = new Agentes();
        ventana.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent Ana) {
        if (this.create == Ana.getSource() || this.mDB_C == Ana.getSource()) {
            p = new Persona();
            p.setNombre(this.txtName.getText());
            JOptionPane.showMessageDialog(this, "El nombre guardado es: " + this.p.getNombre());
            CreateDB();
        }
        if (this.read == Ana.getSource() || this.mDB_R == Ana.getSource()) {
            ReadDB();
        }
        if (this.update == Ana.getSource() || this.mDB_U == Ana.getSource()) {
            UpdateDB();
        }
        if(this.delate == Ana.getSource() || this.mDB_D == Ana.getSource()){
            DelateDB();
            
        }
    }

    public void CreateDB() {
        String sql = "insert into agentes(nombre, departamento, telefono, estado) VALUES (?,?,?,?)";
        try (Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencia", "root", "011507Js"); PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, txtName.getText());
            ps.setString(2, txtAptm.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtStatus.getText());

            ps.executeUpdate();
            System.out.println("succesful");
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getSQLState() + ": " + ex.getMessage());
        }
    }

    public void ReadDB() {
        String sql = "select * from agentes";
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencia", "root", "011507Js");
            PreparedStatement ps = cnx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("successful ");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + "|| Name: " + rs.getString("nombre")
                        + "|| Status: " + rs.getString("estado") + " || Department: "
                        + rs.getString("departamento") + " || Phone: " + rs.getString("telefono"));
            }

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getSQLState() + ": " + ex.getMessage());
        }

    }

    public void UpdateDB() {
        String sql = "UPDATE agentes SET nombre = ? WHERE id = ?";
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencia", "root", "011507Js");
            PreparedStatement ps = cnx.prepareStatement(sql);
            ps.setString(1, this.txtName.getText());
            ps.setString(2, this.txtId.getText());
            int affectedArrows = ps.executeUpdate();
            System.out.println("success update. Afected arrows: " + affectedArrows);
            System.out.println("successful ");

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    public void DelateDB() {
        String sql = "DELETE FROM agentes WHERE id = ?";

        try (Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/agencia", "root", "011507Js"); PreparedStatement ps = cnx.prepareStatement(sql)) {

            int idAEliminar = Integer.parseInt(this.txtId.getText());
            ps.setInt(1, idAEliminar);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el ID en la base de datos.");
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número de ID válido.");
        }
    }
}
