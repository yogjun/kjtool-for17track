package com.yogjun.kjtoolfor17track;

import com.yogjun.kjtoolfor17track.api.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * {@link TestController}
 *
 * @author <a href="mailto:280536928@qq.com">yogjun</a>
 * @version ${project.version} - 2024/9/20
 */
@RequestMapping("/test")
@Controller
public class TestController {

  @RequestMapping("")
  public String index(Model model) {
    model.addAttribute("location", new Location("北京", "中国的首都", 39.9042, 116.4074));
    return "test.html";
  }
}
