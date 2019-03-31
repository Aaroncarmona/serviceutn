/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import com.opencsv.CSVReader;
import java.nio.charset.StandardCharsets;
import com.opencsv.CSVWriter;
import java.awt.Desktop;
import java.awt.FileDialog;
//import model.Alumno;
import Model.database;
//import view.verDatos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import Model.Categoria;
import Model.Publicaciones;
import Model.User;
import Model.login;
import View.frmCategorias;


public class DatosExcelControl implements ActionListener{
    private Categoria cat;
    private database bd;
    private frmCategorias frmcat;
    ResultSet rs;
    Statement st;
    public static database cl = new database();
    public static Connection cn = cl.getConnection();
    public static PreparedStatement ps;
    
    public DatosExcelControl(){
        
    }

  /*  public DatosExcelControl(Alumno al, verDatos verdatos) {
        this.al = al;
        this.verdatos = verdatos;
        this.verdatos.btnImportar.addActionListener(this);
        this.verdatos.btnExportar.addActionListener(this);
        this.verdatos.btnExportarCSV.addActionListener(this);
        this.verdatos.btnImportarCSV.addActionListener(this);
        this.verdatos.btnImportarTXT.addActionListener(this);
        this.verdatos.btnExportarTXT.addActionListener(this);
        
    }*/

    public DatosExcelControl(Categoria cat, frmCategorias frmcat) {
        this.cat = cat;
        this.frmcat = frmcat;
        this.frmcat.btnExportarXLS.addActionListener(this);
    }


    
    
    
   /* public void llenarTabla(){
        DefaultTableModel dfm=new DefaultTableModel();
        verdatos.tbAlumnos.setModel(dfm);
        
        dfm.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Paterno", "Materno", "Género", "Edad", "Carrera"});
        
        database cn = new database();
        rs = cn.SeleccionarEstudiantes();
        try{
            while(rs.next()){
                dfm.addRow(new Object[]{rs.getInt("matAlum"), rs.getString("nomAlum"),rs.getString("appAlum"),rs.getString("apmAlum"),rs.getString("genAlum"),rs.getString("Edad"),rs.getString("Carrera")});
            }
        }catch(SQLException e){
                    
                    }
        }*/
    
