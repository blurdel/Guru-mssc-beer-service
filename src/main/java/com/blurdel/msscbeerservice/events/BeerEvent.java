package com.blurdel.msscbeerservice.events;

import java.io.Serializable;

import com.blurdel.msscbeerservice.web.model.BeerDto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

	private static final long serialVersionUID = 6340427986194221161L;
	
	private final BeerDto beerDto;
	
}
