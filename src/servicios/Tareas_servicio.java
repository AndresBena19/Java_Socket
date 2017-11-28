/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

/**
 *
 * @author andres felipe
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Users;
public class Tareas_servicio {
   private final String tabla = "users";
   public void guardar(Connection conexion, Users user) throws SQLException{
      try{
         PreparedStatement consulta;
         if(user.getId_user() == null){
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(name , password) VALUES(?, ?)");
            consulta.setString(1, user.getName());
            consulta.setString(2, user.getPassword());
 
         }else{
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET name = ? WHERE id_user = ?");
            consulta.setString(1, user.getName());
            consulta.setInt(2, user.getId_user());
            
            System.out.println("hola");
         }
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   public Users recuperarPorId(Connection conexion, int id_user) throws SQLException {
      Users user = null;
      try{
         PreparedStatement consulta = conexion.prepareStatement("SELECT name, password FROM " + this.tabla + " WHERE id_user = ?" );
         consulta.setInt(1, id_user);
         ResultSet resultado = consulta.executeQuery();
         while(resultado.next()){
            user = new Users(id_user, resultado.getString("name"), resultado.getString("password"));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      return user;
   }
   public void eliminar(Connection conexion, int fila) throws SQLException{
      try{
         PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id_user = ?");
         consulta.setInt(1, fila);
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   
}