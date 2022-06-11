package com.blurdel.msscbeerservice.web.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.services.inventory.BeerInventoryService;
import com.blurdel.msscbeerservice.web.model.BeerDto;


public abstract class BeerMapperDecorator implements BeerMapper {
	
	private BeerInventoryService beerInventoryService;
	private BeerMapper mapper;

	
	// Mapstruct did not work with constructor injection, so setters are used here
	@Autowired
	public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
		this.beerInventoryService = beerInventoryService;
	}

	@Autowired
	public void setMapper(BeerMapper mapper) {
		this.mapper = mapper;
	}

//	@Override
//	public BeerDto beerToBeerDto(Beer beer) {
//		return mapper.beerToBeerDto(beer);
//	}

	@Override
	public BeerDto beerToBeerDto/*WithInventory*/(Beer beer) {
		BeerDto dto = mapper.beerToBeerDto(beer);
		dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
		return dto;
	}

	@Override
	public Beer beerDtoToBeer(BeerDto beerDto) {
		return mapper.beerDtoToBeer(beerDto);
	}
}