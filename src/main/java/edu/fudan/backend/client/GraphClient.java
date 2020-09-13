package edu.fudan.backend.client;

import edu.fudan.backend.config.GraphConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "GraphClient", url = "http://localhost:7474", configuration = GraphConfig.class)
public interface GraphClient {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getInformation();

    @RequestMapping(value = "/db/data/transaction/commit", method = RequestMethod.POST)
    Map<String, Object> search(@RequestBody Map<String, Object> body);
}
