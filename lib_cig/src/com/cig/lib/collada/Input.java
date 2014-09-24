package com.cig.lib.collada;

import org.jdom2.Attribute;
import org.jdom2.Element;

public class Input extends ColladaObject {

	public String semantic = null;
	public Source source = null;
	public int offset = -1;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		semantic = element.getAttribute("semantic").getValue();
		source = (Source) getRootObject().findObjectId(element.getAttribute("source").getValue());
		Attribute atrOffset = element.getAttribute("offset");
		if (atrOffset != null) {
			getInt(atrOffset);
		}
	}

}
