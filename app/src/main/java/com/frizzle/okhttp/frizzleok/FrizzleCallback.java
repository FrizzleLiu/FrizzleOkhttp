package com.frizzle.okhttp.frizzleok;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.ResponseBody;

/**
 * author: LWJ
 * date: 2020/9/14$
 * description
 */
public interface FrizzleCallback {
    //失败
    void onFailure(FrizzleCall call, IOException e);
    //成功
    void onResponse(FrizzleCall call, FrizzleResponse response) throws IOException;
}
