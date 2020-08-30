package edu.fudan.backend.service.impl;

import edu.fudan.backend.client.PythonClient;
import edu.fudan.backend.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:39 PM
 */
@Service
public class PythonServiceImpl implements PythonService {
    @Autowired
    private PythonClient pythonClient;

    @Override
    public void createJob(Integer processId, String filename) throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("process_id", processId);
        requestBody.put("filename", filename);
        pythonClient.createJob(requestBody);
    }
}
