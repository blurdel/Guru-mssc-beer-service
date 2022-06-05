package com.blurdel.msscbeerservice.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.blurdel.msscbeerservice.domain.Beer;
import com.blurdel.msscbeerservice.repositories.BeerRepository;


@Component
public class BeerLoader implements CommandLineRunner {

	private final BeerRepository beerRepository;
	public static final String BEER_1_UPC = "063123456006";
	public static final String BEER_2_UPC = "063123456007";
	public static final String BEER_3_UPC = "063123456008";
	
	
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
					.upc(BEER_1_UPC)
					.price(new BigDecimal(12.95))
					.build()					
					);
			
			beerRepository.save(Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(BEER_2_UPC)
					.price(new BigDecimal(10.95))
					.build()					
					);
			
			beerRepository.save(Beer.builder()
					.beerName("No Hammers on the Bar")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(BEER_3_UPC)
					.price(new BigDecimal(15.95))
					.build()					
					);
		}
		System.out.println("Loaded Beers: " + beerRepository.count());
	}

}
