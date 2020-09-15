package com.frizzle.okhttp.frizzleok.chain;

import com.frizzle.okhttp.frizzleok.FrizzleCall;
import com.frizzle.okhttp.frizzleok.FrizzleRealCall;
import com.frizzle.okhttp.frizzleok.FrizzleRequest;
import com.frizzle.okhttp.frizzleok.FrizzleResponse;

import java.io.IOException;
import java.util.List;

/**
 * author: LWJ
 * date: 2020/9/15$
 * description
 * 责任点的管理器
 */
public class ChainManager implements FrizzleChain{

    private final List<FrizzleInterceptor> interceptors;
    private final int index;
    private final FrizzleRequest request;
    private final FrizzleRealCall call;

    public List<FrizzleInterceptor> getInterceptors() {
        return interceptors;
    }

    public int getIndex() {
        return index;
    }

    public FrizzleRealCall getCall() {
        return call;
    }

    public ChainManager(List<FrizzleInterceptor> interceptors, int index, FrizzleRequest request, FrizzleRealCall call) {
        this.interceptors = interceptors;
        this.index = index;
        this.request = request;
        this.call = call;
    }

    @Override
    public FrizzleRequest getRequest() {
        return request;
    }

    @Override
    public FrizzleResponse getResponse(FrizzleRequest request) throws IOException {
        if (index >= interceptors.size()) throw new AssertionError();
        if (interceptors.isEmpty()){
            throw new IOException("interceptors参数异常 empty");
        }

        ChainManager nextChain = new ChainManager(interceptors, index + 1, request, call);
        FrizzleInterceptor frizzleInterceptor = interceptors.get(index);
        FrizzleResponse response = frizzleInterceptor.doNext(nextChain);
        return response;
    }
}
