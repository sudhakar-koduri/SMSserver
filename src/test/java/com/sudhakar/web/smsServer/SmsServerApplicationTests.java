package com.sudhakar.web.smsServer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringTestConfiguration.class })
@SelectPackages("com.sudhakar.web.smsServer.service")
@IncludeTags("UnitTest")
@SpringBootTest
public class SmsServerApplicationTests {

	@Test
	public void contextLoads() {
	}
}
