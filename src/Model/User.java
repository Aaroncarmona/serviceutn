/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Date;
/**
 *
 * @author Oscar Alvarez
 */
public class User {
    int id;
    String nombre;
    String paterno;
    String materno;
    String sexo;
    Date nacimiento;
    String telefono;
    String email;
    String usuario;
    String pass;

    public User() {
    }

    public User(int id, String nombre, String paterno, String materno, String sexo, Date nacimiento, String telefono, String email,String usuario, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.pass = pass;
    }

    public User(int id, String nombre, String paterno, String materno, String sexo, Date nacimiento, String email, String usuario, String pass) {
        this.id = id;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.email = email;
        this.usuario = usuario;
        this.pass = pass;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    
    
         public String toString() {
        return String.valueOf(id);
    }
    
           
    
}
