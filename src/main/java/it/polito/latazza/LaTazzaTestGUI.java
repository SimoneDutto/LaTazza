package it.polito.latazza;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.gui.MainSwing;

public class LaTazzaTestGUI {
	public static void main(String[] args) {
		DataImpl data = new DataImpl("test_db");
		data.reset();
		new MainSwing(data);
	}
}
