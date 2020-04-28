package app.forum;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ForumController {
  private final ForumService forumService;

  @GetMapping(path = {"", "/"})
  public String index() {
    return "redirect:/forums";
  }

  @GetMapping("/forums")
  public String getForums(Model model) {
    var forums = forumService.getForums();
    model.addAttribute("forums", ForumView.from(forums));
    return "index";
  }

}
