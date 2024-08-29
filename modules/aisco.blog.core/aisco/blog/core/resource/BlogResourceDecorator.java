package aisco.blog.core;
import java.util.*;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

public abstract class BlogResourceDecorator extends BlogResourceComponent{
	protected BlogResourceComponent record;

    public BlogResourceDecorator(BlogResourceComponent record) {
        this.record = record;
    }

    public List<HashMap<String,Object>> saveBlog(VMJExchange vmjExchange){
		return record.saveBlog(vmjExchange);
	}

    public Blog createBlog(VMJExchange vmjExchange){
		return record.createBlog(vmjExchange);
	}

    public Blog createBlog(VMJExchange vmjExchange, UUID id){
		return record.createBlog(vmjExchange, id);
	}

    public HashMap<String, Object> updateBlog(VMJExchange vmjExchange){
		return record.updateBlog(vmjExchange);
	}

    public HashMap<String, Object> getBlog(VMJExchange vmjExchange){
		return record.getBlog(vmjExchange);
	}

    public List<HashMap<String,Object>> getAllBlog(VMJExchange vmjExchange){
		return record.getAllBlog(vmjExchange);
	}

    public List<HashMap<String,Object>> transformBlogListToHashMap(List<Blog> blogList){
		return record.transformBlogListToHashMap(blogList);
	}

    public List<HashMap<String,Object>> deleteBlog(VMJExchange vmjExchange){
		return record.deleteBlog(vmjExchange);
	}

}
