package jmx.service;

import java.util.Map;
import java.util.Map.Entry;

public class MapParamService implements MapParamServiceMBean{

	@Override
	public String getMessage(Map<String, String> params) {
		StringBuilder builder = new StringBuilder();
		if(params != null) {
			for(Entry<String, String> entry : params.entrySet()) {
				builder.append(entry.getKey() + ":" + entry.getValue() + "\n");
			}
		}
		return builder.toString();
	}

}
