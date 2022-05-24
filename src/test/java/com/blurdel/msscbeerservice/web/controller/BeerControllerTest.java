package com.blurdel.msscbeerservice.web.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// !!!!! When adding REST docs, the above includes need to be removed in favor of RestDocumentationRequestBuilders below! 
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.blurdel.msscbeerservice.web.model.BeerDto;
import com.blurdel.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.blurdel.msscbeerservice.web.mappers")
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objtMapper;
	
	@Test
	void getBeerById() throws Exception {

		// Code/template change below adding {beerId} to URL required due to REST docs
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
				
				.param("iscold", "yes") // just testing REST doc
				
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// added for REST docs
				.andDo(document("v1/beer", 
						pathParameters(
								parameterWithName("beerId").description("UUID os desired beer to get.")
						),
						// below not a real param - just testing REST doc
						requestParameters(
								parameterWithName("iscold").description("Is beer cold query param")
						),
						responseFields(
								fieldWithPath("id").description("Id of Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price"),
                                fieldWithPath("quantityOnHand").description("Quantity On hand")
						)						
					));
	}

	@Test
	void saveNewBeer() throws Exception {

		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objtMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated())
						
				.andDo(document("v1/beer",
						requestFields(
								fieldWithPath("id").ignored(),
                                fieldWithPath("version").ignored(),
                                fieldWithPath("createdDate").ignored(),
                                fieldWithPath("lastModifiedDate").ignored(),
                                fieldWithPath("beerName").description("Name of the beer"),
                                fieldWithPath("beerStyle").description("Style of Beer"),
                                fieldWithPath("upc").description("Beer UPC").attributes(),
                                fieldWithPath("price").description("Beer Price"),
                                fieldWithPath("quantityOnHand").ignored()
						)
					));
	}

	@Test
	void updateBeerById() throws Exception {

		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objtMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isNoContent());
	}
	
	BeerDto getValidBeerDto() {
		return BeerDto.builder()
				.beerName("My Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal(2.99))
				.upc(123123123123L)
				.build();				
	}

}
