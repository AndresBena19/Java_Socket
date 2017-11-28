/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

/**
 *
 * @author andres felipe
 */


import java.awt.BorderLayout;
import javax.swing.*;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.Users;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(400,400,250,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel {
	
	public LaminaMarcoCliente(){
	
		JLabel texto=new JLabel("User");
		add(texto);
		campo1=new JTextField(20);
		add(campo1);	
                
                JLabel texto2=new JLabel("password");
		add(texto2);
                
		campo4=new JTextField(20);
                
		add(campo4);
                
                
                
                
                JLabel texto3=new JLabel("Fila");
		add(texto3);
		campo3=new JTextField(20);
		add(campo3);
                
               
	
                miborrar=new JButton("DELETE");
		DeleteTexto borrar= new DeleteTexto();
		miborrar.addActionListener(borrar);
		add(miborrar);	
                
                
		miboton=new JButton("CREATE");
		CreateTexto mievento= new CreateTexto();
		miboton.addActionListener(mievento);
		add(miboton);	
                
                mishow=new JButton("SHOW");
		ShowTexto Mostrar= new ShowTexto();
		mishow.addActionListener(Mostrar);
		add(mishow);
                
                
                areatexto=new JTextArea();
		add(areatexto);
		
                
                
                
                
                
                
		
	}
        
        
        private class CreateTexto implements ActionListener{

            
            
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket misocket = new Socket("127.0.0.1",  7897);
                PaqueteSend Send = new PaqueteSend();
                
                Send.setTipo(miboton.getText());
                Send.setName(campo1.getText());
                Send.setPassword(campo4.getText());
                
                ObjectOutputStream InfoSend = new ObjectOutputStream(misocket.getOutputStream());
                InfoSend.writeObject(Send);
                misocket.close(); 
                        
                        
                        
                        } catch (IOException ex) {
                Logger.getLogger(LaminaMarcoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        
        
        
        }
        
        
          private class DeleteTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
               
            
            try {
                Socket misocket = new Socket("127.0.0.1",  7897);
                PaqueteSend Send = new PaqueteSend();
                
                Send.setTipo(miborrar.getText());
                Send.setFila(campo3.getText());
                
                ObjectOutputStream InfoSend = new ObjectOutputStream(misocket.getOutputStream());
                InfoSend.writeObject(Send);
                misocket.close(); 
                
                
                
            } catch (IOException ex) {
                Logger.getLogger(LaminaMarcoCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
          
          
          
          
          
          
          }

   

	private class ShowTexto implements ActionListener , Runnable{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			
			
			try {
				Socket misocket = new Socket("127.0.0.1",  7897);
                                PaqueteSend Send = new PaqueteSend();
                             
                                Thread mihilo= new Thread( this);
                                mihilo.start();
                               
                                Send.setTipo(mishow.getText());
                                Send.setFila(campo3.getText()); 
                                
                              
                                    
                                
                           
                             ObjectOutputStream InfoSend = new ObjectOutputStream(misocket.getOutputStream());
                             InfoSend.writeObject(Send);
                             misocket.close(); 

                                
                                
                                
                              
                                
                                
                                
                                
                                
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

        @Override
        public void run() {
            try {
         
                        ServerSocket serverR = new ServerSocket(9999);
                        Users Update;
                        while(true){
                            
                            Socket misocket2 = serverR.accept();
                            ObjectInputStream Rinfo = new  ObjectInputStream(misocket2.getInputStream());
                            
                            
                            Update =    (Users) Rinfo.readObject();
                            System.out.println(Update.getName());
                            System.out.println(Update.getPassword());
                                   
                                    
                             areatexto.append("\n" + "User:" + Update.getName() + "        " + "password:" + Update.getPassword());       
                                    
                            
                            serverR.close();
                        }

                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(LaminaMarcoCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
      
        }

          
	}
			
	private JTextField campo1;
        private JTextField campo2;
        private JTextField campo3;
        private JTextField campo4;
	
	private JButton miboton;
        private JButton mienviar;
        private JButton miborrar;
        private JButton mishow;
        private JTextArea areatexto;
        
                
	
}




 class PaqueteSend implements Serializable{
    
    private String name, tipo;
    String fila;
    String password;

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

  public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }



}