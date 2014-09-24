package com.cig.lib.collada;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.cig.lib.GModel;
import com.cig.lib.JdomHelper;

public class ColladaParser {

	public GModel load() {
		SAXBuilder builder = JdomHelper.getSAXBuilder();
		Document dom = null;

		try {
			dom = builder.build(ColladaParser.class.getResourceAsStream("/Cube1.dae"));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}

		Element pCollada = dom.getRootElement();

		ColladaObject co = new ColladaObject();
		co.parse(pCollada, false);

		loadGeometries(co);

		return new GModel(null);
	}

	private void loadGeometries(ColladaObject collada) {

		ColladaObject geometries = collada.getChildTag("library_geometries");

		for (ColladaObject child : geometries.getChildren()) {
			loadGeometry(child);
		}
	}

	private void loadGeometry(ColladaObject collada) {
		List<ColladaObject> meshes = collada.getChildrenTag("mesh");

		for (ColladaObject colladaObject : meshes) {
			Mesh mesh = (Mesh) colladaObject;
			
		}
	}

}