    public void exportarExcel(JTable t) throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".xls");
            try {
                File archivoXLS = new File(ruta);
                if (archivoXLS.exists()) {
                    archivoXLS.delete();
                }
                archivoXLS.createNewFile();
                Workbook libro = new HSSFWorkbook();
                FileOutputStream archivo = new FileOutputStream(archivoXLS);
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(f);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (f == 0) {
                            celda.setCellValue(t.getColumnName(c));
                        }
                    }
                }
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio);
                    filaInicio++;
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        if (t.getValueAt(f, c) instanceof Double) {
                            celda.setCellValue(Double.parseDouble(t.getValueAt(f, c).toString()));
                        } else if (t.getValueAt(f, c) instanceof Float) {
                            celda.setCellValue(Float.parseFloat((String) t.getValueAt(f, c)));
                        } else {
                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
                        }
                    }
                }
                libro.write(archivo);
                archivo.close();
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    
    public void exportarCSV() throws IOException, SQLException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".csv");
            try {
                File archivoCSV = new File(ruta);
                if (archivoCSV.exists()) {
                    archivoCSV.delete();
                }
                
                st = cn.createStatement();
                rs = st.executeQuery("SELECT * FROM ALUMNOS ORDER BY matAlum");
                
                archivoCSV.createNewFile();
                Path path = Paths.get(ruta);
                CSVWriter csvWriter = new CSVWriter(Files.newBufferedWriter(path,
                    StandardCharsets.ISO_8859_1), CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
                
//                CSVWriter csvWriter = new CSVWriter(new FileWriter(archivoCSV), CSVWriter.DEFAULT_SEPARATOR,
//                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER,
//                    CSVWriter.DEFAULT_LINE_END);
             
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();
                String[] column = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    column[i-1] = metadata.getColumnName(i);
                }
                csvWriter.writeNext(column, false);

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        column[i-1] = rs.getString(i);
                    }
                    csvWriter.writeNext(column, false);
                }

                csvWriter.close();
                
                Desktop.getDesktop().open(archivoCSV);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    
    public static String importarTablaCat(String archivo) {

        int contador = 0;
        try {
            FileInputStream input = new FileInputStream(archivo);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                row = sheet.getRow(i);
                //double mt = row.getCell(0).getNumericCellValue();
                //int matAlum = (int) mt;
                String categoriaID = row.getCell(0).getStringCellValue();
                String descripcion = row.getCell(1).getStringCellValue();
                //String apmAlum = row.getCell(3).getStringCellValue();
                //String genAlum = row.getCell(4).getStringCellValue();
                //d//ouble ed = row.getCell(5).getNumericCellValue();
                //int Edad = (int) ed;
                //String carrera = row.getCell(6).getStringCellValue();

                String sql = "INSERT IGNORE INTO categoria_servicios(" + "categoriaID," + "descripcion)  "
                        + "VALUES("
                        + "'" + categoriaID + "',"
                        + "'" + descripcion + "'" + ")";

                ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Registro importado " + i);
                
            }
            input.close();
            return "hecho";
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
    public static String importarTablaUsers(String archivo) {

        int contador = 0;
        try {
            FileInputStream input = new FileInputStream(archivo);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                row = sheet.getRow(i);
                double iduser = row.getCell(0).getNumericCellValue();
                int id = (int) iduser;
                String nombre = row.getCell(1).getStringCellValue();
                String paterno = row.getCell(2).getStringCellValue();
                String materno = row.getCell(3).getStringCellValue();
                String sexo = row.getCell(4).getStringCellValue();
                String nacimiento = row.getCell(5).getStringCellValue();
                String email = row.getCell(6).getStringCellValue();
                String usuario = row.getCell(7).getStringCellValue();
                String password = row.getCell(8).getStringCellValue();
                //d//ouble ed = row.getCell(5).getNumericCellValue();
                //int Edad = (int) ed;
                //String carrera = row.getCell(6).getStringCellValue();

                String sql = "INSERT IGNORE INTO users(" + "id," + "nombre," + "paterno," + "materno," + "sexo," + "nacimiento," + "telefono," + "email,"+
                        "email_verified_at ,"+ "usuario," + "password, " + " sepoID," + " remember_token," + " created_at," + " update_at" +")"
                        + "VALUES("
                        + "" + id + ","
                        + "'" + nombre + "',"
                        + "'" + paterno + "'"
                        + "'" + materno + "'"
                        + "'" + sexo + "'"
                        + "'" + nacimiento + "'"
                        + "NULL,"
                        + "'" + email + "'"
                        + "NULL,"
                        + "'" + usuario + "'"
                        + "'" + password + "'" 
                        + "NULL,"
                        + "NULL,"
                        + "NULL,"
                        + "NULL"
                        +")";
/*"INSERT INTO users(nombre, paterno,"
                   + " materno, sexo, nacimiento,telefono, email, email_verified_at,  usuario, password,"
                   + "sepoID, remember_token) "
                   + "VALUES(?, ?,?, ?, ?, NULL, ?, NULL,?, ?,NULL, NULL);";*/
                ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Registro importado " + i);
                
            }
            input.close();
            return "hecho";
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
       public static String importarTablaPub(String archivo) {

        int contador = 0;
        try {
            FileInputStream input = new FileInputStream(archivo);
            POIFSFileSystem fs = new POIFSFileSystem(input);
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            Row row;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                row = sheet.getRow(i);
                double id = row.getCell(0).getNumericCellValue();
                int postID = (int) id;
                //String postID = row.getCell(0).getStringCellValue();
                String titulo = row.getCell(1).getStringCellValue();
                String descripcion = row.getCell(2).getStringCellValue();
                String incluye = row.getCell(3).getStringCellValue();
                String no_incluye = row.getCell(4).getStringCellValue();
                double us = row.getCell(5).getNumericCellValue();
                int usuario = (int) us;
                //String usuario = row.getCell(5).getStringCellValue();
                String categoriaServ = row.getCell(6).getStringCellValue();
                //String usuario = row.getCell(7).getStringCellValue();
                //String password = row.getCell(8).getStringCellValue();
                //d//ouble ed = row.getCell(5).getNumericCellValue();
                //int Edad = (int) ed;
                //String carrera = row.getCell(6).getStringCellValue();

                String sql = "INSERT IGNORE INTO publicacion("+"postID," + "titulo," + "descripcion," + "incluye," + "no_incluye," + "usuario,"+ "categoriaServ "+")  "
                        + "VALUES("
                        + "" + postID + ","
                        + "'" + titulo + "',"
                        + "'" + descripcion + "',"
                        + "'" + incluye + "',"
                        + "'" + no_incluye + "',"
                        + "" + usuario + ","
                        //+ "NULL" 
                       // + "'" + email + "'"
                       // + "NULL"
                        //+ "'" + usuario + "'"
                        + "'" + categoriaServ + "'" +")";
                        //+ "NULL" 
                        //+"NULL" +")";
/*"INSERT INTO users(nombre, paterno,"
                   + " materno, sexo, nacimiento,telefono, email, email_verified_at,  usuario, password,"
                   + "sepoID, remember_token) "
                   + "VALUES(?, ?,?, ?, ?, NULL, ?, NULL,?, ?,NULL, NULL);";*/
                ps = cn.prepareStatement(sql);
                ps.executeUpdate();
                System.out.println("Registro importado " + i);
                
            }
            input.close();
            return "hecho";
        } catch (SQLException | IOException | IllegalStateException ex) {
            Logger.getLogger(Categoria.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
    
    
    public void importarExcel() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (*.xls)", "xls");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("BUSCAR ARCHIVO");
        if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            String valor = importarTablaCat(fileChooser.getSelectedFile().getAbsolutePath());
            if (valor.equals("hecho")) {
                JOptionPane.showMessageDialog(fileChooser, "Datos importados con éxito");
            }

            if (valor.equals("error")) {
                JOptionPane.showMessageDialog(fileChooser, "Error al importar tabla.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void importarCSV(){
            
            JFileChooser fileChooser = new JFileChooser();
            
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("BUSCAR ARCHIVO");
            fileChooser.showOpenDialog(fileChooser);
            String ruta = fileChooser.getSelectedFile().getPath();
            Path path = Paths.get(ruta);
            String ruta2=ruta.replaceAll("\\\\", "/");
           
            try {
            String loadQuery = "LOAD DATA INFILE '"+ruta2+"' REPLACE INTO TABLE ALUMNOS COLUMNS TERMINATED BY ','\n" +"OPTIONALLY ENCLOSED BY '\"'\n" +"ESCAPED BY '\"'\n" +"LINES TERMINATED BY '\\n'\n" +" IGNORE 1 LINES;";
            System.out.println(loadQuery);
            Statement st = cn.createStatement();
            st.execute(loadQuery);
            
            JOptionPane.showMessageDialog(null, "Regitros importados con éxito");
        } catch (SQLException ex) {
            Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    
    public void exportarTXT() throws IOException, SQLException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString().concat(".txt");
            try {
                File archivoTXT = new File(ruta);
                if (archivoTXT.exists()) {
                    archivoTXT.delete();
                }
                String ruta2=ruta.replaceAll("\\\\", "/");
                String loadQuery = "SELECT * FROM alumnos INTO OUTFILE '"+ruta2+"'FIELDS TERMINATED BY ','\n" +"OPTIONALLY ENCLOSED BY '\"'\n" +"LINES TERMINATED BY '\\n|'";
                System.out.println(loadQuery);
                Statement st = cn.createStatement();
                st.execute(loadQuery);
                
                Desktop.getDesktop().open(archivoTXT);
            } catch (IOException | NumberFormatException e) {
                throw e;
            }
        }
    }
    
    public void importarTXT(){
            
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto (*.txt)", "txt");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("BUSCAR ARCHIVO");
            fileChooser.showOpenDialog(fileChooser);
            String ruta = fileChooser.getSelectedFile().getPath();
            Path path = Paths.get(ruta);
            String ruta2=ruta.replaceAll("\\\\", "/");
           
            try {
            String loadQuery = "LOAD DATA INFILE '"+ruta2+"' REPLACE INTO TABLE ALUMNOS FIELDS TERMINATED BY ','\n" +"OPTIONALLY ENCLOSED BY '\"'\n" +"LINES TERMINATED BY '\\n|'";
            System.out.println(loadQuery);
            Statement st = cn.createStatement();
            st.execute(loadQuery);
            
            JOptionPane.showMessageDialog(null, "Regitros importados con éxito");
        } catch (SQLException ex) {
            Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    
    public void iniciar() {
       
        
        frmcat.setTitle("Importar y Exportar Datos");
        frmcat.setLocationRelativeTo(null);
       // llenarTabla();
        frmcat.mostrar();
    }
 
    
    
    public void actionPerformed(ActionEvent e){
        JButton btn;
        
        if(e.getSource()==frmcat.btnExportarXLS){
        
        try {
            exportarExcel(frmcat.tabCategorias);
        } catch (IOException ex) {
            Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        if(e.getSource()==frmcat.btnImportarXLS){
               
           importarExcel();
           // llenarTabla();
           frmcat.mostrar();
        
        }
        
        /*if(e.getSource()==verdatos.btnExportarCSV){
        
            try {
                exportarCSV();
            } catch (IOException ex) {
                Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource()==verdatos.btnImportarCSV){
            
                importarCSV();
            
            llenarTabla();
        }
        
        if(e.getSource()==verdatos.btnExportarTXT){
            try {
                exportarTXT();
            } catch (IOException ex) {
                Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DatosExcelControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(e.getSource()==verdatos.btnImportarTXT){
            importarTXT();
            llenarTabla();
        }*/
    }

    
    
}
