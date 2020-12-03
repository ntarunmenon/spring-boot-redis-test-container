package org.arunm.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	RedisBackedCache redisBackedCache;

	@Test
	void testValueEntry() {
		redisBackedCache.put("hello","there");
		Assertions.assertEquals(redisBackedCache.get("hello"),"there");
	}

}
