package com.wushiyii.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.wushiyii.exception.DistributeLockException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.message.BasicNameValuePair;

public class HttpClient {

    private CloseableHttpClient httpClient;

    public HttpClient() {
        this(3000);
    }
    public HttpClient(int timeout) {
        try {
            HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {};

            HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory(requestWriterFactory, responseParserFactory);

            Registry<ConnectionSocketFactory> connectionFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .build();

            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();

            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE)
                    .setCharset(Charset.defaultCharset())
                    .setMessageConstraints(messageConstraints)
                    .build();

            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(connectionFactoryRegistry, connectionFactory);

            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true).setSoReuseAddress(true).build();

            connManager.setDefaultSocketConfig(socketConfig);
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(100);
            connManager.setDefaultMaxPerRoute(100);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .setExpectContinueEnabled(true)
                    .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                    .setSocketTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .build();

            this.httpClient = HttpClients
                    .custom()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(requestConfig)
                    .setUserAgent("Lock-Client")
                    .build();
        } catch (Exception e) {
            throw new DistributeLockException(e);
        }
    }

    public void close() throws IOException {
        this.httpClient.close();
    }

    public <T> T putForm(String url, Map<String, String> payload, ResponseHandler<? extends T> responseHandler) throws IOException {
        HttpPut req = new HttpPut(url);

        List<BasicNameValuePair> basicNameValuePairList = payload.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(basicNameValuePairList, StandardCharsets.UTF_8);
        req.setEntity(entity);

        return this.httpClient.execute(req, responseHandler);
    }

    public <T> T deleteForm(String url, Map<String, String> payload, ResponseHandler<? extends T> responseHandler) throws IOException {

        String params = flapParams(payload);
        if (Objects.nonNull(params) && !"".equals(params)) {
            url = url + "?" + params;
        }
        HttpDelete req = new HttpDelete(url);

        return this.httpClient.execute(req, responseHandler);
    }

    private static String flapParams(Map<String, String> params) {
        return params.entrySet().stream().map(entry -> (encode(entry.getKey()) + "=" + encode(entry.getValue()))).collect(Collectors.joining("&"));
    }

    private static String encode(String param) {
        try {
            return URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
