package com.frizzle.okhttp.frizzleok.chain;

import com.frizzle.okhttp.frizzleok.FrizzleResponse;

import java.io.IOException;

/**
 * author: LWJ
 * date: 2020/9/15$
 * description
 */
public interface FrizzleInterceptor {
    FrizzleResponse doNext(FrizzleChain chain) throws IOException;
}
