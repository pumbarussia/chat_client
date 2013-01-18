/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectExchange;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author shmelev
 */
public class FriendListModel<E extends Friend> extends AbstractListModel
{
    private static final long serialVersionUID = 1L;
    private ArrayList<E> list;
    public  FriendListModel(ArrayList<E> list)
    {
        this.list   =   list;
    }

    @Override
    public int getSize() 
    {
        return list.size();
    }

    @Override
    public E getElementAt(int index) 
    {
        E friend=  null;
        try
        {
             friend = list.get(index);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        { 
        }
        return friend;
    }
    
    public void add(E e)
    {
        int index = list.size();
        list.add(e);
        fireIntervalAdded(this, index, index);
    }
    
    public void remove(E e)
    {
        int index = list.indexOf(e);
        if (index != -1)
        {
            list.remove(e);
            fireIntervalRemoved(this, index, index);
        }
    }
    
    public void remove(int index)
    {
        list.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public int searchIndexElement(E element)
    {
        int index   =   -1;
        for (E l: this.list )
        {
            if (l.uid    == element.uid   ) 
            {
                return list.indexOf(l);
            }
        } 
        return index;
    }
    public E searchElement(E element)
    {
        int index = searchIndexElement(element);
        if (index != -1)
        {
            return list.get(index);
        }
        return null;
    }
    public E searchElement(int uid)
    {
        for (E l: this.list )
        {
            if (l.uid    == uid   ) 
            {
                return l;
            }
        }
        return null;
    }
    public E searchElement(String nick)
    {
        //int index = searchIndexElement(element);
        for (E l: this.list )
        {
            if (nick.equals(l.nick_name))
            {
                return l;
            }
        }
        return null;
    }
    public boolean changeElementNickName(int uid, String nameString)
    {
        E element = searchElement(uid);
        if (element != null)
        {
            int index= list.size();
            element.nick_name   =   nameString;
            fireContentsChanged(this, index, index);
            return true;
        }
        return  false;
    }
}
