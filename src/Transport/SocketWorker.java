package Transport;

/**
 * Created with IntelliJ IDEA.
 * User: Shmelev
 * Date: 02.10.12
 * Time: 14:36
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.net.*;
public class SocketWorker
{
    private Socket socket   =   null;
    private int    port;
    private String hostname;
    
    public Socket getSocket()
    {
        return socket;
    }
    public SocketWorker(String hostname, int port)
    {
        this.hostname   =   hostname;
        this.port       =   port;
        System.out.println("SocketWorker constructed");
    }
    public Socket createSocket()
    {
        try
        {
            socket  =   new Socket(hostname,port);   
        }
        catch (UnknownHostException unk)
        {
            System.out.println("createSocket error: UnknownHostException");
            return null;
        }
        catch (IOException e)
        {
            System.out.println("createSocket error: IOException");
            return null;
        }
        System.out.println("createSocket success");
        return socket;
    }
    @Deprecated
    public void closeSocket()
    {
        try
        {
            if ((socket  !=  null)&& (!socket.isClosed()))
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
            System.out.println("closeSocket error: IOException");
            return;
        }
        finally
        {
            socket = null;
        }
        System.out.println("closeSocket success");
    }

}
