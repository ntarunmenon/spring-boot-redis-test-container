package org.arunm.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {DemoApplicationTests.Initializer.class})
class DemoApplicationTests {

	@Autowired
	RedisBackedCache redisBackedCache;

	@Container
	public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
			.withExposedPorts(6379);

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.redis.host=" + redis.getHost(),
					"spring.redis.port=" + redis.getFirstMappedPort()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}


	@Test
	void testValueEntry() {
		redisBackedCache.put("hello","there");
		Assertions.assertEquals(redisBackedCache.get("hello"),"there");
	}

}
