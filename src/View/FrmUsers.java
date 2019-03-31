    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

//import static clases.frmInventario.txtEmp;
import Controller.DatosExcelControl;
import static Controller.DatosExcelControl.importarTablaUsers;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ButtonGroup;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.User;
import Model.database;
import Model.login;
import Controller.conectar;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 *
 * @author Oscar Alvarez
 */
public class FrmUsers extends javax.swing.JFrame {
   
        //Atributos del fromato de fecha
        private  String formatIn = "dd/MM/yy";
	private  String formatOut = "yyyy/MM/dd";
        String idcg;

        //Metodo convertir String
	public  String convertToString ( String input , String in , String out ) {
		
		String aux = " ";
		try {
			aux = (new SimpleDateFormat(out)
				.format( (new SimpleDateFormat(in)).parse(input) ));
		
		}catch( Exception e ){
			// hubo un error
		}
		return aux;
	}
          //Metodo convertir String to date
          public Date convertToDate( String input , String in ){
		Date date = null;
		try{
			date = (new SimpleDateFormat(in)).parse(input);
		}catch(Exception e ) { }
		return date;
	}
          //Metodo convertir date to string
          public String convertToString( Date input , String out ) {
		String format = "";
		try {
			format = (new SimpleDateFormat(out)).format(input);
		} catch ( Exception e ) { }
		return format;
	}
    
    //Arreglo de lista pasa Combo box Cargos
    //List<Cargos> listCarg = new ArrayList<> ();
    
    //Constructor 
    public FrmUsers() throws SQLException {
        initComponents(); 
        this.setLocationRelativeTo(null);
        //cbCargo.addItem("Seleccione cargo");
        cbIdemp.addItem("Busqueda por ID");
        dameIdUser();
        //dameCargo();
        mostrar();
         
        /* try{
            Connection con = ConectionBD.getConnection();
            String query = "SELECT * FROM cargos";
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            
            while ( rs.next() ){
                listCarg.add(new Cargos(rs.getString(1), rs.getString(2), rs.getDouble(3)));
            }
            for( int i = 0 ; i < listCarg.size() ; i++ ){
                cbCargo.addItem(listCarg.get(i).getNombreCargo());
            }
            
            
            
            }catch(Exception e){}
         
        ButtonGroup group = new ButtonGroup();
        group.add(rHombre);
        group.add(rMujer);*/
        
    }
    void verificar(){
        if(jtNombre.getText().equals("")||jtApellidoP.getText().equals("")||jtApellidoM.getText().equals("")
                ||jtEmail.getText().equals("")||rHombre.isSelected()==false&&rMujer.isSelected()==false||jtUsuario.getText().equals("")
                ||fNac.getDate()==null){
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los datos");
        }
    }
    
    public void clean(){
        cbIdemp.setSelectedIndex(0);
        //cbCargo.setSelectedIndex(0);
        jtNombre.setText("");
        jtApellidoP.setText("");
        jtEmail.setText("");
        jtApellidoM.setText("");
        //jtDireccion.setText("");
        gpSexo.clearSelection();
        fNac.setCalendar(null);
        jtUsuario.setText("");
        jtPass.setText("");
        
    }
    
   /* void dameCargo() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select * from cargos";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            Cargos c = new Cargos();
            c.setNombreCargo(rs.getString("nom_cargo"));
            cbCargo.addItem(c.toString());
        }
    }*/
    
