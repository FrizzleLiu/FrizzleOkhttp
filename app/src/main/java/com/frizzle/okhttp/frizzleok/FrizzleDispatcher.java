package com.frizzle.okhttp.frizzleok;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public class FrizzleDispatcher {
    private int maxRequests = 64;
    private int maxRequestsPerHost = 5;
    private Deque<FrizzleRealCall.FrizzleAsyncCall> runningAsyncCalls = new ArrayDeque<>();
    private Deque<FrizzleRealCall.FrizzleAsyncCall> readyAsyncCalls = new ArrayDeque<>();

    public void enqueue(FrizzleRealCall.FrizzleAsyncCall frizzleAsyncCall) {
        //同时运行的队列数 必须小于64 同时访问同一ip的数量不能超过5
        if (runningAsyncCalls.size() < maxRequests && runningCallsForHost(frizzleAsyncCall) < maxRequestsPerHost) {
            runningAsyncCalls.add(frizzleAsyncCall); //把任务加入到运行队列
            executorService().execute(frizzleAsyncCall);
        } else {
            readyAsyncCalls.add(frizzleAsyncCall);
        }
    }

    private ExecutorService executorService() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS
                , new SynchronousQueue<Runnable>() {
        }
                , new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("Frizzle线程...");
                thread.setDaemon(false);
                return thread;
            }
        });
    }

    /**
     * @param frizzleAsyncCall
     * @return 返回运行队列中Host同相同数量
     */
    private int runningCallsForHost(FrizzleRealCall.FrizzleAsyncCall frizzleAsyncCall) {
        int count = 0;
        if (runningAsyncCalls.isEmpty()) {
            return 0;
        }
        SocketRequestServer srs = new SocketRequestServer();
        for (FrizzleRealCall.FrizzleAsyncCall call : runningAsyncCalls) {
            if (srs.getHost(call.getRequest()).equals(srs.getHost(frizzleAsyncCall.getRequest()))) {
                count++;
            }
        }
        return count;
    }

    /**
     * @param frizzleAsyncCall 回收资源
     */
    public void finish(FrizzleRealCall.FrizzleAsyncCall frizzleAsyncCall) {
        runningAsyncCalls.remove(frizzleAsyncCall);
        //考虑等待队列是否有任务
        if (readyAsyncCalls.isEmpty()) {
            return;
        }
        //把等待中的队列移动到运行队列
        for (FrizzleRealCall.FrizzleAsyncCall readyCall : readyAsyncCalls) {
            readyAsyncCalls.remove(readyCall);
            runningAsyncCalls.add(readyCall);
            //开始执行
            executorService().execute(readyCall);
        }
    }
}
