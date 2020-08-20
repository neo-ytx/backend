package edu.fudan.backend.controller;

import edu.fudan.backend.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@ResponseBody
@Slf4j
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @RequestMapping(value = "/process_list", method = RequestMethod.GET)
    public List<Map<String, Object>> getAllProcess() {
        try {
            return processService.getAllProcess();
        } catch (Exception e) {
            log.error("get process error!");
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/process_list", method = RequestMethod.POST)
    public Map<String, Object> createProcess(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            processService.createProcess((List<Integer>) body.get("key"));
            result.put("status", "ok");
        } catch (Exception e) {
            log.error("create process error");
            result.put("status", "error");
        }
        return result;
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public Map<String, Object> finishProcess(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            processService.finishProcess((Integer) body.get("id"));
            result.put("status", "ok");
        } catch (Exception e) {
            log.error("finish process error");
            result.put("status", "error");
        }
        return result;
    }
}
