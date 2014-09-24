package com.cig.lib.collada;

import org.jdom2.Element;

public class Accessor extends ColladaObject {

	public int count = 0, stride = 0;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		count = getInt(element.getAttribute("count"));
		stride = getInt(element.getAttribute("stride"));
	}

	public Param getParam(int index) {
		return (Param) getChildren().get(index);
	}

}
