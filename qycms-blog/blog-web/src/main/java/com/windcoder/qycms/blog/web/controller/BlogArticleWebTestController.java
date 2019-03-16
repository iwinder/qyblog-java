package com.windcoder.qycms.blog.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/web/test/articles")
public class BlogArticleWebTestController {

    @RequestMapping("home")
    public String getHome(){

        return "home";
    }
}
