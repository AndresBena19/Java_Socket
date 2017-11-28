/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.Serializable;

/**
 *
 * @author andres felipe
 */

public class Users implements Serializable{
   private final Integer id_user;
   private String name;
   private int fila;
   private String password;
 
   
   public Users() {
      this.id_user = null;
      this.name= null;
      this.fila=0;
      this.password=null;
   }
   
   public Users(Integer id_tarea, String name, Integer fila) {
      this.id_user = id_tarea;
      this.name = name;
      this.fila=fila;
   
   }
   
   
    public Users(int id_user, String string) {
            this.id_user = id_user;
            this.name = name;
    }
    
    public Users(Integer id_tarea, String name, String password) {
      this.id_user = id_tarea;
      this.name = name;
      this.password=password;
   
   }
   
   

   public Integer getId_user() {
      return id_user;
   }
   public String getName() {
      return name;
   }
  
   public void setName(String name) {
      this.name= name;
   }
  
   
    public void setFila(int fila) {
       this.fila=fila;
    }
    
    public int getFila() {
       return fila;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
   @Override
   public String toString() {
      return "Tarea{" + "id_tarea=" + id_user + ", nombre=" + name + '}';
   } 

    
    
}