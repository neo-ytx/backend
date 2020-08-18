package edu.fudan.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@ResponseBody
public class ProcessController {

    @RequestMapping("/")
    public Map<String, Object> getAllProcess() {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

    @RequestMapping("/")
    public Map<String, Object> startProcess() {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

}
