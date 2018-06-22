package us.cartisan.core.util.http;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hyungkook Kim
 *
 */
@Slf4j
public class HttpClient {
	private RestTemplate restTemplate;
	
	public HttpClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public  <T> T get(String url, HttpHeaders headers, Map<String, String> paramMap, ParameterizedTypeReference<T> type) {
		// 헤더
		if (headers == null) {
			headers = new HttpHeaders();
		}
		
		// URL 
		if (paramMap == null) {
			paramMap = Maps.newHashMap();
		}
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (String key : paramMap.keySet()) {
			builder.queryParam(key, paramMap.get(key));
		}
		
		// 요청
		ResponseEntity<T> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange( //
				builder.build().encode().toUri(), //
				HttpMethod.GET, //
				new HttpEntity<Object>(headers), //
				type);
		} catch (Exception e) {
			log.error("get({}, {}, {}, {}) is failed. => Error : {}", url, headers, paramMap, type,e);
		}
		return getResponse(responseEntity);
	}
	
	public <T> T postWithRawBody(String url, HttpHeaders headers, String bodyText, ParameterizedTypeReference<T> type) {
		// 헤더
		if (headers == null) {
			headers = new HttpHeaders();
		}
		
		// 요청
		ResponseEntity<T> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange( //
				url, //
				HttpMethod.POST, //
				new HttpEntity<Object>(bodyText, headers), //
				type);
		} catch (Exception e) {
			log.error("postWithRawBody({}, {}, {}, {}) is failed. => Error : {}", url, headers, bodyText, type, e);
		}
		return getResponse(responseEntity);
	}
	
	public <T> T postWithMultipartFormBody(String url, HttpHeaders headers, MultiValueMap<String, Object> bodyMap, ParameterizedTypeReference<T> type) {
		// 헤더
		if (headers == null) {
			headers = new HttpHeaders();
		}
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		// 요청
		ResponseEntity<T> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange( //
				url, //
				HttpMethod.POST, //
				new HttpEntity<Object>(bodyMap, headers), //
				type);
		} catch (Exception e) {
			log.error("postWithMultipartFormBody({}, {}, {}, {}) is failed. => Error : {}", url, headers, bodyMap, type, e);
		}
		return getResponse(responseEntity);
	}
	
	private <T> T getResponse(ResponseEntity<T> responseEntity) {
		if (responseEntity != null) {
			if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
				return responseEntity.getBody();
			}
		}
		return null;
	}
}
