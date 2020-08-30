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

    /**
     * 获得处理列表
     *
     * @return
     */
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
    public Map<String, Object> updateProcess(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = (Integer) body.get("id");
            Integer percent = (Integer) body.get("percent");
            String status = (String) body.get("status");
            String filename = (String) body.get("filename");
            processService.updateProcess(processId, percent, status, filename);
            result.put("status", "ok");
        } catch (Exception e) {
            log.error("finish process error");
            result.put("status", "error");
        }
        return result;
    }

    /**
     * 获取平均处理时间
     *
     * @return
     */
    @RequestMapping(value = "/process_time", method = RequestMethod.GET)
    public String getProcessTime() {
        try {
            return processService.getProcessTime();
        } catch (Exception e) {
            log.error("get Process Time error");
            return "0";
        }
    }

    @RequestMapping(value = "/process_finish_size", method = RequestMethod.GET)
    public String getFinishSize() {
        try {
            return String.valueOf(processService.getProcessFinish());
        } catch (Exception e) {
            log.error("get Finish Size error");
            return "0";
        }
    }
}
