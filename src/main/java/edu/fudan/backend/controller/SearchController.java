package edu.fudan.backend.controller;

import edu.fudan.backend.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/api")
@Slf4j
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Map<String, Object> search(@RequestParam("keyword") String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> searchResult = elasticsearchService.search(keyword);
            result.put("status", "ok");
            result.put("result", searchResult);
        } catch (Exception e) {
            result.put("status", "error");
            log.error("search from elasticsearch error", e);
        }
        return result;
    }
}
