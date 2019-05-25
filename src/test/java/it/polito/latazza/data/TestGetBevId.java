package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;


public class TestGetBevId {
	DataImpl data = new DataImpl("test_db");
	@Test
	public void TestGetBeveragesId() throws BeverageException {
		data.reset();
		List<Integer> lista = new ArrayList<>();
		
		data.createBeverage("coffee", 50, 500);
		data.createBeverage("chocolate", 50, 100);
		data.createBeverage("tea", 50, 200);
		data.createBeverage("camomile", 50, 300);
		
		lista = data.getBeveragesId();
		
		List<Integer> expected = new ArrayList<>();
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		
		for(int i=0; i<lista.size(); i++) {
		int v1 = expected.get(i);
		int v2 = lista.get(i);
			assertEquals(v1,v2);
		}
	}

}