package aisco.blog.core;

import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="blog_comp")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BlogComponent implements Blog{
	@Id
	protected UUID id; 
	@Lob
	protected String title;
	@Lob
	protected String content;
	@Lob
	protected String image;

	public BlogComponent() {

	} 

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}
 

	@Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            " title='" + getTitle() + "'" +
            " content='" + getContent() + "'" +
            " image='" + getImage() + "'" +
            "}";
    }
	
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> blogMap = new HashMap<String,Object>();
		blogMap.put("id",getId());
		blogMap.put("title",getTitle());
		blogMap.put("content",getContent());
		blogMap.put("image",getImage());

        return blogMap;
    }
}
