package com.cig.lib.collada;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

public class PolyList extends ColladaObject {

	public List<Input> inputs = new ArrayList<Input>();
	public Vcount vcount = null;
	public P p = null;
	public int count = 0;

	// TODO material

	@Override
	public void parse(Element element, boolean keepDOM) {
		super.parse(element, keepDOM);

		count = getInt(element.getAttribute("count"));

		vcount = (Vcount) getChildTag("vcount");
		p = (P) getChildTag("p");

		List<ColladaObject> colladaInputs = getChildrenTag("input");
		for (ColladaObject collada : colladaInputs) {
			inputs.add((Input) collada);
		}
	}

}
