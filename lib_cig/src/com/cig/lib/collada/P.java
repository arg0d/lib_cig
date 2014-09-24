package com.cig.lib.collada;

import org.jdom2.Element;

public class P extends ColladaObject {

	public int[] iValues = null;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		String[] strings = element.getValue().split(" ");

		iValues = new int[strings.length];

		for (int i = 0; i < iValues.length; i++) {
			iValues[i] = Integer.parseInt(strings[i]);
		}
	}

}
