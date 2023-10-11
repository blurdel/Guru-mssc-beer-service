package com.blurdel.msscbeerservice.events;

import com.blurdel.msscbeerservice.web.model.BeerDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

	private static final long serialVersionUID = -5819325361588246835L;

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}

}
