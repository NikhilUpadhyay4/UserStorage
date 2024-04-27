package com.userstorage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userstorage.modal.FileModel;
import org.bson.json.JsonParseException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class UserStorageApplicationTests {

	private final Logger LOGGER = LoggerFactory.getLogger(UserStorageApplicationTests.class);

	private final String SAVE_API = "/user/{username}/upload";
	private final String GET_API = "/user/{username}/files";

	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@BeforeAll
	private void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}


	@Test
    void a_UploadFile_SuccessfulTest() throws Exception {

		LOGGER.info("Running Upload Test - Successful");
		String title = "Test Title";
		String description = "Test Description";
        String fileName = "test.txt";

        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain",
                "Hello, World!".getBytes());
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.multipart(SAVE_API.replace("{username}", "test"))
                        .file(file) // Add the mock MultipartFile object
                        .param("title", title)
                        .param("description", description)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

	}

	@Test
	void b_getAllFileTest_Successful() throws Exception{
		LOGGER.info("Running Get All File - Successful");

		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult mvcResult =
				mockMvc
						.perform(
								MockMvcRequestBuilders.get(
												GET_API.replace("{username}", "test"))
										.contentType(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
						.andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		List<FileModel> list = objectMapper.readValue(responseContent, new TypeReference<List<FileModel>>() {});
		Assert.assertNotNull(list);

	}


}
