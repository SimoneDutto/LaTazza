package it.polito.latazza;

import it.polito.latazza.data.DataImpl;
import it.polito.latazza.data.DataInterface;
import it.polito.latazza.gui.MainSwing;

public class LaTazzaTestGUI {
	public static void main(String[] args) {
		DataInterface data = new DataImpl();
		data.reset();
		new MainSwing(data);
	}
}
