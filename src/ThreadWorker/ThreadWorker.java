package ThreadWorker;

import ClientForm.*;
import ObjectExchange.*;
import Transport.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
public class ThreadWorker extends Thread
{
    protected SocketWorker socketWorker   = null;
    protected Socket socket = null;
    
    ObjectInputStream  reader;
    ObjectOutputStream writer;
    ProxyWindow proxyWindow;
    ArrayList<Friend> chat_user_list;
    protected   final static int IN_MESSAGE_RECEIVE             =   1;  //Сервер говорит "Ваше сообщение получено"
    protected   final static int OUT_MESSAGE_RECEIVE            =   1;  //Клиент говорит "Ваше сообщение получено"
    public      final static int OUT_MESSAGE_FOR_FRIEND         =   2;  //
    public      final static int OUT_MESSAGE_FOR_ALL_FRIENDS    =   3;
    protected   final static int IN_PRIVATE_MESSAGE             =   4;
    public      final static int IN_MULTI_CAST_MESSAGE          =   5;

    protected final static int OUT_CONNECT_START                =   11;  //Я соеденяюсь
    protected final static int IN_CLIENT_CREDENTIALS            =   12;
    protected final static int OUT_GET_CLIENT_FRIENDS_LIST      =   13;
    protected final static int OUT_GET_FRIENDS_LIST             =   14;
    protected final static int IN_FRIENDS_LIST                  =   15;
    protected final static int  IN_FRIEND_CHANGE_STATUS         =   16;
    
    public final static int OUT_CLIENT_CHANGE_STATUS            =   17;
    
        
    protected final static int  IN_FRIEND_CHANGE_NICK           =   18;
    
    public final static int     OUT_FRIEND_CHANGE_NICK          =   18;

    protected final static int IN_SERVER_IS_DOWN_SESSION_CLOSE  =   110;
    protected final static int OUT_SESSION_CLOSE                =   111;
    protected int transactionId = -1;
    private int prevCommand    =   0;
    
        


    public ThreadWorker(SocketWorker socketWorker, ProxyWindow proxyWindow)
    {
        this.socketWorker   =   socketWorker;
        this.socket         =   socketWorker.getSocket();
        this.proxyWindow    =   proxyWindow;
        System.out.println("ThreadWorker constructed");
    }
    
    public void initialize() throws IOException
    {
        System.out.println("ThreadWorker# initialize start");
        writer = new ObjectOutputStream((socket.getOutputStream()));
        writer.flush();
        reader = new ObjectInputStream (new BufferedInputStream(socket.getInputStream()));
        System.out.println("ThreadWorker# initialize success");
    }
    
    public void sendMessage(ObjectExchange data) throws IOException
    {
        synchronized (this)
        {
            if (writer  != null){
                data.friend_id  =   transactionId;
                writer.writeObject(data);
                writer.flush();
            }
        }
    }
    public void closedSession()
    {
        try
        {  
            sendMessage(new ObjectExchangeWrap(OUT_SESSION_CLOSE, null, transactionId).getObjectExchange());
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Showdown input and output");
        }
        catch (Exception e)
        {
            System.out.println("try shutdown error");
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void run()
    {
        try
        {
            System.out.println("New connection"+socket.getRemoteSocketAddress());
            initialize();
            sendMessage( new ObjectExchangeWrap(OUT_CONNECT_START, null, 0).getObjectExchange());
            System.out.println("ThreadWorker# getTransactionId success");
            ObjectExchange data;
            Friend friend;
            Gson gson   =   new Gson();
            Type collectionType = new TypeToken<ArrayList<Friend>>(){}.getType();
            while (((data = (ObjectExchange) reader.readObject()) != null) ) {

                switch(data.message_code)
                {
                    //MESSAGE
                    case IN_MESSAGE_RECEIVE:
                        System.out.println("Server receive message !");
                        break;
                    case IN_PRIVATE_MESSAGE:
                        System.out.println("Date :"+new Date().toString());
                        System.out.println("Private message from :"+data.friend_id);
                        System.out.println("Message : "+data.message);
                        proxyWindow.setText(data);
                        sendMessage(new ObjectExchangeWrap(OUT_MESSAGE_RECEIVE, null, transactionId).getObjectExchange());
                        break;
                    case IN_MULTI_CAST_MESSAGE:
                        System.out.println("Date :"+new Date().toString());
                        System.out.println("Multicast message from :"+data.friend_id);
                        System.out.println("Message : "+data.message);
                        proxyWindow.setText(data);
                        sendMessage(new ObjectExchangeWrap(OUT_MESSAGE_RECEIVE, null, transactionId).getObjectExchange());
                        break;
                    //    
                    case IN_CLIENT_CREDENTIALS:
                        transactionId   =   data.friend_id;
                        proxyWindow.setWindowHeader(data);
                        sendMessage(new ObjectExchangeWrap(OUT_GET_FRIENDS_LIST, null, transactionId).getObjectExchange());
                        System.out.println("Received :" + transactionId);
                        break;
                        
                    //SYSTEM MESSAGE
                    case IN_FRIENDS_LIST:
                        System.out.println("Received :" + data.message);
                        chat_user_list = gson.fromJson(data.message, collectionType);
                        proxyWindow.setFriendList(chat_user_list);
                        sendMessage(new ObjectExchangeWrap(OUT_MESSAGE_RECEIVE, null, transactionId).getObjectExchange());
                        break;
                        
                    case IN_FRIEND_CHANGE_STATUS: //status status message
                        System.out.println("Received :" + data.message);
                        friend = gson.fromJson(data.message, Friend.class);
                        proxyWindow.changeFriendList(friend);
                        sendMessage(new ObjectExchangeWrap(OUT_MESSAGE_RECEIVE, null, transactionId).getObjectExchange());
                        break;
                    case IN_FRIEND_CHANGE_NICK:     
                        System.out.println("Received :" + data.message);
                        friend = gson.fromJson(data.message, Friend.class);
                        proxyWindow.changeFriendNickName(friend);
                        sendMessage(new ObjectExchangeWrap(OUT_MESSAGE_RECEIVE, null, transactionId).getObjectExchange());
                        break;
                }
                prevCommand = data.message_code;  
            }
        }
        catch (Exception e)
        {
            System.out.println("ThreadWorker# run error");
        }
        finally {
            try
            {
                reader.close();
                writer.close();
                socket.close();
            }
            catch (IOException e1)
            {
                System.out.println("ThreadWorker# finally error");
                //e1.printStackTrace();
            }
            System.out.println("Завершающая оло");
        }
    }
}
