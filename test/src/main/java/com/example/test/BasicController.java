package com.example.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BasicController {
  @GetMapping("/")
  @ResponseBody
  public String hi(){
    String tagA = "<a href=\"/about\">about</a>";
    return "hi"+"<br/>"+tagA;
  }
  @GetMapping("/about")
  @ResponseBody
  public String about(){
    return "about";
  }
  @GetMapping("/index")
  public String index(){
    return "index.html";
  }
}
