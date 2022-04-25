package com.techtalentsouth.techtalentblog.blogposts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogPostController {

	/* Tell springboot to set this automatically via INJECTION */
	@Autowired
	private BlogPostRepository blogPostRepository;
	
	private static List<BlogPost> posts = new ArrayList<>();

	@GetMapping(value = "/blogposts/new")
	public String newBlog(BlogPost post) {
		//post.setAuthor("Morgan Jones");
		return "blogpost/new";
	}
	@GetMapping(value="/")
	public String index(Model model) {
		posts.removeAll(posts);
		Iterable<BlogPost> iterable = blogPostRepository.findAll();
		for( BlogPost post : iterable) {
			posts.add(post);
		}
		model.addAttribute("posts", posts);
		return "blogpost/index";
	}

	@PostMapping(value = "/blogposts")
	public String addNewBlogPost(BlogPost blogPost, Model model) {
		// This method's goal is to save a blogPost entered in the form to a database.
		BlogPost savedBlogPost = blogPostRepository.save(blogPost);
		model.addAttribute("blogPost", savedBlogPost);
		return "blogpost/result";
	}
	
	@GetMapping(value="/blogposts/{id}")
	public String editPostWithid(@PathVariable Long id, Model model) {
		/* use the id to load the blogpost from the database table that has that id
		 * 
		 */
		Optional <BlogPost> post = blogPostRepository.findById(id);
		if(post.isPresent()) {
			BlogPost actualPost = post.get();
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/edit";
	}
	
	@RequestMapping(value="/blogposts/update/{id}") 
	public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
		Optional<BlogPost> post = blogPostRepository.findById(id);
		if (post.isPresent()) {
			BlogPost actualPost = post.get();
			actualPost.setTitle(blogPost.getTitle());
			actualPost.setAuthor(blogPost.getAuthor());
			actualPost.setBlogEntry(blogPost.getBlogEntry());
			blogPostRepository.save(actualPost);
			model.addAttribute("blogPost", actualPost);
		}
		return "blogpost/result";		
	}
	@RequestMapping(value="/blogposts/delete/{id}")
	public String deletePostById(@PathVariable Long id) {
		blogPostRepository.deleteById(id);
		return "blogpost/delete";
	}
}
