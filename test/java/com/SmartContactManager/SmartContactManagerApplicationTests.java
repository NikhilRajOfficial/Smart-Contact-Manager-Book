package com.SmartContactManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.SmartContactManager.services.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private EmailService emailService;

	 @Test
	 void sendEmailTest()
	 {
		 emailService.sendMail(
			"njha7548@gmail.com", 
			"Test Subject",
		    "Test Body");

	 }

}
