package com.frizzle.okhttp.frizzleok;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public class FrizzleResponse {
    private String body;
    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String body(){
        return body;
    }
}
