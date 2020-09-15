package com.frizzle.okhttp.frizzleok;

import java.util.HashMap;
import java.util.Map;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public class FrizzleRequest {
    public static final String GET ="GET";
    public static final String POST ="POST";
    private String rquestMethod = GET;
    private String url;
    private Map<String,String> mHeaderParams = new HashMap<>();
    private FrizzleRequestBody requestBody;

    public FrizzleRequestBody getRequestBody() {
        return requestBody;
    }

    public String getRquestMethod() {
        return rquestMethod;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getmHeaderParams() {
        return mHeaderParams;
    }

    public FrizzleRequest() {
        this(new Builder());
    }

    public FrizzleRequest(Builder builder) {
        this.url = builder.url;
        this.rquestMethod =builder.rquestMethod;
        this.mHeaderParams = builder.mHeaderParams;
        this.requestBody = builder.requestBody;
    }

    public static final class Builder{
        private String url;
        private String rquestMethod = GET;
        private Map<String,String> mHeaderParams = new HashMap<>();
        private FrizzleRequestBody requestBody;

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder get(){
            this.rquestMethod = GET;
            return this;
        }

        public Builder post(){
            this.rquestMethod = POST;
            return this;
        }

        public Builder requestBody(FrizzleRequestBody requestBody){
            this.requestBody = requestBody;
            return this;
        }

        public Builder addRequestHead(String key, String value){
            mHeaderParams.put(key,value);
            return this;
        }

        public FrizzleRequest build(){
            return new FrizzleRequest(this);
        }
    }
}
