/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientForm;

import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author shmelev
 */
public class SetNickWindow extends javax.swing.JFrame
{
    private JTextField textField;
    private JButton buttonOk;
    private ProxyWindow proxyMainWindow;
//    private javax.swing.JFrame instance;
    private static SetNickWindow instance = null;
    private SetNickWindow(ProxyWindow proxyMainWindow)
    {
        
        super("Set nick");
        this.proxyMainWindow =   proxyMainWindow;
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2,5,10));
        
        buttonOk  =   new JButton("ok");
        panel.add(buttonOk);
        textField    =   new JTextField();
        panel.add(textField);
        setContentPane(panel);
        setSize(250, 100);
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });
        
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
           this.dispose();
        }
        
    }
}
