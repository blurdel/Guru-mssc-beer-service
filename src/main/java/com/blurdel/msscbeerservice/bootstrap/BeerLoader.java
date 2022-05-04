package com.blurdel.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.repositories.BeerRepository;


@Component
public class BeerLoader implements CommandLineRunner {

	private final BeerRepository beerRepository;
	
	
	public BeerLoader(BeerRepository beerRepository) {
		super();
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();
	}

	private void loadBeerObjects() {
		if (beerRepository.count() == 0) {
			beerRepository.save(Beer.builder()
					.beerName("Mango Bobs")
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(3370100000001L)
					.price(new BigDecimal(12.95))
					.build()					
					);
			beerRepository.save(Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(3370100000002L)
					.price(new BigDecimal(10.95))
					.build()					
					);
		}
		System.out.println("Loaded Beers: " + beerRepository.count());
	}

}
