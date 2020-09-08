package edu.fudan.backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "GoodClient", url = "https://nlp.zhiwenben.com")
public interface GoodClient {
    @PostMapping("/api/ie/event_triple")
    Map<String, Object> getTriple(@RequestBody Map<String, Object> body);
}
