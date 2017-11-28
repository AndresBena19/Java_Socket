/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modelos.Users;
import servicios.Conexion;
import servicios.Tareas_servicio;
import servicios.Conexion;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author andres felipe
 */
public class Init {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
               
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread mihilo = new Thread(this);
		
		mihilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
                       //Instanciamos el socket
			ServerSocket server = new ServerSocket(7897);
			String name;
                        
                        //Instanciamos paqueteR para recibir los objecto
                        PaqueteSend PaqueteR = new PaqueteSend();
                        
                        
			while(true){
		
                        //Aceptamos la comunicacion   al servidor 
			Socket misocket = server.accept();
                        //Instanciamos Rinfo como objeto que permitira la recepcion de la informacion serializada
                        ObjectInputStream Rinfo = new  ObjectInputStream(misocket.getInputStream());
			PaqueteR = (PaqueteSend) Rinfo.readObject();
                        
                        
			/*DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());
			String Mensaje = flujo_entrada.readUTF();*/
                        
                        
                        //Instanciamos un objeto de tipo tareas_servivios  y de User para capturar la informacion que llega 
                        Tareas_servicio tareas_servicio = new Tareas_servicio();
                        Users user= new Users(); 

                        //En caso de que el valor que llaga en el objeto  .GetTipo sea crear se crearan los registros en el DB
                        if(PaqueteR.getTipo().equals("CREATE")){       
                            user.setName(PaqueteR.getName());
                            user.setPassword(PaqueteR.getPassword());
                            tareas_servicio.guardar(Conexion.obtener(), user);          
                        }
                        //En caso de que el valor que llaga en el objeto   .GetTipo sea borrar  se  borrara  los registros en el DB
                       if(PaqueteR.getTipo().equals("DELETE")){       
                           int a = Integer.parseInt(PaqueteR.getFila());
                           user.setFila(a);
                           tareas_servicio.eliminar(Conexion.obtener(), user.getFila());
                        }
                        
                       
                       //En caso de que el valor que llaga en el objeto   .GetTipo sea show se  se enviaran  los registros en el DB al cliente
                       if(PaqueteR.getTipo().equals("SHOW")){    
                           
                       //Instanciamos un objeto de tipo Users
                        Users userU ;
                                             
                       //Generamos una conexion a el cliente por medio de SOcket
                        Socket socketUp = new Socket( "127.0.0.1" , 9999);
                        
                        //Declaramos una instancia de ObjectOutStream
                        ObjectOutputStream PaqueteU = new  ObjectOutputStream(socketUp.getOutputStream());

                        //Se realiza la conversion de String a entero  de la variable que almacena la fila a mostrar
                        int a = Integer.parseInt(PaqueteR.getFila());
                        user.setFila(a);
                        //Se llama ala funcion Recuperar por ID, par que esta retorne el objeto de tipo Users
                        userU =  tareas_servicio.recuperarPorId(Conexion.obtener(), user.getFila());
                         
                       
                     
                        //Se realiza el envio de la informacion
                        PaqueteU.writeObject( userU);
                        //Se cierra el socket
                        socketUp.close();
                        
                        }
     
                        //Se cierra el socket principal
			misocket.close();
			
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException ex) {
                Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MarcoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
		
		
	}
        
         
}


