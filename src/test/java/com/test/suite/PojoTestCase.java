package com.test.suite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.beans.Expression;
import java.beans.Statement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PojoTestCase {

	public List<Class> classList = new ArrayList<>();

	@Mock
	DataHelper datahelper;

	@Before
	public void setup() {
		classList.add(Employee.class);
		classList.add(Department.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPojo() throws Exception {

		for (Class<?> classObj : classList) {
			PojoExplorer<?> pojoExplorer = new PojoExplorer<>(classObj);
			// Constructors
			testConstructors(pojoExplorer);

			// Getters - Setters
			innerTest_GetterSetter(pojoExplorer);

			// Equals
			testEquals(pojoExplorer);

			// Hashcode
			testHashCode(pojoExplorer);

			// toString
			testToString(pojoExplorer);
		}

	}

	private void testConstructors(PojoExplorer<?> empP) throws Exception {

		List<Constructor> constructorList = empP.getConstructors();
		Object returnObj;
		for (Constructor<?> constructor : constructorList) {

			if (empP.isGenericConstructor(constructor)) {
				returnObj = constructor.newInstance(null);
			} else {
				returnObj = constructor.newInstance(prepareObjArgs(constructor));
			}
			assertNotNull("Failed initialising constructor for : " + constructor.getClass() + " ", returnObj);
		}

	}

	private Object[] prepareObjArgs(Constructor<?> cons) {

		List<Class> typesList = Arrays.asList(cons.getParameterTypes());

		List<Object> objList = new ArrayList<>();

		for (Class<?> tClass : typesList) {
			Object objValue = getValue(tClass);
			objList.add(objValue);
		}

		return objList.toArray();

	}

	private Object getValue(Class<?> tClass) {
		when(datahelper.getValue(tClass)).thenReturn(Mockito.any(tClass));
		Object objValue = datahelper.getValue(tClass);

		if (tClass.getName().contains(DataHelper.STRING_CLASS_NAME)) {
			objValue = DataHelper.STRING_VALUE;
		}

		return objValue;
	}

	private void innerTest_GetterSetter(PojoExplorer<?> pojoExplorer) throws Exception {
		List<Field> fieldsList = pojoExplorer.getVariables();

		Object obj = pojoExplorer.createInstance();
		Statement stmt;
		Expression expr;

		String setterName;
		String getterName;

		for (Field field : fieldsList) {
			setterName = pojoExplorer.prepareMethodName(field, PojoExplorer.SET);
			getterName = pojoExplorer.prepareMethodName(field, PojoExplorer.GET);
			
			Object objValue = getValue(field);

			stmt = new Statement(obj, setterName, new Object[] { objValue });
			stmt.execute();

			expr = new Expression(obj, getterName, new Object[0]);
			expr.execute();

			assertEquals("Comparision failed for " + field.getClass() + " - for " + field.getName(), objValue,
					expr.getValue());

		}
	}

	private Object getValue(Field field) {
		when(datahelper.getValue(field.getType())).thenReturn(Mockito.any(field.getType()));
		Object objValue = datahelper.getValue(field.getType());
		if (field.getType().getName().contains(DataHelper.STRING_CLASS_NAME)) {
			objValue = DataHelper.STRING_VALUE;
		}
		if (field.getType().getName().contains(DataHelper.DATE_CLASS_NAME)) {
			objValue = DataHelper.DATE_VALUE;
		}
		return objValue;
	}
	
	

	private void testEquals(PojoExplorer<?> pojoExplorer) throws Exception {

		if (!pojoExplorer.isMethodPresent("equals")) {
			System.out.println("Equals method is not present in " + pojoExplorer.getClassName());
		}

		Object obj = pojoExplorer.createInstance();
		Object obj2 = prepareDummyObject(pojoExplorer);
		Object obj3 = prepareDummyObject(pojoExplorer);

		assertEquals("Equals method test with null " + obj.getClass(), obj.equals(null), false);
		assertEquals("Equals method test with another class obj " + obj.getClass(), obj.equals(pojoExplorer), false);
		assertEquals("Equals method test with same obj " + obj.getClass(), obj.equals(obj), true);
		assertEquals("Equals method test with another obj " + obj.getClass(), obj.equals(obj2), false);
		assertEquals("Equals method test with another obj " + obj.getClass(), obj2.equals(obj3), true);
	}

	private Object prepareDummyObject(PojoExplorer<?> pojoExplorer) throws Exception {
		List<Field> fieldsList = pojoExplorer.getVariables();
		Statement stmt;
		String setterName;
		Object obj = pojoExplorer.createInstance();
		for (Field field : fieldsList) {
			setterName = pojoExplorer.prepareMethodName(field, PojoExplorer.SET);
			Object objValue = getValue(field);
			stmt = new Statement(obj, setterName, new Object[] { objValue });
			stmt.execute();
		}
		return obj;
	}
	
	private void testHashCode(PojoExplorer<?> pojoExplorer) {
		if (!pojoExplorer.isMethodPresent("hashCode")) {
			System.out.println("Hashcode method is not present " + pojoExplorer.getClassName());
		}

		Object obj = pojoExplorer.createInstance();

		assertNotNull("Hashcode method test  " + obj.getClass(), obj.hashCode());
	}

	private void testToString(PojoExplorer<?> pojoExplorer) {
		if (!pojoExplorer.isMethodPresent("toString")) {
			System.out.println("Hashcode method is not present " + pojoExplorer.getClassName());
		}
		Object obj = pojoExplorer.createInstance();
		assertNotNull("toString method test  " + obj.getClass(), obj.toString());
	}

}
