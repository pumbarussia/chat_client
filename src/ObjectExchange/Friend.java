/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectExchange;

/**
 *
 * @author shmelev
 */
public class Friend 
{
    /**
     * 
     */
    public int uid;
    public String nick_name;
    public byte status; 
    
    @Override
    public String toString()
    {
        return nick_name;
    }
    
}
