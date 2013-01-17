/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientForm;
import ObjectExchange.*;
import java.util.ArrayList;
/**
 *
 * @author shmelev
 */
public class ProxyWindow
{
    private MainWindowClientIm  window;
    public int getCurrentStatus()
    {
        return window.getCurrentStatus();
    }

    public ProxyWindow(MainWindowClientIm  window)
    {
        this.window  =   window;  
    }
    
    public void setText(final ObjectExchange objectExchange)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.setNewMessage(objectExchange);
            }
        });
    }
    public void setNewNick(final String newName)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.setNewNick(newName);
            }
        });
    }
    
    public void setFriendList(final ArrayList<Friend>  friendList)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.setFriendList(friendList);
            }
        });
    }
    public void changeFriendNickName(final Friend  friend)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.changeFriendNickName(friend);
            }
        });
    }
    
    public void setWindowHeader(final ObjectExchange objectExchange)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.setWindowHeader(objectExchange);
            }
        });
    }
    
    public void changeFriendList(final Friend  friend)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.changeFriendList(friend);
            }
        });
    }
    
    public void setUserStatus(final int status )
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.setUserStatus(status);
            }
        });
    }
    
    public WindowParameter getWindowParameter()
    {
        return window.getWindowParameter();
    }
    public void notifyTransportChangeState(final boolean all_right)
    {
        java.awt.EventQueue.invokeLater(new Runnable(){
            @Override
            public void run()
            {
                window.notifyTransportChangeState(all_right);
            }
        });
    }
}
