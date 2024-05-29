package com.AchintyaNigam.demo.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {
	
	CacheManagerCustomizer<ConcurrentMapCacheManager> customizer()
	{
		return new ConcurrentCustomizer();
	}
	
	class ConcurrentCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager>
	{
		
		@Override
		public void customize(ConcurrentMapCacheManager cacheManager)
		{
			cacheManager.setAllowNullValues(false);
			cacheManager.setStoreByValue(false);
		}
	}

	
}
