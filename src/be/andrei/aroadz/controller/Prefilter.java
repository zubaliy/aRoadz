package be.andrei.aroadz.controller;

import be.andrei.aroadz.model.Data;
import be.andrei.aroadz.model.Filter;

public class Prefilter implements Filter {
	DataCombiner dc = null;
	
	public Prefilter() {
		dc = new DataCombiner();
	}
	
	public Data getData() {
		return dc.getData();
	}

	public Data Algorithm1() {
		// do something with data
		return null;
	
	}



}
