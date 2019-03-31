/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Controller.DatosExcelControl;
import static Controller.DatosExcelControl.importarTablaPub;
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
import Model.Publicaciones;
import Model.Categoria;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author Oscar Alvarez
 */
public class frmPublicaciones extends javax.swing.JFrame {
    String idCat;
    int idUser;
    /**
     * Creates new form frmPublicaciones
     */
    public frmPublicaciones() throws SQLException {
        initComponents();
         this.setLocationRelativeTo(null);
         cbpostID.addItem("Busqueda por ID");
         cbCategoriaServ.addItem("Categorias disponibles");
         cbUsuario.addItem("Usuario");
         //llenartabla();
         mostrar();
         dameIdCategoria();
         dameIdPublicacion();
         dameIdUser();
    }
    //////Verificar campos
        void verificar(){
        if(jtDescripcion.getText().equals("")||jtIncluye.getText().equals("")||jtNoIncluye.getText().equals("")
                ||jtTitulo.getText().equals("")||cbCategoriaServ.getSelectedIndex()==0||cbUsuario.getSelectedIndex()==0){
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los datos");
        }
        }
    //////////Limpiar
    public void clean(){
        cbCategoriaServ.setSelectedIndex(0);
        cbpostID.setSelectedIndex(0);
        jtDescripcion.setText("");
        jtIncluye.setText("");
        jtNoIncluye.setText("");
        jtTitulo.setText("");
        jlUsuario.setText("");
        cbUsuario.setSelectedIndex(0);
       

        
    }
/////////////////Dame id publicacion
     void dameIdPublicacion() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select distinct * from publicacion ";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            Publicaciones p = new Publicaciones();
            p.setPostID(Integer.parseInt(rs.getString("postID")));
            p.setTitulo(rs.getString("titulo"));
            //cbpostID.addItem(p.toString());
            cbpostID.addItem(p.toString());
        }
    } 
     ////////////////////////////////////////////dame nombre usuario
          void dameNombreUsuario() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select usuario * from users ";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            User u = new User();
            
            u.setUsuario(rs.getString("usuario"));
            //cbpostID.addItem(p.toString());
            cbpostID.addItem(u.getUsuario());
        }
    } 
///////////////Dame id categorias
        void dameIdCategoria() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select * from categoria_servicios";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            Categoria c = new Categoria();
            c.setId(rs.getString("categoriaID"));
            cbCategoriaServ.addItem(c.toString());
        }
    }
//////////////////Dame id user
        void dameIdUser() throws SQLException{
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        ResultSet rs;
        String sql="select * from users";
        PreparedStatement sqls = conn.prepareStatement(sql);
        rs = sqls.executeQuery();
        while(rs.next()){
            User u = new User();
            u.setId(rs.getInt("id"));
            cbUsuario.addItem(u.toString());
        }
    }
///////////////////Mostrar datos tabla
    public void mostrar (){
        conectar cn= new conectar();
        Connection conn = cn.conexion();
        DefaultTableModel modelo = new DefaultTableModel();
        ResultSet rs = database.getTable("select postId, titulo, descripcion, incluye, no_incluye, usuario, categoriaServ from publicacion");
        modelo.setColumnIdentifiers(new Object []{"ID","TITULO","DESCRIPCION","INCLUYE","NO INCLUYE","USUARIO","CATEGORIA"});
        try{
            while (rs.next()){
                modelo.addRow(new Object[]{rs.getInt("postId"), rs.getString("titulo"), rs.getString("descripcion"),
                    rs.getString("incluye"), rs.getString("no_incluye"),rs.getInt("usuario"),rs.getString("categoriaServ")});
                }
            tabPublicaciones.setModel(modelo);
            }catch(Exception e){
            System.out.print(e);
            }
        }  
///////////////////inserta id categoria
    void insertaidCategoria() throws SQLException{
        
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String cat = (String) cbCategoriaServ.getSelectedItem();
            String sql="SELECT categoriaID FROM categoria_servicios WHERE categoriaID = '"+cat+"';";
            PreparedStatement sqls = conn.prepareStatement(sql);
            rs = sqls.executeQuery();
            while(rs.next()){
            Categoria c = new Categoria();
            c.setId(rs.getString("categoriaID"));
            idCat=c.getId();
            }
            } catch(Exception e){
                        String cat = (String) cbCategoriaServ.getSelectedItem();
                        System.out.println(cat);
                    }
    }   
///////////////////insertar id usuario
    void insertaidUser() throws SQLException{
        
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String id = (String) cbUsuario.getSelectedItem();
            String sql="SELECT id FROM users WHERE id = '"+id+"';";
            PreparedStatement sqls = conn.prepareStatement(sql);
            rs = sqls.executeQuery();
            while(rs.next()){
            User u = new User();
            u.setId(rs.getInt("id"));
            idUser=u.getId();
            }
            } catch(Exception e){
                        String id = (String) cbUsuario.getSelectedItem();
                        System.out.println(id);
                    }
    } 
