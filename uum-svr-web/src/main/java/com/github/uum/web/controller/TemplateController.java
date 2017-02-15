package com.github.uum.web.controller;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by user on 2016/11/4.
 */
@Controller
@RequestMapping("/")
public class TemplateController {

    @RequestMapping(value = "{fileName}.ftl", method = RequestMethod.GET)
    public String none(@PathVariable String fileName) {
        return fileName;
    }

    @RequestMapping(value = "{dir}/{fileName}.ftl", method = RequestMethod.GET)
    public String account(@PathVariable String dir, @PathVariable String fileName) {
        return Joiner.on("/").join(Lists.newArrayList(dir, fileName));
    }

    @RequestMapping(value = "{dir1}/{dir2}/{fileName}.ftl", method = RequestMethod.GET)
    public String account(@PathVariable String dir1, @PathVariable String dir2, @PathVariable String fileName) {
        return Joiner.on("/").join(Lists.newArrayList(dir1, dir2, fileName));
    }
    /*@RequestMapping(value = "/menu/{path}.ftl", method = RequestMethod.GET)
    public String menu(@PathVariable String path) {
        return "menu/" + path;
    }

    @RequestMapping(value = "/role/{path}.ftl", method = RequestMethod.GET)
    public String role(@PathVariable String path) {
        return "role/" + path;
    }*/
}
