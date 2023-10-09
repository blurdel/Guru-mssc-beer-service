package com.blurdel.msscbeerservice.services;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.blurdel.msscbeerservice.config.JmsConfig;
import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.events.BrewBeerEvent;
import com.blurdel.msscbeerservice.repositories.BeerRepository;
import com.blurdel.msscbeerservice.services.inventory.BeerInventoryService;
import com.blurdel.msscbeerservice.web.mappers.BeerMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
	
	private final BeerRepository beerRepository;
	private final BeerInventoryService beerInventoryService;
	private final JmsTemplate jmsTemplate;
	private final BeerMapper beerMapper;
	
	
	@Scheduled(fixedRate = 5000) // 5 seconds
	private void checkForLowInventory() {
		List<Beer> beers = beerRepository.findAll();
		
		beers.forEach(beer -> {
			Integer invQOH = beerInventoryService.getOnhandInventory(beer.getId());
			
			log.debug("Min On-hand is: " + beer.getMinOnHand());
			log.debug("Inventory is: " + invQOH);
			
			if (beer.getMinOnHand() >= invQOH) {
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});
		
	}
	
}
