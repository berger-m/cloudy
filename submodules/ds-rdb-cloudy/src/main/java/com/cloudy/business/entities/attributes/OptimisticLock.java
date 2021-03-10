package com.cloudy.business.entities.attributes;

/**
 * Indicates that this entity has an attribute used for optimistic locking
 */
public interface OptimisticLock<T> {

	public static final String DB_COLUMN_VERSION = "version";

	public T getVersion();
}
