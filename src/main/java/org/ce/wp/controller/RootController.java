package org.ce.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
@Controller
@RequestMapping(path = "/")
public class RootController {

    @GetMapping
    public String root() {
        return "main.html";
    }

    @GetMapping("/desktop")
    public String desktop() {
        return "desktop.html";
    }
}
