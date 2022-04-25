package com.techtalentsouth.techtalentblog.blogposts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost,Long>{
		
}
