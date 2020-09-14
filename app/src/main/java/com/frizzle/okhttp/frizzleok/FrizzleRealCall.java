package com.frizzle.okhttp.frizzleok;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public class FrizzleRealCall implements FrizzleCall{

    private FrizzleOkHttpClient frizzleOkHttpClient;
    private FrizzleRequest frizzleRequest;
    private boolean executed;

    public FrizzleRealCall(FrizzleOkHttpClient frizzleOkHttpClient,FrizzleRequest frizzleRequest) {
        this.frizzleOkHttpClient = frizzleOkHttpClient;
        this.frizzleRequest = frizzleRequest;
    }

    //异步
    @Override
    public void enqueue(FrizzleCallback responseCallback) {
        synchronized (this){
            //不能被重复执行
            if (executed){
                executed = true;
                throw new IllegalStateException("Already Executed");
            }

            frizzleOkHttpClient.dispatcher().enqueue(new FrizzleAsyncCall(responseCallback));
        }
    }

    final class FrizzleAsyncCall implements Runnable{
        private FrizzleCallback responseCallback;
        public FrizzleAsyncCall(FrizzleCallback responseCallback) {
            this.responseCallback=responseCallback;
        }

        @Override
        public void run() {
            boolean signalledCallback = false;
            try {
                //如果这行代码执行发生异常 signalledCallback = false 这里的try/catch是为了下面的责任划分
                FrizzleResponse response = getResponseWithInterceptorChain();
                if (frizzleOkHttpClient.isCancled()){
                    signalledCallback = true;
                    responseCallback.onFailure(FrizzleRealCall.this, new IOException("Canceled"));
                }else {
                    signalledCallback = true;
                    responseCallback.onResponse(FrizzleRealCall.this,response);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //责任划分,true表示用户取消或使用过程中发生异常,反之Okhttp内部发生异常
                if (signalledCallback){
                    Log.e("Frizzle","用户取消,或使用中发生异常");
                }else {
                    Log.e("Frizzle","Okhttp getResponseWithInterceptorChain发生错误..");
                }
            }finally {
                frizzleOkHttpClient.dispatcher().finish(this);
            }
        }

        public FrizzleRequest getRequest() {
            return FrizzleRealCall.this.frizzleRequest;
        }

        /**
         * @return 责任链模式的拦截器
         * @throws IOException
         */
        private FrizzleResponse getResponseWithInterceptorChain() throws IOException {
            FrizzleResponse response = new FrizzleResponse();
            response.setBody("流程走通...");
            return response;
        }
    }

}
