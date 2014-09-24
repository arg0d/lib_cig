package com.cig.lib.collada;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.lwjgl.util.vector.Matrix4f;

public class ColladaObject {

	private ColladaObject parent;

	private String tag, id, name;

	private Matrix4f transformationMatrix;

	private List<ColladaObject> children = new ArrayList<ColladaObject>();

	private Element DOM;

	/**
	 * If true, will clone underlying DOM object. Should be set in the
	 * constructor of CcolladaObject.
	 */
	protected boolean keepDOM = false;

	public void addObject(ColladaObject child) {
		child.parent = this;
		children.add(child);
	}

	public ColladaObject getRootObject() {
		if (parent == null) {
			return this;
		}

		return parent.getRootObject();
	}

	public ColladaObject findObjectTag(String tag) {

		if (this.tag != null && this.tag.equals(tag)) {
			return this;
		}

		for (ColladaObject child : children) {
			ColladaObject returnVal = child.findObjectTag(tag);
			if (returnVal != null) {
				return returnVal;
			}
		}

		return null;
	}

	public ColladaObject findObjectId(String id) {

		if (this.id != null && this.id.equals(id)) {
			return this;
		}

		for (ColladaObject child : children) {
			ColladaObject returnVal = child.findObjectId(id);
			if (returnVal != null) {
				return returnVal;
			}
		}

		return null;
	}

	public ColladaObject findObjectName(String name) {

		if (this.name != null && this.name.equals(name)) {
			return this;
		}

		for (ColladaObject child : children) {
			ColladaObject returnVal = child.findObjectName(name);
			if (returnVal != null) {
				return returnVal;
			}
		}

		return null;
	}

	public void parse(Element element, boolean keepDOM) {

		if (element == null) {
			return;
		}

		if (keepDOM) {
			this.DOM = element.clone();
		}

		this.tag = element.getName();

		Attribute pId = element.getAttribute("id");
		if (pId != null) {
			this.id = pId.getValue();
		}

		Attribute pName = element.getAttribute("name");
		if (pName != null) {
			this.id = pName.getValue();
		}

		List<Element> pChildren = element.getChildren();
		for (Element pChild : pChildren) {
			ColladaObject object = createColladaObject(pChild.getName());
			addObject(object);
			object.parse(pChild, keepDOM);
		}

	}

	/*
	 * ==========================================================================
	 * ================================== Getters and setters below
	 * ==============
	 * ============================================================
	 * ==================================
	 */

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Matrix4f getTransformationMatrix() {
		return transformationMatrix;
	}

	public void setTransformationMatrix(Matrix4f transformationMatrix) {
		this.transformationMatrix = transformationMatrix;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public ColladaObject getParent() {
		return parent;
	}

	public Element getDOM() {
		return DOM;
	}

	public List<ColladaObject> getChildren() {
		return children;
	}

	public ColladaObject getChildTag(String tag) {
		for (ColladaObject child : children) {
			if (child.tag != null && child.tag.equals(tag)) {
				return child;
			}
		}

		return null;
	}

	public List<ColladaObject> getChildrenTag(String tag) {

		List<ColladaObject> list = new ArrayList<ColladaObject>();

		for (ColladaObject child : children) {
			if (child.tag != null && child.tag.equals(tag)) {
				list.add(child);
			}
		}

		return list;
	}

	public static ColladaObject createColladaObject(String tag) {

		if (tag.equals("float_array")) return new FloatArray();
		else if (tag.equals("accessor")) return new Accessor();
		else if (tag.equals("param")) return new Param();
		else if (tag.equals("input")) return new Input();
		else if (tag.equals("p")) return new P();
		else if (tag.equals("polylist")) return new PolyList();
		else if (tag.equals("source")) return new Source();
		else if (tag.equals("technique_common")) return new TechniqueCommon();
		else if (tag.equals("vcount")) return new Vcount();
		else if (tag.equals("mesh")) return new Mesh();
		else if (tag.equals("vertices")) return new Vertices();

		return new ColladaObject();
	}

	public static int getInt(Attribute atr) {
		return Integer.parseInt(atr.getValue());
	}

}
