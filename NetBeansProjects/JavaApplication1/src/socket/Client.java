/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

/**
 *
 * @author YEN-WEN WANG
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main( String[] args ) throws IOException
    {
        String host = "localhost";
        int port = 5987;
        float txt=0;
        String oper;
        Socket socket = null;
        Scanner consoleInput = new Scanner( System.in );
        //System.out.println("請輸入Server端位址");
        //host = consoleInput.nextLine();
        try
        {
            socket = new Socket( host, port );
            DataInputStream input = null;
            DataOutputStream output = null;
            
            try
            {
                input = new DataInputStream( socket.getInputStream() );
                output = new DataOutputStream( socket.getOutputStream() );
                while ( true )
                {
                    try{
                        System.out.println("A:");
                        txt=consoleInput.nextFloat();
                        output.writeFloat(txt);
                        System.out.println( input.readUTF() );
                        System.out.println("B:");
                        txt=consoleInput.nextFloat();
                        output.writeFloat(txt);
                        System.out.println( input.readUTF() );
                        System.out.println("O:");
                        oper=consoleInput.next();
                        output.writeUTF(oper);
                        System.out.println( input.readUTF() );
                        output.flush();
                    }
                    catch(Exception o){
                        System.out.println("pls number");
                        output.writeUTF("Error");
                        consoleInput.nextLine();
                        continue;
                    }
                    
                }
            }
            catch ( IOException e )
            {
            }
            finally 
            {
                if ( input != null )
                    input.close();
                if ( output != null )
                    output.close();
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( socket != null )
                socket.close();
            if ( consoleInput != null )
                consoleInput.close();
        }
    }
}