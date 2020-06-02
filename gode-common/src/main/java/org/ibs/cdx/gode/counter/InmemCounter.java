package org.ibs.cdx.gode.counter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InmemCounter implements Counter<Long>{
	
	private Map<Serializable,BigInteger> cache;

	public InmemCounter() {
		this.cache = new ConcurrentHashMap<>();
	}

	public Long next(Serializable key) {
		BigInteger value = this.cache.compute(key, (k,v)-> v!=null ? v.add(BigInteger.ONE) :BigInteger.ONE);
		return value.longValue();
	}
	
	public Long curr(Serializable key) {
		return this.cache.getOrDefault(key, BigInteger.ZERO).longValue();
	}
}
