package edu.fudan.backend.service;

import java.util.List;
import java.util.Map;

public interface ProcessService {
    List<Map<String, Object>> getAllProcess() throws Exception;

    void createProcess(Integer documentId) throws Exception;

    void createProcess(List<Integer> documentIdList) throws Exception;

    void startProcess() throws Exception;

    Boolean startProcess(Integer documentId) throws Exception;

    void finishProcess(Integer documentId) throws Exception;
}
