package com.blurdel.msscbeerservice.services.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blurdel.msscbeerservice.bootstrap.BeerLoader;


//@Disabled // NOTE: Tests require beer-inventory-service to be running
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

	@Autowired
	BeerInventoryService service;
	
	
	@BeforeEach
	void setup() {
	}
	
	@Test
	void getOnhandInventory() {
		Integer qoh = service.getOnhandInventory(BeerLoader.BEER_1_UUID);
		System.out.println("qoh=" + qoh);
	}

}
