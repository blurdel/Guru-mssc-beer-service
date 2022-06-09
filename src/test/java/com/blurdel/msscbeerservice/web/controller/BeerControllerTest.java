package com.blurdel.msscbeerservice.web.controller;

//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// !!!!! When adding REST docs, the above includes need to be removed in favor of RestDocumentationRequestBuilders below! 
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
//import org.springframework.restdocs.RestDocumentationExtension;
//import org.springframework.restdocs.constraints.ConstraintDescriptions;
//import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.StringUtils;

import com.blurdel.msscbeerservice.bootstrap.BeerLoader;
import com.blurdel.msscbeerservice.services.BeerService;
import com.blurdel.msscbeerservice.web.model.BeerDto;
import com.blurdel.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

//@ExtendWith(RestDocumentationExtension.class)

// Added src/test/resources/org/springframework/restdocs/templates/request-fields.snippet
//This changes values in gen-snippets files down target/generated-snippets/v1/beer/
//@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80) 

@WebMvcTest(BeerController.class)  // NOTE! @WebMvcTest does not bring in the service layer, so need to add @MockBean for services!
//@ComponentScan(basePackages = "com.blurdel.msscbeerservice.web.mappers")
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objMapper;
	
	@MockBean
    BeerService beerService;
	
/*
	@Test
	void getBeerById() throws Exception {

		// This works with fields.withPath() below
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		// Code/template change below adding {beerId} to URL required due to REST docs
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
				
				.param("iscold", "yes") // just testing REST doc
				
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				// added for REST docs
				.andDo(document("v1/beer-get", 
						pathParameters(
								parameterWithName("beerId").description("UUID of desired beer to get.")
						),
						// below not a real param - just testing REST doc
						requestParameters(
								parameterWithName("iscold").description("Is beer cold query param")
						),
						responseFields(
								fields.withPath("id").description("Id of Beer").type(UUID.class),
                                fields.withPath("version").description("Version number").type(String.class),
                                fields.withPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                fields.withPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
                                fields.withPath("beerName").description("Beer Name").type(String.class),
                                fields.withPath("beerStyle").description("Beer Style").type(BeerStyleEnum.class),
                                fields.withPath("upc").description("UPC of Beer").type(String.class),
                                fields.withPath("price").description("Price").type(BigDecimal.class),
                                fields.withPath("quantityOnHand").description("Quantity On hand").type(Integer.class)
						)						
					));
	}
*/
	@Test
    void getBeerById() throws Exception {

		// mockito
        given(beerService.getById(any())).willReturn(getValidBeerDto());

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
	
//	@Test
//	void saveNewBeer() throws Exception {
//
//		BeerDto beerDto = getValidBeerDto();
//		String beerDtoJson = objtMapper.writeValueAsString(beerDto);
//		
//		// This works with fields.withPath() below
//		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
//		
//		mockMvc.perform(post("/api/v1/beer/")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(beerDtoJson))
//				.andExpect(status().isCreated())
//				// added for REST docs
//				.andDo(document("v1/beer-new",
//						requestFields(
//								fields.withPath("id").ignored(),
//                                fields.withPath("version").ignored(),
//                                fields.withPath("createdDate").ignored(),
//                                fields.withPath("lastModifiedDate").ignored(),
//                                fields.withPath("beerName").description("Name of the beer"),
//                                fields.withPath("beerStyle").description("Style of Beer"),
//                                fields.withPath("upc").description("Beer UPC").attributes(),
//                                fields.withPath("price").description("Beer Price"),
//                                fields.withPath("quantityOnHand").ignored()
//						)
//					));
//	}
	
	@Test
	void saveNewBeer() throws Exception {

		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objMapper.writeValueAsString(beerDto);
		
		// mockito
		given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());
				
		mockMvc.perform(post("/api/v1/beer/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isCreated());
	}

	@Test
	void updateBeerById() throws Exception {
		
		// mockito
		given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objMapper.writeValueAsString(beerDto);
		
		mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(beerDtoJson))
				.andExpect(status().isNoContent());
	}
	
	BeerDto getValidBeerDto() {
		return BeerDto.builder()
				.beerName("Nice Beer")
				.beerStyle(BeerStyleEnum.ALE)
				.price(new BigDecimal(8.99))
				.upc(BeerLoader.BEER_1_UPC)
				.build();				
	}

	
	// !!!
	// Added with REST docs testing constraints/templates
//	private static class ConstrainedFields {
//
//        private final ConstraintDescriptions constraintDescriptions;
//
//        ConstrainedFields(Class<?> input) {
//            this.constraintDescriptions = new ConstraintDescriptions(input);
//        }
//
//        private FieldDescriptor withPath(String path) {
//            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
//                    .collectionToDelimitedString(this.constraintDescriptions
//                            .descriptionsForProperty(path), ". ")));
//        }
//    }
	
}
