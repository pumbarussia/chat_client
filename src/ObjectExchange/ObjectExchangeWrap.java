package ObjectExchange;

/**
 * Created with IntelliJ IDEA.
 * User: forever_yong
 * Date: 06.10.12
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public class ObjectExchangeWrap
{
    private ObjectExchange objectExchange   =   null;

    public ObjectExchangeWrap(int message_code, String message)
    {
        this.objectExchange             =   new ObjectExchange();
        this.objectExchange.message_code     =   message_code;
        this.objectExchange.message          =   message;
    }
    public ObjectExchangeWrap(int message_code, String message, int friend_id)
    {
        this.objectExchange             =   new ObjectExchange();
        this.objectExchange.message_code     =   message_code;
        this.objectExchange.friend_id        =   friend_id;
        this.objectExchange.message          =   message;

    }
    public ObjectExchange getObjectExchange()
    {
        return objectExchange;
    }
}
