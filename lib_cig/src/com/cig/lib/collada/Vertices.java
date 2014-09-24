package com.cig.lib.collada;

import org.jdom2.Element;

public class Vertices extends ColladaObject {

	public String semantic = null;
	public Source source = null;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		semantic = element.getAttribute("semantic").getValue();
		source = (Source) getChildTag("source");
	}

}