/////////////////Llenar tabla
     public void llenartabla() throws SQLException{
        conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String sql="SELECT * FROM publicacion";
            PreparedStatement sqls = conn.prepareStatement(sql);
            rs = sqls.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            tabPublicaciones.setModel(modelo);
            modelo.addColumn("ID");
            modelo.addColumn("TITULO"); 
            modelo.addColumn("DESCRIPCION");
            modelo.addColumn("INCLUYE");
            modelo.addColumn("NO INCLUYE");
            modelo.addColumn("USUARIO");
            modelo.addColumn("CATEGORIA");
            while(rs.next()){
                Object [] fila = new Object[5]; 
                for (int i=0;i<5;i++)
                fila[i] = rs.getObject(i+1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
                modelo.addRow(fila); 
            }
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField5 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jtTitulo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtIncluye = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbCategoriaServ = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jtNoIncluye = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabPublicaciones = new javax.swing.JTable();
        cbpostID = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDescripcion = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jlUsuario = new javax.swing.JLabel();
        cbUsuario = new javax.swing.JComboBox<>();
        btnLimpiar = new javax.swing.JButton();
        btnExportarXLS = new javax.swing.JButton();
        btnImportarExcel = new javax.swing.JButton();

        jTextField5.setText("jTextField5");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Publicaciones");

        jtTitulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtTituloKeyTyped(evt);
            }
        });

        jLabel2.setText("Post ID");

        jLabel3.setText("Titulo");

        jLabel4.setText("Descripcion");

        jLabel5.setText("Incluye");

        jLabel6.setText("Usuario");

        jLabel7.setText("Categoria Servicio");

        jLabel8.setText("No Incluye");

        jtNoIncluye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNoIncluyeActionPerformed(evt);
            }
        });

        tabPublicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabPublicaciones);

        cbpostID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbpostIDActionPerformed(evt);
            }
        });

        jtDescripcion.setColumns(20);
        jtDescripcion.setRows(5);
        jtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtDescripcionKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jtDescripcion);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jlUsuario.setText(".......");

        cbUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUsuarioActionPerformed(evt);
            }
        });

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnExportarXLS.setText("Exportar XLS");
        btnExportarXLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarXLSActionPerformed(evt);
            }
        });

        btnImportarExcel.setText("Importar Excel");
        btnImportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtTitulo)
                                    .addComponent(cbpostID, 0, 290, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbCategoriaServ, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel6))
                                .addGap(35, 35, 35)
                                .addComponent(jlUsuario)
                                .addGap(18, 18, 18)
                                .addComponent(cbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimpiar)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(181, 181, 181)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnExportarXLS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnImportarExcel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(171, 171, 171)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtNoIncluye, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtIncluye, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(56, 56, 56))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel1)
                .addContainerGap(323, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbpostID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEnviar)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnActualizar)
                            .addComponent(btnLimpiar))
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jtIncluye, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtNoIncluye, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jlUsuario)
                                    .addComponent(cbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cbCategoriaServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(btnExportarXLS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImportarExcel)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtNoIncluyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNoIncluyeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jtNoIncluyeActionPerformed

    private void cbpostIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbpostIDActionPerformed
        // TODO add your handling code here:
                        
            try {
                String idpub = (String) cbpostID.getSelectedItem();
                conectar cn= new conectar();
                Connection conn = cn.conexion();
                ResultSet rs;
                String sql="SELECT * FROM publicacion p, users u where postID = '"+idpub+"';";
                PreparedStatement sqls = conn.prepareStatement(sql);
                rs = sqls.executeQuery();
                while(rs.next()){
                jtDescripcion.setText(rs.getString("p.descripcion"));
                jtTitulo.setText(rs.getString("p.titulo"));
                jtIncluye.setText(rs.getString("p.incluye"));
                jtNoIncluye.setText(rs.getString("p.no_incluye"));
                jlUsuario.setText(rs.getString("u.usuario"));
                cbCategoriaServ.setSelectedItem(rs.getString("p.categoriaServ"));
                cbUsuario.setSelectedItem(rs.getString("p.usuario"));
                /*String s = rs.getString("sexo");
                if(s.equalsIgnoreCase("H")){
                    rHombre.setSelected(true);
                } else if (s.equalsIgnoreCase("M")){
                    rMujer.setSelected(true);
                }*/
                //jtDireccion.setText(rs.getString("direc_emp"));
                //jtEmail.setText(rs.getString("email"));
                //String dateValue = rs.getString("nacimiento"); // What ever column
                //ava.util.Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateValue);
               // fNac.setDate(date);
                //cbCargo.setSelectedIndex(0);
                }
                    } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            } /*catch (ParseException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            }*/
    }//GEN-LAST:event_cbpostIDActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // TODO add your handling code here:
       verificar();
         String titulo=jtTitulo.getText();
         String desc=jtDescripcion.getText();
        String incluye = jtIncluye.getText();
        String no_incluye = jtNoIncluye.getText();
         try {
        insertaidUser();
        } catch (SQLException ex) {
            Logger.getLogger(frmPublicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        insertaidCategoria();
        } catch (SQLException ex) {
            Logger.getLogger(frmPublicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }    
            conectar cc=new conectar();
            Connection cn=cc.conexion();
           String sql="INSERT INTO publicacion(titulo, descripcion, incluye, no_incluye,usuario,categoriaServ) "
                   + "VALUES(?,?,?,?,?,?);";
           
 
            try{
                
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setString(1,titulo);
                pst.setString(2,desc);
                pst.setString(3,incluye);
                pst.setString(4,no_incluye);
                pst.setInt(5,idUser);
                pst.setString(6,idCat);
                
   
                int n=pst.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(null,"Registro realizado con éxito");
                    mostrar();
                    clean();
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null,"Por favor, verifique los campos");
            }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
         verificar();
        Connection con = null;
        String idpub = (String) cbpostID.getSelectedItem();
                    try {
                insertaidUser();
            } catch (SQLException ex) {
                Logger.getLogger(frmPublicaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
                    try {
                insertaidCategoria();
            } catch (SQLException ex) {
                Logger.getLogger(frmPublicaciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
            ResultSet rs;
            String sql="UPDATE publicacion SET titulo = ?, descripcion = ?, incluye = ?,"
                    + " no_incluye = ?, usuario = ?, categoriaServ = ? WHERE postID ='"+idpub+"'";
            PreparedStatement sqls = conn.prepareStatement(sql);

            sqls.setString(1, jtTitulo.getText());
            sqls.setString(2, jtDescripcion.getText());
            sqls.setString(3, jtIncluye.getText());
            sqls.setString(4, jtNoIncluye.getText());
            sqls.setInt(5, idUser);
            sqls.setString(6, idCat);

            
            int res = sqls.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Categoria actualizada");
             
              mostrar();
                clean();
                
            } else {
                 JOptionPane.showMessageDialog(null, "Error al actualizar publicacion");
                 
                 clean();
            }
            
                    
            
        } catch(Exception e){
            System.err.println(e);
        }
        
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
          if (cbpostID.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar algún id");
            
        }
        
        Connection con = null;
        //Date date = fNac.getDate();
        //long d = date.getTime();
        //java.sql.Date fecha = new java.sql.Date(d);
        String idpub = (String) cbpostID.getSelectedItem();
        
        try{
            conectar cn= new conectar();
            Connection conn = cn.conexion();
             ResultSet rs;
             String sql="DELETE FROM publicacion WHERE postID = ?";
            PreparedStatement sqls = conn.prepareStatement(sql);
            sqls.setString(1, idpub);
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

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        clean();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnExportarXLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarXLSActionPerformed
        // TODO add your handling code here:
                try {
            DatosExcelControl c = new DatosExcelControl();
                    c.exportarExcel(tabPublicaciones);
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
            String valor = importarTablaPub(fileChooser.getSelectedFile().getAbsolutePath());
            if (valor.equals("hecho")) {
                JOptionPane.showMessageDialog(fileChooser, "Datos importados con éxito");
            }
            mostrar();

            if (valor.equals("error")) {
                JOptionPane.showMessageDialog(fileChooser, "Error al importar tabla.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnImportarExcelActionPerformed

    private void jtTituloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtTituloKeyTyped
        // TODO add your handling code here:
int limite = 60;

if(jtTitulo.getText().length()== limite){
   evt.consume(); 
}

    }//GEN-LAST:event_jtTituloKeyTyped

    private void jtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDescripcionKeyTyped
        // TODO add your handling code here:
int limite = 50;

if(jtDescripcion.getText().length()== limite){
   evt.consume(); 
}

    }//GEN-LAST:event_jtDescripcionKeyTyped

    private void cbUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUsuarioActionPerformed
 try {
                String idUser = (String) cbUsuario.getSelectedItem();
                conectar cn= new conectar();
                Connection conn = cn.conexion();
                ResultSet rs;
                String sql="SELECT * FROM users  where id = '"+idUser+"';";
                PreparedStatement sqls = conn.prepareStatement(sql);
                rs = sqls.executeQuery();
                while(rs.next()){
  
                jlUsuario.setText(rs.getString("usuario"));

                }
                    } catch (SQLException ex) {
                Logger.getLogger(FrmUsers.class.getName()).log(Level.SEVERE, null, ex);
            }    
    }//GEN-LAST:event_cbUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnEnviar;
    public javax.swing.JButton btnExportarXLS;
    public javax.swing.JButton btnImportarExcel;
    public javax.swing.JButton btnLimpiar;
    public javax.swing.JComboBox<String> cbCategoriaServ;
    public javax.swing.JComboBox<String> cbUsuario;
    public javax.swing.JComboBox<String> cbpostID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField5;
    public javax.swing.JLabel jlUsuario;
    public javax.swing.JTextArea jtDescripcion;
    public javax.swing.JTextField jtIncluye;
    public javax.swing.JTextField jtNoIncluye;
    public javax.swing.JTextField jtTitulo;
    public javax.swing.JTable tabPublicaciones;
    // End of variables declaration//GEN-END:variables
}
