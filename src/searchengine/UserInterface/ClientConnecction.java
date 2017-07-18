/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine.UserInterface;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author 
 */
public class ClientConnecction {
    String count,array[];
    int size;
    public String[] connection(String key1,String key2,String indexMode) throws UnknownHostException, IOException
    {
        System.out.println("IN CLIENT");
    Socket sock=new Socket("127.0.0.1",4001);
        DataOutputStream dos=new DataOutputStream(sock.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(sock.getInputStream()));
        dos.writeBytes(key1+'\n'+key2+'\n'+indexMode+'\n');
        count=br.readLine();
        size=Integer.parseInt(count);
        array=new String[size];
        for(int i=0;i<size;i++)
        {
            array[i]=br.readLine();
        }
        sock.close();
        return array;
    }
}
