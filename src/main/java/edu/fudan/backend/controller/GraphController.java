package edu.fudan.backend.controller;

import edu.fudan.backend.service.GraphService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
@ResponseBody
@Slf4j
public class GraphController {
    @Autowired
    private GraphService graphService;

    @RequestMapping(value = "/createImg", method = RequestMethod.POST)
    public Map<String, Object> createImg(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        String img = (String) body.get("data");
        String topic = (String) body.get("topic");
        try {
            result.put("status", "ok");
            graphService.saveGraphImg(img, topic);
        } catch (Exception e) {
            log.error("create Image handler error", e);
            result.put("status", "error");
        }
//        String input = img.split(",")[1];
//        String imgFilePath = "D:\\g\\new.png";//新生成的图片
//        OutputStream out = null;
//        try {
//            Base64.Decoder decoder = Base64.getDecoder();
//            byte[] buffer = decoder.decode(input);
//            out = new FileOutputStream(imgFilePath);
//            out.write(buffer);
//            out.flush();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    @RequestMapping(value = "/searchGraph", method = RequestMethod.GET)
    public Map<String, Object> search(@RequestParam("keyword") String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            Map<String, Object> searchResult = graphService.search(keyword);
            result.put("status", "ok");
            result.put("result", searchResult);
        } catch (Exception e) {
            result.put("status", "error");
            log.error("search from elasticsearch error", e);
        }
        return result;
    }
}
