package com.blurdel.msscbeerservice.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.repositories.BeerRepository;
import com.blurdel.msscbeerservice.web.mappers.BeerMapper;
import com.blurdel.msscbeerservice.web.model.BeerDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

	private final BeerRepository repo;
	private final BeerMapper mapper;
	
	@Override
	public BeerDto getById(UUID beerId) {
		return mapper.beerToBeerDto(
				repo.findById(beerId).orElseThrow(NotFoundException::new)
				);
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		return mapper.beerToBeerDto(
				repo.save(mapper.beerDtoToBeer(beerDto))
				);
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
		Beer beer = repo.findById(beerId).orElseThrow(NotFoundException::new);
		
		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle().name());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());
		
		return mapper.beerToBeerDto(repo.save(beer));
	}

}
