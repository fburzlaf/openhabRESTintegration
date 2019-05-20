package me.steffenjacobs.openhabrequester.domain;

import java.util.ArrayList;
import java.util.Collection;

/** @author Steffen Jacobs */
public class GenericNode<T> {

	private final T value;
	private final String name;
	private final Collection<GenericNode<?>> children = new ArrayList<>();
	private GenericNode<?> parent;

	public GenericNode(T value) {
		this.value = value;
		this.name = null;
	}

	public GenericNode(T value, String name) {
		this.value = value;
		this.name = name;
	}

	public T getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setParent(GenericNode<?> parent) {
		this.parent = parent;
	}

	public GenericNode<?> getParent() {
		return parent;
	}

	public void appendChild(GenericNode<?> child) {
		child.setParent(this);
		this.children.add(child);
	}

	public Collection<GenericNode<?>> getChildren() {
		return children;
	}

	public boolean hasValue() {
		return value != null;
	}
}
