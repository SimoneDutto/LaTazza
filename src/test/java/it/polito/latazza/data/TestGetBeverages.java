package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

public class TestGetBeverages {

	@Test
	public void TestGetBev() throws BeverageException {
		DataImpl data = new DataImpl();
		data.reset();
		Map<Integer,String> mappa = new HashMap<>();
		
		try {
			data.createBeverage("coffee", 50, 500);
			data.createBeverage("chocolate", 50, 100);
			data.createBeverage("tea", 50, 200);
			data.createBeverage("camomile", 50, 300);
			
			mappa = data.getBeverages();
			
			Map<Integer,String> expected = new HashMap<>();
			expected.put(1,"coffee");
			expected.put(2,"chocolate");
			expected.put(3,"tea");
			expected.put(4,"camomile");
			
			for(int i=1; i<=mappa.size(); i++) {
			String s1 = expected.get(i);
			String s2 = mappa.get(i);
				assertEquals(s1,s2);
			}
					
		}catch(BeverageException e) {
			e.printStackTrace();
		}
	}
}