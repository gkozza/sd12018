
import java.net.*;
import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import multicastapplication.KeyPairGen;


public class MulticastApplication{
    
    public static void recurso1(){
        System.out.println("escolheu o recurso 1");
        
    }
    
    public static void recurso2(){
        
        System.out.println("escolheu o recurso 2");
    }
    
    
    
    
    public static void main(String[] args) {
                

		// args give message contents and destination multicast group (e.g. "228.5.6.7")
		MulticastSocket s =null;
		try {			
                        InetAddress group = InetAddress.getByName("228.5.6.7");
                        String estado = "REALEASED";
                        String nome;
                        Scanner reader = new Scanner(System.in); 
                        nome = reader.next();                        
                        
                        List<String> online = new ArrayList<String>();
                        List<byte[]> chaves_publicas = new ArrayList<byte[]>();
			
                        s = new MulticastSocket(6789);
                        s.joinGroup(group);
                        online.add(nome);                                           
                        
                        KeyPairGen key = new KeyPairGen();   
                        
                        byte[] pub_key  = key.getPub().getEncoded();
                        key.writeToFile("KeyPair/publicKey" + nome, pub_key);
                        byte[] priv_key =  key.getPriv().getEncoded();
                        key.writeToFile("KeyPair/privateKey" + nome, priv_key);
                                                                     
                        String mensagem = nome + "id";
                                                
 			byte [] m = mensagem.getBytes();
			DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
			s.send(messageOut);	
			byte[] buffer = new byte[1000000];    
     
                        int i = 0;                      
                        String pub_key_ = new String(pub_key) + "pubkey";
                        byte[] pub_key_s = pub_key_.getBytes();
                        DatagramPacket messageOutCP = new DatagramPacket(pub_key_s, pub_key_s.length, group, 6789);
			s.send(messageOutCP);	
			                                                                          			
                        
                        
                        while(true){                           
                            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                            s.receive(messageIn);                                                       
                            //System.out.println(new String(messageIn.getData()) + " entrou");                            
                            String conteudo = new String(messageIn.getData());                                                       
                            if(conteudo.contains("id")){
                                online.add(conteudo); 
                                System.out.println(conteudo + " entrou"); 
                            }
                            if(conteudo.contains("pubkey")){
                                conteudo = conteudo.replace("pubkey", "");
                                byte[] b = conteudo.getBytes();
                                chaves_publicas.add(b);
                            }
                            
                                  
                            
                            
                            i++;
                                                        
                        }
                        
                        //3 pessoas online 
                        
                        
                        
//                        String mensagem_saida = nome + " saiu";
//                        byte [] ms = mensagem_saida.getBytes();
//			DatagramPacket messageOutSaida = new DatagramPacket(ms, ms.length, group, 6789);
//			s.send(messageOutSaida);				                        
//                        s.leaveGroup(group);
//                        
                                
  			
					
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(s != null) s.close();}
	}		      	
	
}