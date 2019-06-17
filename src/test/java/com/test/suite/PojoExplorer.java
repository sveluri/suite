package com.test.suite;

import java.beans.Statement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PojoExplorer<T> {

	public Class<T> tClass;
	public static final String SET = "set";
	public static final String GET = "get";

	public PojoExplorer(Class<T> tClass) {
		try {
			this.tClass = tClass;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getClassName() {
		return this.tClass.getName();
	}

	public List<Field> getVariables() {

		List<Field> newFieldsList = new ArrayList<>();

		List<Field> fieldsList = Arrays.asList(this.tClass.getDeclaredFields());
		if (fieldsList != null && !fieldsList.isEmpty()) {
			for (Field field : fieldsList) {
				if (!field.isSynthetic()) {
					field.setAccessible(true);
					newFieldsList.add(field);
				}
			}
		}

		return newFieldsList;
	}

	public T createInstance() {
		try {
			if (isGenericConstructorPresent())
				return this.tClass.newInstance();
			else {
				return prepareDummyObject();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isGenericConstructorPresent() {
		List<Constructor> constructorList = getConstructors();
		boolean found = false;
		for (Constructor<?> cons : constructorList) {
			if (cons.getParameterCount() == 0) {
				found = true;
				break;
			}
		}
		if (!found) {
			for (Constructor<?> cons : constructorList) {
				cons.setAccessible(true);
			}
		}
		return found;
	}

	public String prepareMethodName(Field field, String setGet) {

		String initialName = field.getName();
		String firstChar = String.valueOf(initialName.charAt(0));
		firstChar = firstChar.toUpperCase();
		String formattedName = initialName.substring(1, initialName.length());

		String name = String.format("%s%s", firstChar, formattedName);

		Class<?> returnClass = field.getType();
		
		if(setGet.equals(GET) && (returnClass == java.lang.Boolean.class || String.valueOf(returnClass).equalsIgnoreCase("boolean"))) {
			return String.format("%s%s", "is", name);
		}
		
		return String.format("%s%s", setGet, name);

	}

	public List<Constructor> getConstructors() {
		return Arrays.asList(this.tClass.getConstructors());
	}

	public boolean isGenericConstructor(Constructor cons) {
		return cons.getParameterCount() == 0;
	}

	public boolean isMethodPresent(String methodName) {

		List<Method> methodsList = Arrays.asList(this.tClass.getMethods());
		boolean found = false;
		for (Method method : methodsList) {
			if (methodName.equalsIgnoreCase(method.getName())) {
				found = true;
				break;
			}
		}

		return found;
	}

	private T prepareDummyObject() throws Exception {

		Object obj = null;

		List<Constructor> constructorList = getConstructors();
		for (Constructor<?> cons : constructorList) {
			if (cons.getParameterCount() != 0) {

				List<Class> typesList = Arrays.asList(cons.getParameterTypes());

				List<Object> objList = new ArrayList<>();

				for (Class<?> tClass : typesList) {
					Object objValue = getValue(tClass);
					objList.add(objValue);
				}

				return (T) cons.newInstance(objList.toArray());

			}
		}

		return (T) obj;
	}

	private Object getValue(Class<?> tClass2) throws Exception {
		if (DataHelper.isPrimitive(tClass2)) {
			return DataHelper.getDefaultValue(tClass2);
		}
		return tClass2.newInstance();
	}

}
