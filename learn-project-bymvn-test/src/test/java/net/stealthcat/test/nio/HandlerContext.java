package net.stealthcat.test.nio;

/**
 * Created by qianyang on 17-11-13.
 */
public interface HandlerContext {

    HandlerContext fireChannelRead(Object message, Client client);

    HandlerContext fireChannelWrite(Object message, Client client);

    Handler handler();

    public HandlerContext getBefore();

    public void setBefore(HandlerContext before);

    public HandlerContext getAfter();

    public void setAfter(HandlerContext after);
}
