package aisco.blog.core;
import java.util.*;

import vmj.routing.route.VMJExchange;

public interface BlogResource {
    List<HashMap<String,Object>> saveBlog(VMJExchange vmjExchange);
    HashMap<String, Object> updateBlog(VMJExchange vmjExchange);
    HashMap<String, Object> getBlog(VMJExchange vmjExchange);
    List<HashMap<String,Object>> getAllBlog(VMJExchange vmjExchange);
    List<HashMap<String,Object>> deleteBlog(VMJExchange vmjExchange);
}
