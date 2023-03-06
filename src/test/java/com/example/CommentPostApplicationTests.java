package com.example;

import com.example.dto.ContactDTO;
import com.example.service.ContactService;
import com.example.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentPostApplicationTests {

	@Autowired
	private ContactService contactService;

	@Autowired
	private ProfileService profileService;

	@Test
	void contextLoads() {
//		ContactDTO contactByProfileId = contactService.getContactByProfileId(2);
//		contactService.getContactByProfileEntity(4);
	}

	@Test
	void something(){
		profileService.getProfileByContactEntity(4);
	}
}
