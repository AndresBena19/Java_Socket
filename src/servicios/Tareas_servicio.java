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
    
   //Nombre de la tabla en la base de datos
   private final String tabla = "users";
   
   //Metodo guardar, encargada de establecer conexion con la base de datos y gurdar la informacion que entra como objeto a la db, por medio de un query
   public void guardar(Connection conexion, Users user) throws SQLException{
      try{
         PreparedStatement consulta;
         //Validamos que el id del usuario no sea nulo
         if(user.getId_user() == null){
            //Se establece el query
            consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(name , password) VALUES(?, ?)");
            //Se ingresan los valores al query
            consulta.setString(1, user.getName());
            consulta.setString(2, user.getPassword());
         //En caso de que el id no sea nulo, entonces se procedera a actualizar la db
         }else{
            //Se establece el query
            consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET name = ? WHERE id_user = ?");
            //Se ingresan los valores al query
            consulta.setString(1, user.getName());
            consulta.setInt(2, user.getId_user());
            
         }
         //Se envia el query a la db para su ejecuacion
         consulta.executeUpdate();
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
   //Metodo recargar por id , encargada de establecer conexion con la base de datos y de utilizar como index un entero que representa la fila a mostrar
   public Users recuperarPorId(Connection conexion, int id_user) throws SQLException {
      //Se instancia un objeto de tipo Users
       Users user = null;
      try{
           //Se establece el query
         PreparedStatement consulta = conexion.prepareStatement("SELECT name, password FROM " + this.tabla + " WHERE id_user = ?" );
           //Se ingresan los valores al query
         consulta.setInt(1, id_user);
         //Se envia el query a la db para su ejecuacion y se intancia un objeto de ResultSet para conservar la respuestas de la DB
         ResultSet resultado = consulta.executeQuery();
         //Se ingresan los valores extraidos de la db en un bjeto de tipo Users
         while(resultado.next()){
            user = new Users(id_user, resultado.getString("name"), resultado.getString("password"));
         }
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
      
      //Se retorna un objeto de tipo Users
      return user;
   }
   
  //Metodo eliminar, encargada de establecer conexion con la base de datos y de utilizar como index un entero que representa la fila a mostrar
   public void eliminar(Connection conexion, int fila) throws SQLException{
      try{
         //Se establece el query
         PreparedStatement consulta = conexion.prepareStatement("DELETE FROM " + this.tabla + " WHERE id_user = ?");
         //Se ingresan los valores al query
         consulta.setInt(1, fila);
         consulta.executeUpdate();
         
      }catch(SQLException ex){
         throw new SQLException(ex);
      }
   }
}