package org.choongang.file.controllers;

import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 일반 컨트롤러
 */

@Controller
@RequestMapping("/file")
public class FilelController implements ExceptionProcessor {

    @GetMapping("/upload")
    public String upload() {

        return "upload";
    }

}
