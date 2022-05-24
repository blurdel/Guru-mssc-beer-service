package com.blurdel.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.web.model.BeerDto;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

	BeerDto beerToBeerDto(Beer beer);
	
	Beer beerDtoToBeer(BeerDto dto);
}
