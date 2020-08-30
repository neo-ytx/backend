package edu.fudan.backend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "PythonClient", url = "http://localhost:8081")
public interface PythonClient {
    @PostMapping("/job/create")
    String createJob(@RequestBody Map<String, Object> body);
}
