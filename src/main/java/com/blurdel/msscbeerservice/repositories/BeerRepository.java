package com.blurdel.msscbeerservice.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.web.model.BeerStyleEnum;


public interface BeerRepository extends JpaRepository<Beer, UUID> {

	Page<Beer> findAllByBeerName(String beerName, PageRequest pageRequest);

	Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, PageRequest pageRequest);

	Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest);

	Beer findByUpc(String upc);
	
}
