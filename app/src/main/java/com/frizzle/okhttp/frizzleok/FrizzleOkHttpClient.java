package com.frizzle.okhttp.frizzleok;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public class FrizzleOkHttpClient {
    public FrizzleDispatcher frizzleDispatcher;

    private boolean isCancled = false;
    public boolean isCancled() {
        return isCancled;
    }

    public FrizzleOkHttpClient(Builder builder) {
        this.frizzleDispatcher = builder.frizzleDispatcher;
        this.isCancled = builder.isCancled;
    }

    public FrizzleDispatcher dispatcher() {
        return frizzleDispatcher;
    }

    public final static class Builder{
        public FrizzleDispatcher frizzleDispatcher = new FrizzleDispatcher();
        private boolean isCancled = false;

        public FrizzleOkHttpClient build(){
            return new FrizzleOkHttpClient(this);
        }

        public Builder cancled(){
            isCancled = true;
            return this;
        }

        public Builder dispatcher(FrizzleDispatcher frizzleDispatcher){
            this.frizzleDispatcher = frizzleDispatcher;
            return this;
        }
    }

    public FrizzleCall newCall(FrizzleRequest frizzleRequest ){
        //RealCall
        return new FrizzleRealCall(this,frizzleRequest);
    }
}
