package edu.fudan.backend.service;

public interface ProcessService {
    void createProcess(Integer documentId) throws Exception;

    void startProcess() throws Exception;

    Boolean startProcess(Integer documentId) throws Exception;

    void finishProcess(Integer documentId) throws Exception;
}
