package com.research.assistant;


import lombok.Data;

//this class is used to make a format for request
@Data
public class ResearchRequest {
    private String content;
    private String operation;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }



}
