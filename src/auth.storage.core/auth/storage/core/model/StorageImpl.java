package auth.storage.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vmj.auth.core.AuthPayload;

public class StorageImpl extends StorageComponent {

	public Map<String, Object> getUserData(AuthPayload payload) {
        Map<String, Object> data = new HashMap<>();
        return data;
    }
	
    public Map<String, List<String>> getRoles(AuthPayload payload) {
        Map<String, List<String>> data = new HashMap<>();
        return data;
    }
}
