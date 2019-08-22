package com.sudhakar.web.smsServer.services.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.sudhakar.web.smsServer.services.SMSCacheManager;

@Service
public class SMSCacheManagerImpl implements SMSCacheManager {

	@Autowired
	private RedisTemplate<String,Object> cacheTemplate;

	@Override
	public void cacheStopDetails(String fromNumber, String toNumber) {
		StringBuilder keyString = new StringBuilder().append(fromNumber).append("_").append(toNumber);
		cacheTemplate.opsForValue().set(keyString.toString(), "STOP", 4, TimeUnit.HOURS);		
	}

	@Override
	public boolean isStopped(String fromNumber, String toNumber) {
		StringBuilder keyString = new StringBuilder().append(fromNumber).append("_").append(toNumber);
		return (cacheTemplate.opsForValue().get(keyString.toString()) != null);
	}

	private void cacheLimitDetails(String fromNumber, Integer count) {
		cacheTemplate.opsForValue().set( fromNumber, count, 1, TimeUnit.DAYS);
	}

	@Override
	public boolean doIncrementLimit(String fromNumber) {
		String value = 	(String) cacheTemplate.opsForValue().get(fromNumber);
		
		if ( value == null ) {
			return true;
		}
		
		Integer currLimit = Integer.parseInt(value);
		if ( currLimit.intValue() < 5 )
		{
			cacheLimitDetails(fromNumber, currLimit.intValue()+1);
		}
		return false;
	}
	
}
