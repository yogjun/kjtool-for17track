package com.yogjun.kjtoolfor17track;

import com.yogjun.kjtoolfor17track.api.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link DefaultController}
 *
 * @author <a href="mailto:280536928@qq.com">yogjun</a>
 * @version ${project.version} - 2024/9/20
 */
@RequestMapping("")
@Controller
public class DefaultController {

  @RequestMapping("")
  public String index(Model model) {
    return "index.html";
  }
}
