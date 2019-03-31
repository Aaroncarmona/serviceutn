/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Oscar Alvarez
 */
public class Publicaciones {
    int postID;
    String titulo;
    String descripcion;
    String incluye;
    String no_incluye;
    int usuario;
    String categoriaID;

    public Publicaciones() {
    }

    public Publicaciones(int postID, String titulo, String descripcion, String incluye, String no_incluye, int usuario, String categoriaID) {
        this.postID = postID;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.incluye = incluye;
        this.no_incluye = no_incluye;
        this.usuario = usuario;
        this.categoriaID = categoriaID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIncluye() {
        return incluye;
    }

    public void setIncluye(String incluye) {
        this.incluye = incluye;
    }

    public String getNo_incluye() {
        return no_incluye;
    }

    public void setNo_incluye(String no_incluye) {
        this.no_incluye = no_incluye;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }
            public String toString() {
        return String.valueOf(postID);
    } 
    
    
}
