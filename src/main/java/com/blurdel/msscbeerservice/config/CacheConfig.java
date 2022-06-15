package com.blurdel.msscbeerservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@EnableCaching  // Setup ehcache caching (could have been aded on MsscBeerServiceApplication)
@Configuration
public class CacheConfig {
}
