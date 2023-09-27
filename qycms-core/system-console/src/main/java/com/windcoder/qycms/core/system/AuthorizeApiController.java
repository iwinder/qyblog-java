package com.windcoder.qycms.core.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthorizeApiController {
//    @RequestMapping(value = "api/unauth", produces = "text/html")
//    public String unauthorized(Model model) {
//
//        return "403";
//    }

    @RequestMapping(value = "api/unauth")
    @ResponseBody
    public ResponseEntity<?> unauthorized() {

        //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
