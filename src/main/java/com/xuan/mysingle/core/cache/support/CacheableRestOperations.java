package com.xuan.mysingle.core.cache.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.util.Set;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
public interface CacheableRestOperations {
    <T> T getForObject(String var1, Class<T> var2, boolean var3) throws RestClientException;

    <T> ResponseEntity<T> getForEntity(String var1, Class<T> var2) throws RestClientException;

    HttpHeaders headForHeaders(String var1, boolean var2) throws RestClientException;

    URI postForLocation(URI var1, Object var2) throws RestClientException;

    <T> T postForObject(String var1, Object var2, Class<T> var3) throws RestClientException;

    <T> ResponseEntity<T> postForEntity(String var1, Object var2, Class<T> var3) throws RestClientException;

    void put(String var1, Object var2) throws RestClientException;

    void delete(String var1) throws RestClientException;

    Set<HttpMethod> optionsForAllow(String var1, boolean var2) throws RestClientException;

    <T> ResponseEntity<T> exchange(String var1, HttpMethod var2, HttpEntity<?> var3, Class<T> var4) throws RestClientException;

    <T> T execute(String var1, HttpMethod var2, RequestCallback var3, ResponseExtractor<T> var4) throws RestClientException;
}
