
package Model;
import Controller.conectar;
public class login {
    private int emp_id;
    private String clave;




    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    conectar metodosConectar = new conectar();

    @Override
    public String toString() {
        return String.valueOf(emp_id);
    }
    
    
        
        
    }
    
    

