package com.frizzle.okhttp.frizzleok.chain;

import com.frizzle.okhttp.frizzleok.FrizzleRequest;
import com.frizzle.okhttp.frizzleok.FrizzleResponse;

import java.io.IOException;

/**
 * author: LWJ
 * date: 2020/9/15$
 * description
 */
public interface FrizzleChain {
    FrizzleRequest getRequest();

    FrizzleResponse getResponse(FrizzleRequest request) throws IOException;
}
