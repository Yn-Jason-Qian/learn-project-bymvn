package effctiveJava.section1;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Service provider framework
 * 
 */
public class Services {
	
	private Services(){};
	
	private static Map<String, ServiceProvider> providers = new ConcurrentHashMap<>();
	
	private static final String DEFAULT_PROVIDER_NAME = "<def>";
	
	/* 提供者注册api(provider registration api) */
	public static void registerDefaultProvider (ServiceProvider p) {
		registerProvider(DEFAULT_PROVIDER_NAME, p);
	}
	
	public static void registerProvider (String name, ServiceProvider p) {
		providers.put(name, p);
	}
	
	/* 服务访问api(service access api) */
	public static Service newInstance() {
		return newInstance(DEFAULT_PROVIDER_NAME); 
	}
	
	public static Service newInstance(String name) {
		ServiceProvider provider = providers.get(name);
		if(provider == null) 
			throw new IllegalArgumentException("No provider registered with name : " + name);
		return provider.newInstance();
	}
	
	public static void main(String[] args) {
		Services.registerProvider("music", new MusiceServiceProvider());
		Services.newInstance("music").service();
	}
	
	private static class MusicService implements Service {

		@Override
		public void service() {
			System.out.println("One songer is performing");
		}

	}
	
	private static class MusiceServiceProvider implements ServiceProvider {

		@Override
		public Service newInstance() {
			return new MusicService();
		}
	}
}


