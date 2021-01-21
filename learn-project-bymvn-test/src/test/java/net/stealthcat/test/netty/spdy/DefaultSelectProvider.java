//package net.stealthcat.test.netty.spdy;
//
//import com.google.common.collect.Lists;
//import org.eclipse.jetty.npn.NextProtoNego;
//
//import java.util.List;
//
///**
// * Created by qianyang on 17-11-23.
// */
//public class DefaultSelectProvider implements NextProtoNego.ServerProvider {
//    private static List<String> protocols = Lists.newArrayList("http/1.1", "spdy/3.1");
//
//    private String protocol;
//
//    @Override
//    public void unsupported() {
//        this.protocol = "http/1.1";
//    }
//
//    @Override
//    public List<String> protocols() {
//        return protocols;
//    }
//
//    @Override
//    public void protocolSelected(String s) {
//        this.protocol = s;
//    }
//
//    public String getSelectedProtocol() {
//        return this.protocol;
//    }
//}
