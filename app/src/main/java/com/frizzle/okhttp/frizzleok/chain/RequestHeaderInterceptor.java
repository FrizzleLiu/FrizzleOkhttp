package com.frizzle.okhttp.frizzleok.chain;

import com.frizzle.okhttp.frizzleok.FrizzleRealCall;
import com.frizzle.okhttp.frizzleok.FrizzleRequest;
import com.frizzle.okhttp.frizzleok.FrizzleRequestBody;
import com.frizzle.okhttp.frizzleok.FrizzleResponse;
import com.frizzle.okhttp.frizzleok.SocketRequestServer;

import java.io.IOException;
import java.util.Map;

/**
 * author: LWJ
 * date: 2020/9/15$
 * description
 */
public class RequestHeaderInterceptor implements FrizzleInterceptor {
    @Override
    public FrizzleResponse doNext(FrizzleChain chain) throws IOException {
        ChainManager chainManager = (ChainManager) chain;
        FrizzleRequest request = chainManager.getRequest();
        Map<String, String> headerParams = request.getmHeaderParams();
        //Host: restapi.amap.com
        headerParams.put("Host",new SocketRequestServer().getHost(chainManager.getRequest()));
        if ("POST".equalsIgnoreCase(chainManager.getRequest().getRquestMethod())){
            //请求体
            /**
             * Content-Length: 48
             * Content-Type: application/x-www-form-urlencoded
             */
            headerParams.put("Content-Length", request.getRequestBody().getBody().length()+"");
            headerParams.put("Content-Type", FrizzleRequestBody.TYPE);
        }
        return chain.getResponse(request);
    }
}
