package edu.fudan.backend.service;

/**
 * @Author tyuan@ea.com
 * @Date 8/11/2020 3:39 PM
 */
public interface PythonService {
    void createJob(Integer processId, String filename) throws Exception;

    void createGoodJob(Integer processId, String filename) throws Exception;
}
