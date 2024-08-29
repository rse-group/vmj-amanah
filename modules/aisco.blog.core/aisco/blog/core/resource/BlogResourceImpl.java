package aisco.blog.core;
import java.util.*;
import java.lang.RuntimeException;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import vmj.routing.route.exceptions.*;

import vmj.hibernate.integrator.RepositoryUtil;

import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;
import aisco.blog.BlogFactory;
import vmj.auth.annotations.Restricted;
//add other required packages

public class BlogResourceImpl extends BlogResourceComponent{

	@Restricted(permissionName = "CreateBlog")
    @Route(url="call/blog/save")
    public List<HashMap<String,Object>> saveBlog(VMJExchange vmjExchange){
    	System.out.println("here");
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Blog blog = createBlog(vmjExchange);
		blogRepository.saveObject(blog);
		return getAllBlog(vmjExchange);
	}

    public Blog createBlog(VMJExchange vmjExchange){
		return createBlog(vmjExchange, UUID.randomUUID());
	}

    public Blog createBlog(VMJExchange vmjExchange, UUID id){
    	System.out.println("Testing create endpoint");
    	Map<String, Object> payload = vmjExchange.getPayload();
    	System.out.println(payload);
		String title = (String) payload.get("title");
    	System.out.println("berhasil convert title");
		String content = (String) payload.get("content");
		String image = "";
		System.out.println(title);
		System.out.println(content);
		System.out.println(image);
		System.out.println((payload.get("image")).getClass().getName());
		//to do: fix association attributes
		
        Map<String, byte[]> uploadedFile = (HashMap<String, byte[]>) payload.get("image");
        image = "data:" + (new String(uploadedFile.get("type"))).split(" ")[1].replaceAll("\\s+", "")
                + ";base64," + Base64.getEncoder().encodeToString(uploadedFile.get("content"));
        int fileSize = uploadedFile.get("content").length;
        if (fileSize > 4000000)
            throw new FileSizeException(4.0, ((double) fileSize) / 1000000, "megabyte");
        try {
            String type = URLConnection
                    .guessContentTypeFromStream(new ByteArrayInputStream(uploadedFile.get("content")));
            if (type == null || !type.startsWith("image"))
                throw new FileTypeException("image");
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
		
		Blog blog = BlogFactory.createBlog("aisco.blog.core.BlogImpl", id, title, content, image);
			return blog;
	}

    @Restricted(permissionName = "UpdateBlog")
    @Route(url="call/blog/update")
    public HashMap<String, Object> updateBlog(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		System.out.println(idStr);
		UUID id = UUID.fromString(idStr);
		
		Blog blog = blogRepository.getObject(id);
		blog = createBlog(vmjExchange, id);
		blogRepository.updateObject(blog);
		//to do: fix association attributes
		
		return blog.toHashMap();
		
	}

	// @Restriced(permission = "")
    @Route(url="call/blog/detail")
    public HashMap<String, Object> getBlog(VMJExchange vmjExchange){
		String idStr = vmjExchange.getGETParam("id"); 
		UUID id = UUID.fromString(idStr);
		Blog blog = blogRepository.getObject(id);
		try {
			return blog.toHashMap();
		} catch (NullPointerException e) {
			HashMap<String, Object> blank = new HashMap<>();
			blank.put("error", "Missing Params");
			return blank;
		}
	}

	// @Restriced(permission = "")
    @Route(url="call/blog/list")
    public List<HashMap<String,Object>> getAllBlog(VMJExchange vmjExchange){
		List<Blog> blogList = blogRepository.getAllObject("blog_impl");
		return transformBlogListToHashMap(blogList);
	}

    public List<HashMap<String,Object>> transformBlogListToHashMap(List<Blog> blogList){
		List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
        for(int i = 0; i < blogList.size(); i++) {
            resultList.add(blogList.get(i).toHashMap());
        }

        return resultList;
	}

	@Restricted(permissionName = "DeleteBlog")
    @Route(url="call/blog/delete")
    public List<HashMap<String,Object>> deleteBlog(VMJExchange vmjExchange){
		if (vmjExchange.getHttpMethod().equals("OPTIONS")) {
			return null;
		}
		
		Map<String, Object> payload = vmjExchange.getPayload();
		String idStr = (String) payload.get("id");
		UUID id = UUID.fromString(idStr);
		blogRepository.deleteObject(id);
		return getAllBlog(vmjExchange);
	}

}
