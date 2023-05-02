package com.example.demo;

import com.example.demo.model.RedisInfo;
import com.example.demo.service.RedisService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private RedisService redisService;

	@Test
	void contextLoads() {
	}

	@Test
	void string_redis() {
		String key = "string_redis";
		String value = "string";
		redisService.setStringOps(key, value, 10, TimeUnit.SECONDS);
		log.info("### Redis Key => {} | value => {}", key, redisService.getStringOps(key));
	}

	@Test
	void hash_redis() {

		String key = "hash_redis";
		HashMap<String, String> map = new HashMap<>();
		String mapKeyOne = "hash_key_1";
		String mapKeyTwo = "hash_key_2";

		map.put(mapKeyOne, "hash_value_1");
		map.put(mapKeyTwo, "hash_value_2");

		redisService.setHashOps(key, map);
		log.error("### Redis One Key => {} | value => {}", key, redisService.getHashOps(key, mapKeyOne));
		log.error("### Redis Two Key => {} | value => {}", key, redisService.getHashOps(key, mapKeyTwo));

//		String keyEty = "key_empty";
//		log.error("### Redis Empty hash Key => {} | value => {}", keyEty, redisService.getHashOps(key, keyEty));
//		log.error("### Redis Empty Key => {} | value => {}", keyEty, redisService.getHashOps(keyEty, mapKeyOne));
	}

	@Test
	void sortedSet_redis() {
		String key = "sortedSet_redis_test";

		List<ZSetOperations.TypedTuple<String>> values = new ArrayList<>();
		values.add(new DefaultTypedTuple<>("member1", 1.0));
		values.add(new DefaultTypedTuple<>("member2", 2.0));
		values.add(new DefaultTypedTuple<>("member3", 4.0));

		redisService.setSortedSetOps(key, values);

		Set range = redisService.getSortedSetOps(key);
		log.info("### Redis Sorted Set => {}", range);

	}


	@Test
	void test() {
		while (true) {
			try {
				Thread.sleep(5000);

				RedisInfo redisInfo = new RedisInfo();
				redisInfo.setKey("key");
				redisInfo.setValue("hello_3");

				MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
				map.add("key", "hello3");

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("key","key");
				jsonObject.put("value","value");

				RestTemplate restTemplate = new RestTemplate();
				String url = "http://localhost:8080/register";

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
				String answer = restTemplate.postForObject(url, entity, String.class);

				log.info(answer);

			} catch (Exception e) {
				log.error("### 테스트 에러 발생 => {}", e.getMessage());
			}
		}
	}
}
