package me.steffenjacobs.openhabrequester.service;

import me.steffenjacobs.openhabrequester.domain.GenericNode;

/** @author Steffen Jacobs */
public final class TreePrinterService {

	public TreePrinterService() {
	}

	public void printTree(GenericNode<?> itemTree, int depth) {

		// print name
		for (int i = 0; i < depth; i++) {
			System.out.print("=");
		}
		System.out.println("> " + itemTree.getName() + ": " + itemTree.getValue());

		// print children
		for (GenericNode<?> child : itemTree.getChildren()) {
			printTree(child, depth + 1);
		}

		// insert empty line after each item
		if (depth == 1) {
			System.out.println();
		}
	}
}
