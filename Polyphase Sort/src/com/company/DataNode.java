package com.company;

public class DataNode {
    private String value;
    private String fileName;
    public DataNode(String value, String fileName){
        this.value = value;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) { this.value = value; }
}
