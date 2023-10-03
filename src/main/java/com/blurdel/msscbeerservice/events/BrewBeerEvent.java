package com.blurdel.msscbeerservice.events;

import com.blurdel.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {

	private static final long serialVersionUID = 3191590372824868348L;

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
