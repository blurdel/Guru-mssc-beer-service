package com.blurdel.msscbeerservice.services;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.repositories.BeerRepository;
import com.blurdel.msscbeerservice.web.mappers.BeerMapper;
import com.blurdel.msscbeerservice.web.model.BeerDto;
import com.blurdel.msscbeerservice.web.model.BeerPagedList;
import com.blurdel.msscbeerservice.web.model.BeerStyleEnum;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

	private final BeerRepository repo;
	private final BeerMapper mapper;
	
	
	@Override
	public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {
		
		BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = repo.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } 
        else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = repo.findAllByBeerName(beerName, pageRequest);
        } 
        else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = repo.findAllByBeerStyle(beerStyle, pageRequest);
        } 
        else {
            beerPage = repo.findAll(pageRequest);
        }

//        if (showInventoryOnHand){
//            beerPagedList = new BeerPagedList(beerPage
//                    .getContent()
//                    .stream()
//                    .map(mapper::beerToBeerDtoWithInventory)
//                    .collect(Collectors.toList()),
//                    PageRequest
//                            .of(beerPage.getPageable().getPageNumber(),
//                                    beerPage.getPageable().getPageSize()),
//                    beerPage.getTotalElements());
//        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(mapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
//        }

        return beerPagedList;
	}
	
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
