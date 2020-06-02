package org.ibs.cdx.gode.counter;

import java.io.Serializable;

public interface Counter<V> {
	V next(Serializable key);
	V curr(Serializable key);
}
