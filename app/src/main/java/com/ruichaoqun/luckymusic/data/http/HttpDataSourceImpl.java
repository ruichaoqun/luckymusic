package com.ruichaoqun.luckymusic.data.http;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Rui Chaoqun
 * @date :2019/11/28 11:48
 * description:
 */
@Singleton
public class HttpDataSourceImpl implements HttpDataSource {
    private ApiService mApiService;

    @Inject
    public HttpDataSourceImpl(ApiService mApiService) {
    }
}
