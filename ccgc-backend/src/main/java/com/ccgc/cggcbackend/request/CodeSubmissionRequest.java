package com.ccgc.cggcbackend.request;

public class CodeSubmissionRequest {
    private String code;
    private String language;
    private String fileNameHint;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getFileNameHint() { return fileNameHint; }
    public void setFileNameHint(String fileNameHint) { this.fileNameHint = fileNameHint; }
}
