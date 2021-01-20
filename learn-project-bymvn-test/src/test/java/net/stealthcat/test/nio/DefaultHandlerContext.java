package net.stealthcat.test.nio;

/**
 * Created by qianyang on 17-11-13.
 */
public class DefaultHandlerContext implements HandlerContext {

    private HandlerContext before;
    private HandlerContext after;
    private Handler handler;
    private boolean isRead;

    public DefaultHandlerContext(Handler handler) {
        this.handler = handler;
        this.isRead = handler.isRead();
    }

    @Override
    public HandlerContext fireChannelRead(Object message, Client client) {
        return null;
    }

    @Override
    public HandlerContext fireChannelWrite(Object message, Client client) {
        return null;
    }

    @Override
    public Handler handler() {
        return null;
    }

    public HandlerContext getBefore() {
        return before;
    }

    public void setBefore(HandlerContext before) {
        this.before = before;
    }

    public HandlerContext getAfter() {
        return after;
    }

    public void setAfter(HandlerContext after) {
        this.after = after;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
