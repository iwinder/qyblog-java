package com.windcoder.qycms.system;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AuthorizeApiController {
//    @RequestMapping(value = "api/unauth", produces = "text/html")
//    public String unauthorized(Model model) {
//
//        return "403";
//    }

    @RequestMapping(value = "/unauth")
    @CrossOrigin
    public ResponseEntity<?> unauthorized() {

        //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
