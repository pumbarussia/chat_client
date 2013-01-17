/**
 * Created with IntelliJ IDEA.
 * User: shmelev
 * Date: 02.10.12
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
import ClientForm.MainWindowClientIm;
class WindowTread extends Thread
{
    MainWindowClientIm window;
    public WindowTread(MainWindowClientIm window)
    {
        this.window =   window;
    }
    @Override
    public void run()
    {
        window.setVisible(true);
    }
}

public class ImClient
{
    public static void main(String[] args)
    {
        MainWindowClientIm  window  =   new MainWindowClientIm();
        WindowTread windowThread    =   new WindowTread(window);
        java.awt.EventQueue.invokeLater(windowThread);
        
    }
}
