package com.blurdel.msscbeerservice.services.brewing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blurdel.msscbeerservice.config.JmsConfig;
import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.events.BrewBeerEvent;
import com.blurdel.msscbeerservice.events.NewInventoryEvent;
import com.blurdel.msscbeerservice.repositories.BeerRepository;
import com.blurdel.msscbeerservice.web.model.BeerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {
	
	private final BeerRepository beerRepo;
	private final JmsTemplate jmsTempalte;
	
	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUE)
	public void listen(BrewBeerEvent event) {
		BeerDto beerDto = event.getBeerDto();
		
		Beer beer = beerRepo.getOne(beerDto.getId());
		
		beerDto.setQuantityOnHand(beer.getQuantityToBrew());
		
		NewInventoryEvent newInvEvent = new NewInventoryEvent(beerDto);
		
		log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());
		
		jmsTempalte.convertAndSend(JmsConfig.NEW_INVENTORY_QUE, newInvEvent);
	}
	
}
