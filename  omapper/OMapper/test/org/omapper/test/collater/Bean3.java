/*
 * 
 */
package org.omapper.test.collater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.omapper.annotations.Implementation;
import org.omapper.annotations.Mappable;
import org.omapper.annotations.Source;

/**
 * The Class Bean2.
 */
@Mappable
public class Bean3 {
	
	/** The i. */
	@Source(type = Bean1.class, property = "i")
	private int i;

	/** The x. */
	@Source(type = Bean1.class, property = "x")
	private String x;

	/** The child. */
	@Source(type = Bean1.class, property = "child")
	private Child2 child;

	/** The child list. */
	@Source(type = Bean2.class, property = "childList")
	@Implementation(name = ArrayList.class)
	private List<Child2> childList;

	@Source(type = Bean1.class, property = "childArray")
	private Child2[] childArray;
	
	@Source(type= Bean1.class,property="intArray")
	private int[] intArray;
	
	@Source(type= Bean2.class,property="stringArray")
	private String[] stringArray;
	
	@Source(type= Bean2.class,property="charArray")
	private char[] charArray;

	/**
	 * Gets the i.
	 * 
	 * @return the i
	 */
	public int getI() {
		return i;
	}

	/**
	 * Sets the i.
	 * 
	 * @param i
	 *            the i to set
	 */
	public void setI(int i) {
		this.i = i;
	}

	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public String getX() {
		return x;
	}

	/**
	 * Sets the x.
	 * 
	 * @param x
	 *            the x to set
	 */
	public void setX(String x) {
		this.x = x;
	}

	/**
	 * Gets the child.
	 * 
	 * @return the child1
	 */
	public Child2 getChild() {
		return child;
	}

	/**
	 * Sets the child.
	 * 
	 * @param child
	 *            the new child
	 */
	public void setChild(Child2 child) {
		this.child = child;
	}

	/**
	 * Gets the child list.
	 * 
	 * @return the child1List
	 */
	public List<Child2> getChildList() {
		return childList;
	}

	/**
	 * Sets the child list.
	 * 
	 * @param childList
	 *            the new child list
	 */
	public void setChildList(List<Child2> childList) {
		this.childList = childList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bean2 [i=");
		builder.append(i);
		builder.append(", x=");
		builder.append(x);
		builder.append(", child=");
		builder.append(child);
		builder.append(", childList=");
		builder.append(childList);
		builder.append(", childArray=");
		builder.append(Arrays.toString(childArray));
		builder.append(", intArray=");
		builder.append(Arrays.toString(intArray));
		builder.append(", stringArray=");
		builder.append(Arrays.toString(stringArray));
		builder.append(", charArray=");
		builder.append(Arrays.toString(charArray));
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the childArray
	 */
	public Child2[] getChildArray() {
		return childArray;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}

	public void setChildArray(Child2[] childArray) {
		this.childArray = childArray;
	}

	public String[] getStringArray() {
		return stringArray;
	}

	public void setStringArray(String[] stringArray) {
		this.stringArray = stringArray;
	}

	public char[] getCharArray() {
		return charArray;
	}

	public void setCharArray(char[] charArray) {
		this.charArray = charArray;
	}
}
