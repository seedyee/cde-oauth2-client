package io.cde.oauth2.client.unit;

import com.github.scribejava.httpclient.ning.NingHttpClient;
import com.ning.http.client.AsyncHttpClient;

/**
 * Created by liaofangcai.
 * Created time 12/12/16.
 */
public class InitHttpClient {

    public static NingHttpClient getNingHttpClient() {
        final NingHttpClient ningHttpClient = new NingHttpClient(getAsyncHttpClient());
        return ningHttpClient;
    }

    private static AsyncHttpClient getAsyncHttpClient() {
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient ();
        return  asyncHttpClient;
    }
}
