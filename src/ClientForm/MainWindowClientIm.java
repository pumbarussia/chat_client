/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientForm;

import ObjectExchange.*;
import ThreadWorker.ThreadWorker;
import Transport.SocketWorker;
import com.google.gson.Gson;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JDialog;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
/**
 *
 * @author shmelev
 */

public class MainWindowClientIm extends javax.swing.JFrame {
    private ProxyWindow proxyWindow;
    private ThreadWorker threadWrite;
    private volatile int CurrentStatus = ONLINE_STATUS;
    private FriendListModel myListModel;
    private int currentSessionId;
    private ObjectExchange lastReceiveMessage = null;
    JDialog SetNickFrameDialog;
    Gson gson   =   new Gson();
    /**
     * Creates new form MainWindowClientIm
     */
    public MainWindowClientIm() 
    {
        initComponents();
        
        setTitle("IM java messager");
        //CurrentStatus   =   OFFLINE_STATUS;
        MessageTextPane.setEditable(false);
        FriendList.setAutoscrolls(true);
        FriendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        FriendList.removeAll(); 
        proxyWindow    =   new ProxyWindow(this);
        TextMessageField.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {

                if ( e.isControlDown())
                {
                    int code = e.getKeyCode();
                    if (code == 82)
                    {
                        //System.out.println("test4");
                        if (lastReceiveMessage != null)
                        {
                            System.out.println(lastReceiveMessage.friend_id);
                            Friend friend = myListModel.getElementAt(lastReceiveMessage.friend_id);
                            if (friend != null)
                            {
                                //System.out.println("STAVSYA");
                                setNickInTextEditField(friend.nick_name);
                            }
                        }
                    }
                }
            }
        });
        addWindowListener(new WindowAdapter() 
                {
                    @Override
                    public void windowOpened(WindowEvent e) 
                    {
                        initializeThreadWriter();
                    }
                    @Override
                    public void windowClosing(WindowEvent e) 
                    {
                        closeThreadWrite();
                    }
                }
        );
        
        FriendList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               javax.swing.JList list = (javax.swing.JList)e.getSource();
               if (e.getClickCount() == 2) {
                   int index = list.locationToIndex(e.getPoint());
                   if (index != -1)
                   {
                        Friend friend = myListModel.getElementAt(index);
                        setNickInTextEditField(friend.nick_name);
                   }
               } 
            }
        });
        TextMessageField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                     sendMessage();
            }
        });
        System.out.println("FORM CREATED");
        TextMessageField.requestFocusInWindow();
    }
    private void setNickInTextEditField(String nick)
    {
        String textMessage =  TextMessageField.getText();
        TextMessageField.setText("<"+nick+"/> "+textMessage);
        TextMessageField.requestFocusInWindow();
    }
    private String[] parseTextMessage(String message)
    {
        int indexReceiverFirst = message.indexOf('<');
        if (indexReceiverFirst != 0)
        {
            return new String[] {"", message};
        }   
        int indexReceiverLast = message.indexOf("/>");
        if (indexReceiverLast == -1)
        {
            return new String[] {"", message};
        }   
        if(indexReceiverFirst> indexReceiverLast)
        {
            return new String[]{"", message};
        }
        if (indexReceiverLast+2>= message.length())
        {
            return new String[]{message.substring(indexReceiverFirst+1,indexReceiverLast), ""};    
        }
        String receiver = message.substring(indexReceiverFirst+1,indexReceiverLast);
        String textMessage = message.substring(indexReceiverLast+2).trim();
        
        return new String[]{receiver, textMessage};
    }
    public int getCurrentStatus()
    {
        return CurrentStatus;
    }
    public void setThreadWorker(ThreadWorker threadWrite)
    {
        this.threadWrite    =   threadWrite;
    }
    public void setUserStatus(int status)
    {
        CurrentStatus   =   status;
        this.ClientStatusSelector.setSelectedIndex(status);
    }

    
    public void closeThreadWrite()
    {
        if (CurrentStatus != OFFLINE_STATUS)
        {
            threadWrite.closedSession();
        }
    }
    public void initializeThreadWriter()
    {
           
        SocketWorker    socketWorker    =   new SocketWorker("localhost",4444);
        socketWorker.createSocket();
        if (socketWorker.getSocket()==null)
        {
            System.out.println("Неудачная попытка создать сокет. Пока");
            notifyTransportChangeState(false);
            //return false;
        }
        else{
            ThreadWorker  threadWriter       =   new ThreadWorker(socketWorker.getSocket(), proxyWindow);
          
            threadWriter.setPriority(ThreadWorker.NORM_PRIORITY);
            threadWriter.start();
            this.threadWrite= threadWriter;
            //return true;// may be
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        FriendList = new javax.swing.JList();
        TextMessageField = new javax.swing.JTextField();
        SendMessageButton = new javax.swing.JButton();
        ClearMessageHistoryButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        MessageTextPane = new javax.swing.JTextPane();
        ClientStatusSelector = new javax.swing.JComboBox();
        setNickButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        FriendList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(FriendList);

        SendMessageButton.setText("Send");
        SendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendMessageButtonActionPerformed(evt);
            }
        });

        ClearMessageHistoryButton.setText("Clear");
        ClearMessageHistoryButton.setActionCommand("ClearMessageHistory");
        ClearMessageHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearMessageHistoryButtonActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(MessageTextPane);

        ClientStatusSelector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Online", "Invisible", "Offline" }));
        ClientStatusSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientStatusSelectorActionPerformed(evt);
            }
        });

        setNickButton.setText("Set nick");
        setNickButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setNickButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(TextMessageField)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(setNickButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                        .addComponent(ClearMessageHistoryButton)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClientStatusSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ClientStatusSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearMessageHistoryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setNickButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextMessageField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendMessageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setNickButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setNickButtonActionPerformed
        // TODO add your handling code here:
        this.setEnabled(false);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SetNickFrameDialog = SetNickWindow.getInstance(proxyWindow);
                SetNickFrameDialog.setVisible(true);
            }});

    }//GEN-LAST:event_setNickButtonActionPerformed

    private void ClientStatusSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientStatusSelectorActionPerformed
        // TODO add your handling code here:
        int status   = ClientStatusSelector.getSelectedIndex();
        
        
        switch(CurrentStatus)
        {
            case OFFLINE_STATUS:
                if (status !=  OFFLINE_STATUS)
                {   
                    CurrentStatus = status;
                    initializeThreadWriter();
                }
                break;
            default: 
                if (status    ==  OFFLINE_STATUS)    
                {
                    closeThreadWrite();
                    CurrentStatus   =   OFFLINE_STATUS;
                    FriendList.removeAll();       
                }
                else if (CurrentStatus != status)
                {
                    String json = gson.toJson(status);
                    int prevStatus = CurrentStatus;
                    try {
                        threadWrite.sendMessage( new ObjectExchangeWrap(ThreadWorker.OUT_CLIENT_CHANGE_STATUS, json).getObjectExchange());
                        CurrentStatus   =   status;
                    }
                    catch (IOException e){
                        setUserStatus(prevStatus);
                    }    
                }
                break;
        }
        
    }//GEN-LAST:event_ClientStatusSelectorActionPerformed

    private void ClearMessageHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearMessageHistoryButtonActionPerformed

        MessageTextPane.setText("");
    }//GEN-LAST:event_ClearMessageHistoryButtonActionPerformed

    private void SendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendMessageButtonActionPerformed

        sendMessage();
        TextMessageField.requestFocusInWindow();
    }//GEN-LAST:event_SendMessageButtonActionPerformed
    public void setNewNick(String nick)
    {
        String textMessage = gson.toJson(nick);
        this.setEnabled(true);
        try 
        {            
            threadWrite.sendMessage( new ObjectExchangeWrap(ThreadWorker.OUT_FRIEND_CHANGE_NICK, textMessage).getObjectExchange());
            myListModel.changeElementNickName(currentSessionId, nick);
        }
        catch (IOException e)
        {
           //Произошла ошибка отправки 
        }
        
    }
    public void changeFriendNickName(Friend receivefriend)
    {
        myListModel.changeElementNickName(receivefriend.uid, receivefriend.nick_name);

    }
    public void newIncomingMessage(ObjectExchange objectExchange)
    {
        if (objectExchange.message_code == ThreadWorker.IN_PRIVATE_MESSAGE)
        {
            System.out.println("Incoming message");
            lastReceiveMessage = objectExchange;
        }
        
        setNewMessage(objectExchange);
    }
    public void setNewMessage(ObjectExchange objectExchange)
    {
        Document document=  MessageTextPane.getDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(keyWord, Color.BLUE);
        StyleConstants.setBackground(keyWord, Color.WHITE);
        StyleConstants.setBold(keyWord, true);
        String friendName;
        Friend friend; 
        if ( (friend = myListModel.searchElement(objectExchange.friend_id)) != null)
        {            
            friendName  =   friend.nick_name;
        }
        else {
            friendName  =   objectExchange.friend_id+"";
        }
        try{
            SimpleDateFormat    simpleDateFormat=    new SimpleDateFormat("hh:mm:ss");
            String date     =   "("+simpleDateFormat.format(new Date())+") ";
            //StyleConstants.setForeground(keyWord, Color.BLACK);
            StyleConstants.setBackground(keyWord, Color.WHITE);
            document.insertString(document.getLength(), date, null );
            document.insertString(document.getLength(), "<"+ friendName+"> : ", keyWord );
            document.insertString(document.getLength(), objectExchange.message+"\n", null );
        }
        catch (BadLocationException badLocationException)
        {
            
        }
        
    }
    private void sendMessage()
    {
        Document document=  MessageTextPane.getDocument();
        if (CurrentStatus   !=  OFFLINE_STATUS)
        {
            String  textMessage;
            textMessage = TextMessageField.getText().trim();
            String[] messageInfo = parseTextMessage(textMessage);
            System.out.println(messageInfo[0]);
            System.out.println(messageInfo[1]);
            if (!"".equals(messageInfo[1]))
            {
                try {
                    String toName   =   "";
                    boolean is_not_send = false;
                    
                    if (!"".equals(messageInfo[0]))
                    {
                        toName = " to "+ messageInfo[0];
                        Friend friend = myListModel.searchElement(messageInfo[0]);
                        if (friend != null )
                        {
                            threadWrite.sendMessage( new ObjectExchangeWrap(ThreadWorker.OUT_MESSAGE_FOR_FRIEND, messageInfo[1],friend.uid).getObjectExchange());
                        }
                        else
                        {
                            is_not_send = true;
                        }
                    }
                    else
                    {
                        threadWrite.sendMessage( new ObjectExchangeWrap(ThreadWorker.OUT_MESSAGE_FOR_ALL_FRIENDS, messageInfo[1]).getObjectExchange());
                    }
                    if (!is_not_send)
                    {
                        SimpleAttributeSet keyWord = new SimpleAttributeSet();
                        StyleConstants.setBold(keyWord, true);
                        SimpleDateFormat    simpleDateFormat=    new SimpleDateFormat("hh:mm:ss");
                        String date     =   "("+simpleDateFormat.format(new Date())+") ";
                        //StyleConstants.setForeground(keyWord, Color.BLACK);
                        StyleConstants.setBackground(keyWord, Color.WHITE);
                        document.insertString(document.getLength(), date, null );
                        StyleConstants.setForeground(keyWord, Color.GREEN);
                       
                        document.insertString(document.getLength(), "Me"+toName+": ", keyWord );
                        
                        document.insertString(document.getLength(), messageInfo[1]+"\n", null );
                        TextMessageField.setText("");
                    }
                }
                catch (IOException e)
                {
                   //Произошла ошибка отправки 
                }
                catch(BadLocationException exception)
                {
                }               
            }
        }
    }
        
    public void setWindowHeader(ObjectExchange objectExchange)
    {
         this.setTitle("IM java messager #"+ objectExchange.friend_id);
         this.currentSessionId  =   objectExchange.friend_id;

    }
    public void setFriendList(ArrayList<Friend> friendList)
    {
         FriendList.removeAll();
         myListModel    =   new FriendListModel(friendList);
         FriendList.setModel(myListModel);

    }
    public WindowParameter getWindowParameter()
    {
        WindowParameter windowParameter =   new WindowParameter();
        windowParameter.dimension   =   getSize();
        windowParameter.location    =   getLocation();
        return windowParameter;
    }
    public void changeFriendList(Friend friend)
    {
        int index  = myListModel.searchIndexElement(friend);
        if (index == -1 && friend.status != OFFLINE_STATUS)
        {
            myListModel.add(friend);
        }
        else
        {
            if (friend.status == OFFLINE_STATUS)
            {
                myListModel.remove(index);
                return;
            }
            
            if (friend.status == INVISIBLE_STATUS)
            {
                myListModel.remove(index);
            }
        }
       
    }
    public void notifyTransportChangeState(boolean all_right)
    {   int status;
        if (all_right)
        {
            status = CurrentStatus;
        }
        else
        {
            status = OFFLINE_STATUS;
        }
         setUserStatus(status);
    }
            
    private static final int ONLINE_STATUS      =   0;
    private static final int INVISIBLE_STATUS   =   1;
    private static final int OFFLINE_STATUS     =   2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClearMessageHistoryButton;
    private javax.swing.JComboBox ClientStatusSelector;
    private javax.swing.JList FriendList;
    private javax.swing.JTextPane MessageTextPane;
    private javax.swing.JButton SendMessageButton;
    private javax.swing.JTextField TextMessageField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton setNickButton;
    // End of variables declaration//GEN-END:variables
}
