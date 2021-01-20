package jmx.server;

import jmx.service.MapParamService;

import javax.management.*;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
public class JmxServer {

	public static void main(String[] args) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, MalformedObjectNameException {
		MBeanServer server = MBeanServerFactory.createMBeanServer();

		server.registerMBean(new MapParamService(), new ObjectName("service", "name", "MapParamService"));
		try {
			JMXServiceURL serviceURL = new JMXServiceURL("rmi", null, 8080);
			System.out.println(serviceURL);
			JMXConnectorServerFactory.newJMXConnectorServer(serviceURL, null, server).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		serverAbc.registerMBean(new SimpleServie(), new ObjectName("service", "name", "SimpleService"));
	}
	
}
