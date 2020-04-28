package app.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PostController {
  private final PostService postService;

  @GetMapping(path = "/forums/{forumId}/posts")
  String getPostsByForum(@PathVariable Long forumId, Model model) {
    var posts = postService.getPostsByForumId(forumId);
    model.addAttribute("posts", PostView.from(posts));
    return "posts";

  }
}
