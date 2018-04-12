package net.stealthcat.test.dubbo;

/**
 * Created by qianyang on 18-3-27.
 */
public class ServiceImpl implements IService {

    @Override
    public String service() throws Exception{
        System.out.println("Provider service has been called.");
        throw new RuntimeException();
//        return "service Return.";
    }

}
