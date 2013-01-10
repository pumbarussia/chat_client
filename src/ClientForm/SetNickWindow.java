/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientForm;

import ObjectExchange.WindowParameter;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowStateListener;

import java.awt.event.WindowEvent;
import javax.swing.*;
/**
 *
 * @author shmelev
 */
public class SetNickWindow extends javax.swing.JDialog
{
    private JTextField textField;
    private JButton buttonOk;
    private final ProxyWindow proxyMainWindow;
//    private javax.swing.JFrame instance;
    private static SetNickWindow instance = null;
    private int sizeX =  250;
    private int sizeY =  70;
    private SetNickWindow(final ProxyWindow proxyMainWindow)
    {
    
        setTitle("Set nick");
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize ();
        this.proxyMainWindow =   proxyMainWindow;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2,5,10));
        textField    =   new JTextField();
        panel.add(textField);
        buttonOk  =   new JButton("ok");
        panel.add(buttonOk);
        setContentPane(panel);
        setSize(250, 70);
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });
        
        this.addComponentListener(new ComponentAdapter( ) {

            @Override
            public void componentShown(ComponentEvent e) {
                /* code run when component shown */
                WindowParameter mainWindowParameter = proxyMainWindow.getWindowParameter();
                setLocation((int)(mainWindowParameter.location.x+(mainWindowParameter.dimension.width/2)- sizeX/2),
                         (int)(mainWindowParameter.location.y+(mainWindowParameter.dimension.height/2)-sizeY/2));
            }
            
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public static SetNickWindow getInstance (ProxyWindow proxyMainWindow)
    {
        if (instance == null)
        {
            instance    =   new SetNickWindow(proxyMainWindow);
        }
        return instance;
        
    }
    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String newNick = textField.getText();
        if (!"".equals(newNick.trim()))
        {
           proxyMainWindow.setNewNick(newNick);
           this.setVisible(false);
        }
        
    }
}
