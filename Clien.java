import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
  
public class Clien  
{ 
    final static int ServerPort = 1234; 
    public static void main(String args[]) throws UnknownHostException, IOException  
    { 
        Scanner scn = new Scanner(System.in); 
           
        InetAddress ip = InetAddress.getByName("localhost"); 
          
        // establish the connection 
        Socket s = new Socket(ip, ServerPort); 
        //In we cannot control port number given to client  
        // obtaining input and out streams 
        DataInputStream dis = new DataInputStream(s.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
  
        // sendMessage thread 
        Thread sendMessage = new Thread(new Runnable()  
        { 
            public void run() { 
                while (true) { 
  
                    // read the message to deliver. 
                    String msg = scn.nextLine();   
                    try { 
                        // write on the output stream 
                        dos.writeUTF(msg); 
			if(msg.equals("logout"))
			{
			    s.close();
			    dis.close();
			    dos.close();
			    return;
			}
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
          
        // readMessage thread 
        Thread readMessage = new Thread(new Runnable()  
        { 
            public void run() { 
  
                while (true) { 
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        System.out.println(msg); 
			if(msg.equals("Group closed"))
			{
			    s.close();
			    dis.close();
			    dos.close();
			    return;		 	
			}
                    } catch (IOException e) { 
  
                        e.printStackTrace(); 
                    } 
                } 
            } 
        }); 
  
        sendMessage.start(); 
        readMessage.start(); 
    } 
} 
