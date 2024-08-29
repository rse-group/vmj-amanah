package aisco.blog.core;

import java.lang.Math;
import java.util.*;
import vmj.routing.route.Route;
import vmj.routing.route.VMJExchange;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity(name="blog_impl")
@Table(name="blog_impl")
public class BlogImpl extends BlogComponent {

	public BlogImpl(UUID id, String title, String content, String image) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.image = image;
	}

	public BlogImpl(String title, String content, String image) {
		this.id =  UUID.randomUUID();
		this.title = title;
		this.content = content;
		this.image = image;
	}

	public BlogImpl() {
		this.id = UUID.randomUUID();
		this.title = "";
		this.content = "";
		this.image = "";
	}


}
