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
   // protected EventListenerList listenerList = new EventListenerList();
    private ArrayList<E> list;
    public  FriendListModel(ArrayList<E> list)
    {
        this.list   =   list;
    }

    @Override
    public int getSize() {
        return list.size();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getElementAt(int index) {
        return list.get(index);
        //throw new UnsupportedOperationException("Not supported yet.");
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
        list.remove(e);
        fireIntervalRemoved(this, index, index);
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
