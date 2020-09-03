package com.baselibrary.activity.util;

/**
 * Incremental change is better than ambitious failure.
 *
 * @author : <a href="http://mysticcoder.coding.me/myBlog">MysticCoder</a>
 * @date : 2017/12/7
 * @desc :
 */


public class ResultException extends RuntimeException {


    private int code;
    private String codeStr;
    private String message;
    private String json;

    public ResultException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultException(String codeStr, String message) {
        this.codeStr = codeStr;
        this.message = message;
    }

    public ResultException(int code, String codeStr, String message) {
        this.code = code;
        this.codeStr = codeStr;
        this.message = message;
    }

    public ResultException(String codeStr, String message, String json) {
        this.codeStr = codeStr;
        this.message = message;
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
