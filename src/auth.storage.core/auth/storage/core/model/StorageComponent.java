package auth.storage.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vmj.auth.core.AuthPayload;

public abstract class StorageComponent implements Storage {
	
	public abstract Map<String, Object> getUserData(AuthPayload payload);
	
    public abstract Map<String, List<String>> getRoles(AuthPayload payload);
}
