package com.cig.lib.collada;

import org.jdom2.Element;

public class Param extends ColladaObject {

	public String type = null;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		type = element.getAttribute("type").getValue();
	}

}
