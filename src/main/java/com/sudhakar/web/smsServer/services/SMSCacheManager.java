package com.sudhakar.web.smsServer.services;

public interface SMSCacheManager {

	void cacheStopDetails(String fromNumber, String toNumber);
	
	boolean isStopped(String fromNumber, String toNumber);
	
	boolean doIncrementLimit(String fromNumber);

//	void cacheLimitDetails(String fromNumber, Integer count);
}
