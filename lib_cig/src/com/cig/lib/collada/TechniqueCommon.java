package com.cig.lib.collada;

import org.jdom2.Element;

public class TechniqueCommon extends ColladaObject {

	public Accessor accessor = null;
	
	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);
		
		accessor = (Accessor) getChildTag("accessor");
	}

	
	
}
