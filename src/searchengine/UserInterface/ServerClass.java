/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine.UserInterface;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import searchengine.search.SearchDriver;

/**
 *
 * @author 
 */
public class ServerClass {
 //   client_socket cs=new client_socket();
	public void run() throws Exception
        {
		String SearchKey1;
                String SearchKey2;
                Object indexMode;
                Object[] result;
		ServerSocket clientconn = new ServerSocket(4001);
                System.out.println("Server Started.");//displays when server is running
                
		
		while(true)
		{
			Socket sock=clientconn.accept();//accept connection from client
                        System.out.println("Client Connected...");
			BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
			DataOutputStream outtoclient=new DataOutputStream(sock.getOutputStream());
			SearchKey1=br.readLine();
                        SearchKey2=br.readLine();
                        indexMode=br.readLine();
                        result=SearchDriver.main(indexMode,SearchKey1,SearchKey2);
                        int ss=0;
                        for(int i=0;i<result.length;i++)
                        {
                            if(result[i]!=null)
                                ss++;
                        }
                         String s=new Integer(ss).toString();
                        outtoclient.writeBytes(s+'\n');
			for(int i=0;i<result.length;i++)
                        {
                            if(result[i]!=null)
                            outtoclient.writeBytes(result[i].toString()+'\n');
                        }
			
		}
		

	}

}