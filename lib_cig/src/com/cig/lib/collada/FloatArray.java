package com.cig.lib.collada;

import org.jdom2.Element;

public class FloatArray extends ColladaObject {

	public float[] fValues;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		int length = Integer.parseInt(element.getAttribute("count").getValue());

		fValues = new float[length];

		String[] strings = element.getValue().split(" ");

		for (int i = 0; i < length; i++) {
			fValues[i] = Float.parseFloat(strings[i]);
		}
		
	}

}
