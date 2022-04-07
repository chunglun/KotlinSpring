package com.clluv.poc

import com.clluv.poc.model.NFT
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.GreaterThan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/*
 * MockMvc --> send requests to the controller
 * ObjectMapper --> convert an object in Kotlin to a JSON string
 * */
@SpringBootTest
@AutoConfigureMockMvc
class KotlinSpringApplicationTests(
	@Autowired val mockMvc: MockMvc, @Autowired val objectMapper: ObjectMapper
) {

	@Test
	fun `Assert NFTs has nft1 as the first item`() {
		mockMvc.get("/nfts")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				jsonPath("$[0].id") { value(1) }
				jsonPath("$[0].name") { value("nft1") }
				jsonPath("$[0].floor_price") { value(100) }
				jsonPath("$.length()") { GreaterThan(1) }
			}
	}

	@Test
	fun `Assert that we can create an NFT`() {
		mockMvc.get("/nfts/6")
			.andExpect {
				status { isNotFound() }
			}

		val newNFT = NFT(0, "nft6", 77.6)
		mockMvc.post("/nfts") {
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writeValueAsString(newNFT)
		}.andExpect {
			status { isCreated() }
			content { contentType(MediaType.APPLICATION_JSON) }
			jsonPath("$.name") { value("nft6") }
			jsonPath("$.floor_price") { value(77.6) }
			jsonPath("$.id") { value(6) }
		}

		mockMvc.get("/nfts/6")
			.andExpect {
				status { isOk() }
				content { contentType(MediaType.APPLICATION_JSON) }
				jsonPath("$.name") { value("nft6") }
				jsonPath("$.floor_price") { value(77.6) }
				jsonPath("$.id") { value(6) }
			}
	}
}