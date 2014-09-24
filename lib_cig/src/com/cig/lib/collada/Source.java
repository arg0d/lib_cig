package com.cig.lib.collada;

import org.jdom2.Element;

public class Source extends ColladaObject {

	public FloatArray array;
	public TechniqueCommon techniqueCommon;

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		array = (FloatArray) getChildTag("float_array");
		techniqueCommon = (TechniqueCommon) getChildTag("technique_common");
	}

}
