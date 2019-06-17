package com.test.suite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataHelper {

	public static final String STRING_VALUE = "anyStringValue";
	public static final String STRING_CLASS_NAME = "java.lang.String";
	public static final String DATE_CLASS_NAME = "java.util.Date";
	public static final Date DATE_VALUE = new Date();
	public static final Integer INT_VALUE = 1;
	public static final Long LONG_VALUE = 102930291039l;
	public static final Double DOUBLE_VALUE = 1.1;
	// public static final String STRING_VALUE = "anyStringValue";
	private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();
	public static boolean found = false;
	private static boolean DEFAULT_BOOLEAN;
	private static byte DEFAULT_BYTE;
	private static short DEFAULT_SHORT;
	private static int DEFAULT_INT;
	private static long DEFAULT_LONG;
	private static float DEFAULT_FLOAT;
	private static double DEFAULT_DOUBLE;

	static {
		PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
		PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
		PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
		PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
		PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
		PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
		PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
		PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
		PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
	}

	public Object getValue(Class classType) {
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> wrap(Class<T> c) {
		return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
	}

	@SuppressWarnings("unchecked")
	public static boolean isPrimitive(Class c) {
		return c.isPrimitive();
	}

	public static Object getDefaultValue(Class clazz) {
		if (clazz.equals(boolean.class)) {
			return DEFAULT_BOOLEAN;
		} else if (clazz.equals(byte.class)) {
			return DEFAULT_BYTE;
		} else if (clazz.equals(short.class)) {
			return DEFAULT_SHORT;
		} else if (clazz.equals(int.class)) {
			return DEFAULT_INT;
		} else if (clazz.equals(long.class)) {
			return DEFAULT_LONG;
		} else if (clazz.equals(float.class)) {
			return DEFAULT_FLOAT;
		} else if (clazz.equals(double.class)) {
			return DEFAULT_DOUBLE;
		}
		return null;
	}
}
