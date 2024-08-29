package aisco.blog.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.OneToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.CascadeType;
//add other required packages

@MappedSuperclass
public abstract class BlogDecorator extends BlogComponent{
	protected BlogComponent record;
		
	public BlogDecorator (BlogComponent record) {
		this.record = record;
	}

	public BlogDecorator (UUID id, BlogComponent record) {
		this.id =  id;
		this.record = record;
	}
	
	public BlogDecorator(){
		super();
		this.id =  UUID.randomUUID();
	}

	public UUID getId() {
		return record.getId();
	}
	public void setId(UUID id) {
		record.setId(id);
	}
	public String getTitle() {
		return record.getTitle();
	}
	public void setTitle(String title) {
		record.setTitle(title);
	}
	public String getContent() {
		return record.getContent();
	}
	public void setContent(String content) {
		record.setContent(content);
	}
	public String getImage() {
		return record.getImage();
	}
	public void setImage(String image) {
		record.setImage(image);
	}


	public HashMap<String, Object> toHashMap() {
        return this.record.toHashMap();
    }

}