   /* void dameidCargo() throws SQLException{
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String carr = (String) cbCargo.getSelectedItem();
            String sql="SELECT cargo_id FROM cargos WHERE nom_cargo = '"+carr+"';";
            PreparedStatement sqls = conn.prepareStatement(sql);
            rs = sqls.executeQuery();
            while(rs.next()){
            Cargos c = new Cargos();
            c.setId(rs.getString("cargo_id"));
            idcg=c.getId();
            }
            } catch(Exception e){
                        String carr = (String) cbCargo.getSelectedItem();
                        System.out.println(carr);
                    }
    }*/
    
    
     void dameIdUser() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select * from users";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            User u = new User();
            u.setId(Integer.parseInt(rs.getString("id")));
            cbIdemp.addItem(u.toString());
        }
    }

    //Metodo mostrar datos en la tabla
    public void mostrar (){
        DefaultTableModel modelo = new DefaultTableModel();
        ResultSet rs = database.getTable("select id, nombre, paterno, materno,sexo,nacimiento,email, usuario, password from users");
        modelo.setColumnIdentifiers(new Object []{"ID","NOMBRE","APELLIDO P","APELLIDO M","SEXO","NACIMIENTO","EMAIL","USUARIO","CONTRASEÑA"});
        try{
            while (rs.next()){
                modelo.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre"), rs.getString("paterno"),
                    rs.getString("materno"),rs.getString("sexo"),rs.getDate("nacimiento"),rs.getString("email"),rs.getString("usuario"), rs.getString("password")});
                }
            tabEmpleados.setModel(modelo);
            }catch(Exception e){
            System.out.print(e);
            }
        }
    
    
    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        gpSexo = new javax.swing.ButtonGroup();
        jlUsuarios = new javax.swing.JLabel();
        jlMaterno = new javax.swing.JLabel();
        jlPaterno = new javax.swing.JLabel();
        jlSexo = new javax.swing.JLabel();
        jlEmail = new javax.swing.JLabel();
        btnEnviar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jlFnac = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        jtabEmpleado = new javax.swing.JScrollPane();
        tabEmpleados = new javax.swing.JTable();
        jlIDuser = new javax.swing.JLabel();
        btActualizar = new javax.swing.JButton();
        jtNombre = new javax.swing.JTextField();
        jtApellidoP = new javax.swing.JTextField();
        jtEmail = new javax.swing.JTextField();
        jtApellidoM = new javax.swing.JTextField();
        jlNombre = new javax.swing.JLabel();
        cbIdemp = new javax.swing.JComboBox<>();
        rHombre = new javax.swing.JRadioButton();
        rMujer = new javax.swing.JRadioButton();
        jtUsuario = new javax.swing.JTextField();
        jlUser = new javax.swing.JLabel();
        jtPass = new javax.swing.JPasswordField();
        jlPass = new javax.swing.JLabel();
        btnExportarXLS = new javax.swing.JButton();
        btnImportarExcel = new javax.swing.JButton();
        jlFondo = new javax.swing.JLabel();

        jInternalFrame1.setBackground(new java.awt.Color(0, 0, 255));
        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Empleados");
        getContentPane().setLayout(null);

        jlUsuarios.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jlUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        jlUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        jlUsuarios.setText("Usuarios");
        getContentPane().add(jlUsuarios);
        jlUsuarios.setBounds(150, 0, 439, 37);

        jlMaterno.setForeground(new java.awt.Color(255, 255, 255));
        jlMaterno.setText("Apellido Materno");
        getContentPane().add(jlMaterno);
        jlMaterno.setBounds(150, 160, 100, 20);

        jlPaterno.setForeground(new java.awt.Color(255, 255, 255));
        jlPaterno.setText("Apellido Paterno");
        getContentPane().add(jlPaterno);
        jlPaterno.setBounds(150, 130, 100, 20);

        jlSexo.setForeground(new java.awt.Color(255, 255, 255));
        jlSexo.setText("Sexo");
        getContentPane().add(jlSexo);
        jlSexo.setBounds(180, 190, 80, 30);

        jlEmail.setForeground(new java.awt.Color(255, 255, 255));
        jlEmail.setText("Email");
        getContentPane().add(jlEmail);
        jlEmail.setBounds(170, 220, 80, 20);

        btnEnviar.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/guardar.png"))); // NOI18N
        btnEnviar.setText("Guardar");
        btnEnviar.setBorder(null);
        btnEnviar.setDefaultCapable(false);
        btnEnviar.setOpaque(false);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEnviar);
        btnEnviar.setBounds(570, 350, 120, 40);

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/borrar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.setDefaultCapable(false);
        btnEliminar.setOpaque(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar);
        btnEliminar.setBounds(30, 350, 120, 40);

        jlFnac.setForeground(new java.awt.Color(255, 255, 255));
        jlFnac.setText("Fecha de Nacimiento");
        getContentPane().add(jlFnac);
        jlFnac.setBounds(130, 250, 130, 20);

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setDefaultCapable(false);
        btnLimpiar.setOpaque(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar);
        btnLimpiar.setBounds(580, 70, 120, 25);

        tabEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabEmpleadosMouseClicked(evt);
            }
        });
        jtabEmpleado.setViewportView(tabEmpleados);

        getContentPane().add(jtabEmpleado);
        jtabEmpleado.setBounds(20, 400, 700, 155);

        jlIDuser.setForeground(new java.awt.Color(255, 255, 255));
        jlIDuser.setText("ID Usuario");
        getContentPane().add(jlIDuser);
        jlIDuser.setBounds(170, 70, 80, 20);

        btActualizar.setBackground(new java.awt.Color(255, 255, 255));
        btActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modificar.png"))); // NOI18N
        btActualizar.setText("Actualizar");
        btActualizar.setBorder(null);
        btActualizar.setDefaultCapable(false);
        btActualizar.setOpaque(false);
        btActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(btActualizar);
        btActualizar.setBounds(290, 350, 120, 40);

        jtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNombreActionPerformed(evt);
            }
        });
        getContentPane().add(jtNombre);
        jtNombre.setBounds(270, 100, 200, 23);

        jtApellidoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtApellidoPActionPerformed(evt);
            }
        });
        getContentPane().add(jtApellidoP);
        jtApellidoP.setBounds(270, 130, 200, 23);

        jtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtEmailFocusLost(evt);
            }
        });
        jtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtEmailActionPerformed(evt);
            }
        });
        jtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtEmailKeyTyped(evt);
            }
        });
        getContentPane().add(jtEmail);
        jtEmail.setBounds(270, 220, 200, 23);
        getContentPane().add(jtApellidoM);
        jtApellidoM.setBounds(270, 160, 200, 23);

        jlNombre.setForeground(new java.awt.Color(255, 255, 255));
        jlNombre.setText("Nombre");
        getContentPane().add(jlNombre);
        jlNombre.setBounds(170, 100, 80, 20);

        cbIdemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIdempActionPerformed(evt);
            }
        });
        getContentPane().add(cbIdemp);
        cbIdemp.setBounds(270, 70, 200, 20);

        gpSexo.add(rHombre);
        rHombre.setText("Hombre");
        rHombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rHombreActionPerformed(evt);
            }
        });
        getContentPane().add(rHombre);
        rHombre.setBounds(270, 190, 100, 25);

        gpSexo.add(rMujer);
        rMujer.setText("Mujer");
        rMujer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rMujerActionPerformed(evt);
            }
        });
        getContentPane().add(rMujer);
        rMujer.setBounds(390, 190, 80, 25);
        getContentPane().add(jtUsuario);
        jtUsuario.setBounds(270, 280, 200, 22);

        jlUser.setForeground(new java.awt.Color(255, 255, 255));
        jlUser.setText("Usuario");
        getContentPane().add(jlUser);
        jlUser.setBounds(160, 280, 43, 16);
        getContentPane().add(jtPass);
        jtPass.setBounds(270, 310, 200, 22);

        jlPass.setForeground(new java.awt.Color(255, 255, 255));
        jlPass.setText("Password");
        getContentPane().add(jlPass);
        jlPass.setBounds(160, 310, 55, 16);

        btnExportarXLS.setText("Exportar XLS");
        btnExportarXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarXLSActionPerformed(evt);
            }
        });
        getContentPane().add(btnExportarXLS);
        btnExportarXLS.setBounds(580, 100, 120, 25);

        btnImportarExcel.setText("Importar Excel");
        btnImportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarExcelActionPerformed(evt);
            }
        });
        getContentPane().add(btnImportarExcel);
        btnImportarExcel.setBounds(580, 140, 120, 25);

        jlFondo.setBackground(java.awt.Color.white);
        jlFondo.setForeground(new java.awt.Color(255, 255, 255));
        jlFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_formulario3.jpg"))); // NOI18N
        getContentPane().add(jlFondo);
        jlFondo.setBounds(-20, 0, 790, 640);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (cbIdemp.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar algún id");
            
        }
        
        Connection con = null;
        Date date = fNac.getDate();
        long d = date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
        String idemp = (String) cbIdemp.getSelectedItem();
        
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
             ResultSet rs;
             String sql="DELETE FROM users WHERE id = ?";
            PreparedStatement sqls = conn.prepareStatement(sql);
            sqls.setString(1, idemp);
            int res = sqls.executeUpdate();
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Row Eliminado");
                clean();
                mostrar();
                //llenartabla(); 
            } else {
                 JOptionPane.showMessageDialog(null, "Error al eliminar");
                 clean();
                 mostrar();
                 //llenartabla(); 
            }
            
            con.close();
            
        } catch(Exception e){
            System.err.println(e);
        }
        
    }//GEN-LAST:event_btnEliminarActionPerformed
   /*  public void llenartabla() throws SQLException{
        conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String sql="SELECT id,nombre,paterno,materno,usuario  FROM users";
            PreparedStatement sqls = conn.prepareStatement(sql);
            rs = sqls.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            tabEmpleados.setModel(modelo);
            modelo.addColumn("ID");
            modelo.addColumn("NOMBRE");
            modelo.addColumn("APELLIDO P");
            modelo.addColumn("APELLIDO M");
            modelo.addColumn("USER");          
            while(rs.next()){
                Object [] fila = new Object[5]; 
                for (int i=0;i<5;i++)
                fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
                modelo.addRow(fila); 
            }
    }*/

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        clean();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
         verificar();
        Date date = fNac.getDate();
        long d = date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
                
        
        //Validacion de campos vacios
         String nombre=jtNombre.getText();
         String paterno=jtApellidoP.getText();
         String materno=jtApellidoM.getText();
         String sexo = null;
         String email=jtEmail.getText();
         String user=jtUsuario.getText();
         String pass=jtPass.getText();
         //String dir=jtDireccion.getText();
            if(rHombre.isSelected()){
                sexo="M";
            }
            else if(rMujer.isSelected()){
                sexo="F";
            }
       /* try {
            dameidCargo();
        } catch (SQLException ex) {
            Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            conectar cc=new conectar();
            Connection cn=cc.conexion();
           String sql="INSERT INTO users(nombre, paterno,"
                   + " materno, sexo, nacimiento,telefono, email, email_verified_at,  usuario, password,"
                   + "sepoID, remember_token) "
                   + "VALUES(?, ?,?, ?, ?, NULL, ?, NULL,?, ?,NULL, NULL);";
           
           
    
            try{
                
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1,nombre);
                pst.setString(2,paterno);
                pst.setString(3,materno);
                pst.setString(4,sexo);
                pst.setDate(5,fecha);
               // pst.setString(5,dir);
                pst.setString(6,email);
                pst.setString(7,user);
                pst.setString(8,pass);
                int n=pst.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null,"Registro realizado con éxito");
                    clean();
                    mostrar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null,"Por favor, verifique los campos");
            }
            ////////////////////////////////////////////////////////////////////////////////////////

    }//GEN-LAST:event_btnEnviarActionPerformed

    private void tabEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabEmpleadosMouseClicked
        // TODO add your handling code here:
      /* jtNombre.setText(tabEmpleados.getValueAt(tabEmpleados.getSelectedRow(), 0).toString());
        jtApellidoP.setText(tabEmpleados.getValueAt(tabEmpleados.getSelectedRow(), 1).toString());
        jtEmail.setText(tabEmpleados.getValueAt(tabEmpleados.getSelectedRow(), 2).toString());*/
    }//GEN-LAST:event_tabEmpleadosMouseClicked

    private void cbIdempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIdempActionPerformed
        
            try {
                String idemp = (String) cbIdemp.getSelectedItem();
                conectar cn= new conectar();
                Connection conn = cn.conexion();
                ResultSet rs;
                String sql="SELECT * FROM users where id = '"+idemp+"';";
                PreparedStatement sqls = conn.prepareStatement(sql);
                rs = sqls.executeQuery();
                while(rs.next()){
                jtNombre.setText(rs.getString("nombre"));
                jtApellidoP.setText(rs.getString("paterno"));
                jtApellidoM.setText(rs.getString("materno"));
                jtUsuario.setText(rs.getString("usuario"));
                jtPass.setText(rs.getString("password"));
                String s = rs.getString("sexo");
                if(s.equalsIgnoreCase("M")){
                    rHombre.setSelected(true);
                } else if (s.equalsIgnoreCase("F")){
                    rMujer.setSelected(true);
                }
                //jtDireccion.setText(rs.getString("direc_emp"));
                jtEmail.setText(rs.getString("email"));
                String dateValue = rs.getString("nacimiento"); // What ever column
                java.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateValue);
                fNac.setDate(date);
                //cbCargo.setSelectedIndex(0);
                }
                    } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_cbIdempActionPerformed

    private void jtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtNombreActionPerformed

    private void btActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btActualizarActionPerformed
        String sexo = null;
        verificar();
        Connection con = null;
        String idemp = (String) cbIdemp.getSelectedItem();
        if(rHombre.isSelected()){
                sexo="M";
            }
            else if(rMujer.isSelected()){
                sexo="F";
            }
            /*try {
                dameidCargo();
            } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
             ResultSet rs;
            String sql="UPDATE users SET nombre = ?, paterno = ?, materno = ?, sexo = ?, nacimiento = ?, email = ?, usuario = ?, password = ? WHERE id ='"+idemp+"'";
            PreparedStatement sqls = conn.prepareStatement(sql);
            Date date = fNac.getDate();
            long d = date.getTime();
            java.sql.Date fecha = new java.sql.Date(d);
            sqls.setString(1, jtNombre.getText());
            sqls.setString(2, jtApellidoP.getText());
            sqls.setString(3, jtApellidoM.getText());
            sqls.setString(4, sexo);
            //sqls.setString(5, jtDireccion.getText());
            sqls.setDate(5, fecha);
            sqls.setString(6, jtEmail.getText());
            sqls.setString(7,jtUsuario.getText() );
            sqls.setString(8,jtPass.getText());
            
            int res = sqls.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Empleado actualizado");
              //llenartabla();
              mostrar();
                clean();
                
            } else {
                 JOptionPane.showMessageDialog(null, "Error al actualizar empleado");
                 clean();
            }
            
                    
            
        } catch(Exception e){
            System.err.println(e);
        }
    }//GEN-LAST:event_btActualizarActionPerformed

    private void rHombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rHombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rHombreActionPerformed

    private void rMujerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rMujerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rMujerActionPerformed

    private void jtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtEmailActionPerformed

    private void btnExportarXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarXLSActionPerformed
        // TODO add your handling code here:
                try {
            DatosExcelControl c = new DatosExcelControl();
                    c.exportarExcel(tabEmpleados);
        } catch (IOException ex) {
            Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        mostrar();
       
    }//GEN-LAST:event_btnExportarXLSActionPerformed

    private void btnImportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarExcelActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (*.xls)", "xls");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("BUSCAR ARCHIVO");
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            String valor = importarTablaUsers(fileChooser.getSelectedFile().getAbsolutePath());
            if (valor.equals("hecho")) {
                JOptionPane.showMessageDialog(fileChooser, "Datos importados con éxito");
            }
            mostrar();

            if (valor.equals("error")) {
                JOptionPane.showMessageDialog(fileChooser, "Error al importar tabla.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnImportarExcelActionPerformed

    private void jtEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtEmailKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jtEmailKeyTyped


public boolean isEmail(String correo) {
Pattern pat = null;
Matcher mat = null; 
pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
mat = pat.matcher(correo);
if (mat.find()) {
System.out.println("[" + mat.group() + "]");
return true;
}else{
return false;
} 
}
 
    


    private void jtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtEmailFocusLost
        // TODO add your handling code here:
        if (isEmail(jtEmail.getText())){
            
        }else {
            JOptionPane.showMessageDialog(null, "Email incorrecto", "Validar email", JOptionPane.INFORMATION_MESSAGE);
            jtEmail.requestFocus();
        }
    }//GEN-LAST:event_jtEmailFocusLost

    private void jtApellidoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtApellidoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtApellidoPActionPerformed
   boolean verificarFecha(){
        Date dat=new Date();//Instancia la fecha del sistema
        if(fNac.getDate().getTime()<=dat.getTime()){//Compara si la fecha seleccionada es mayor o igual a la fecha actual
            return true;
        }
        return false;
    }

    private void fNacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fNacFocusLost

    }//GEN-LAST:event_fNacFocusLost
    //Metodo limpiar campos
    
    
    
   
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEnviar;
    public javax.swing.JButton btnExportarXLS;
    public javax.swing.JButton btnImportarExcel;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbIdemp;
    private javax.swing.ButtonGroup gpSexo;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel jlEmail;
    private javax.swing.JLabel jlFnac;
    public javax.swing.JLabel jlFondo;
    private javax.swing.JLabel jlIDuser;
    private javax.swing.JLabel jlMaterno;
    private javax.swing.JLabel jlNombre;
    public javax.swing.JLabel jlPass;
    private javax.swing.JLabel jlPaterno;
    private javax.swing.JLabel jlSexo;
    private javax.swing.JLabel jlUser;
    private javax.swing.JLabel jlUsuarios;
    private javax.swing.JTextField jtApellidoM;
    private javax.swing.JTextField jtApellidoP;
    private javax.swing.JTextField jtEmail;
    private javax.swing.JTextField jtNombre;
    public javax.swing.JPasswordField jtPass;
    public javax.swing.JTextField jtUsuario;
    private javax.swing.JScrollPane jtabEmpleado;
    public javax.swing.JRadioButton rHombre;
    public javax.swing.JRadioButton rMujer;
    private javax.swing.JTable tabEmpleados;
    // End of variables declaration//GEN-END:variables
}
