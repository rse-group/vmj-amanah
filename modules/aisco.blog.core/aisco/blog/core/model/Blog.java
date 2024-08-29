package aisco.blog.core;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import java.util.*;

public interface Blog {
	public UUID getId();
	public void setId(UUID id);
	public String getTitle();
	public void setTitle(String title);
	public String getContent();
	public void setContent(String content);
	public String getImage();
	public void setImage(String image);
	HashMap<String, Object> toHashMap();
}
