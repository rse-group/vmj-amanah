package auth.storage.core;

import java.util.List;
import java.util.Map;

import vmj.auth.core.AuthPayload;

public abstract class StorageDecorator extends StorageComponent {
	
	public StorageComponent storage;
	
	public StorageDecorator(StorageComponent storage) {
        this.storage = storage;
    }

    public StorageDecorator() {
        this.storage = new StorageImpl();
    }
    
    public StorageComponent getStorage() {
        return this.storage;
    }

    public void setStorage(StorageComponent storage) {
        this.storage = storage;
    }
}
