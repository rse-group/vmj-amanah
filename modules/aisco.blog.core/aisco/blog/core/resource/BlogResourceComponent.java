package aisco.blog.core;
import java.util.*;

import vmj.hibernate.integrator.RepositoryUtil;
import vmj.routing.route.VMJExchange;
//add other required packages

public abstract class BlogResourceComponent implements BlogResource{
	protected RepositoryUtil<Blog> blogRepository;

    public BlogResourceComponent(){
        this.blogRepository = new RepositoryUtil<Blog>(aisco.blog.core.BlogComponent.class);
    }	

    public abstract List<HashMap<String,Object>> saveBlog(VMJExchange vmjExchange);
    public abstract Blog createBlog(VMJExchange vmjExchange);
	public abstract Blog createBlog(VMJExchange vmjExchange, UUID id);    
	public abstract HashMap<String, Object> updateBlog(VMJExchange vmjExchange);
    public abstract HashMap<String, Object> getBlog(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> getAllBlog(VMJExchange vmjExchange);
    public abstract List<HashMap<String,Object>> transformBlogListToHashMap(List<Blog> BlogList);
    public abstract List<HashMap<String,Object>> deleteBlog(VMJExchange vmjExchange);

}
