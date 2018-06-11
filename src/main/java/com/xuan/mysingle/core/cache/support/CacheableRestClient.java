/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.core.cache.support;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Set;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/23
 */
public class CacheableRestClient implements CacheableRestOperations {
    public static final String CACHE_NAME = "cache.network";
    public static final long CACHE_TTL = 300L;
    private final RestOperations restOperations;

    public CacheableRestClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Cacheable(
            value = {"cache.network"},
            key = "#url",
            condition = "#cacheable == true"
    )
    public <T> T getForObject(String url, Class<T> responseType, boolean cacheable) throws RestClientException {
        return this.restOperations.getForObject(url, responseType, new Object[0]);
    }

    @Cacheable(
            value = {"cache.network"},
            key = "#url",
            condition = "#cacheable == true"
    )
    public HttpHeaders headForHeaders(String url, boolean cacheable) throws RestClientException {
        return this.restOperations.headForHeaders(url, new Object[0]);
    }

    @Cacheable(
            value = {"cache.network"},
            key = "#url",
            condition = "#cacheable == true"
    )
    public Set<HttpMethod> optionsForAllow(String url, boolean cacheable) throws RestClientException {
        return this.restOperations.optionsForAllow(url, new Object[0]);
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType) throws RestClientException {
        return this.restOperations.getForEntity(url, responseType, new Object[0]);
    }

    public URI postForLocation(URI url, Object request) throws RestClientException {
        return this.restOperations.postForLocation(url, request);
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType) throws RestClientException {
        return this.restOperations.postForObject(url, request, responseType, new Object[0]);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType) throws RestClientException {
        return this.restOperations.postForEntity(url, request, responseType, new Object[0]);
    }

    public void put(String url, Object request) throws RestClientException {
        this.restOperations.put(url, request, new Object[0]);
    }

    public void delete(String url) throws RestClientException {
        this.restOperations.delete(url, new Object[0]);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return this.restOperations.exchange(url, method, requestEntity, responseType, new Object[0]);
    }

    public <T> T execute(String url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) throws RestClientException {
        return this.restOperations.execute(url, method, requestCallback, responseExtractor, new Object[0]);
    }

}
