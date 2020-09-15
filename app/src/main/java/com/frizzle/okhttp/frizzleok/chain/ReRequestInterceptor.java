package com.frizzle.okhttp.frizzleok.chain;

import com.frizzle.okhttp.frizzleok.FrizzleOkHttpClient;
import com.frizzle.okhttp.frizzleok.FrizzleRealCall;
import com.frizzle.okhttp.frizzleok.FrizzleResponse;

import java.io.IOException;

/**
 * 重试拦截器
 */
public class ReRequestInterceptor implements FrizzleInterceptor {

    private final String TAG = ReRequestInterceptor.class.getSimpleName();

    @Override
    public FrizzleResponse doNext(FrizzleChain chain2) throws IOException {

        // Log.d(TAG, "我是重试拦截器，执行了");
        System.out.println("我是重试拦截器，执行了");

        ChainManager chainManager = (ChainManager) chain2;

        FrizzleRealCall frizzleRealCall = chainManager.getCall();
        FrizzleOkHttpClient frizzleRealCallOkHttpClient = frizzleRealCall.getOkHttpClient2();
        IOException ioException = null;
        // 重试次数
        if (frizzleRealCallOkHttpClient.getRetryCount() != 0) {
            for (int i = 0; i < frizzleRealCallOkHttpClient.getRetryCount(); i++) { // 3
                try {
                    System.out.println("我是重试拦截器，我要Return Response了");
                    // 如果没有异常，循环就结束了
                    FrizzleResponse frizzleResponse = chain2.getResponse(chainManager.getRequest()); // 执行下一个拦截器（任务节点）
                    return frizzleResponse;
                } catch (IOException e) {
                    // e.printStackTrace();
                    ioException = e;
                }

            }
        }
        // return null;
        throw ioException;
    }
}
