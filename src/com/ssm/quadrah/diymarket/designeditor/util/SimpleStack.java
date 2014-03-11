package com.ssm.quadrah.diymarket.designeditor.util;

//TODO : make this unlimited stack like array list
public class SimpleStack {
	//stack vals
	private int stack[];
	private int stackPoint;

	//stackFunc
	public SimpleStack(int mStackSize) {
		stackPoint = 0;
		stack = new int[mStackSize];
	}

	public void push(int value) {
		stack[stackPoint] = value;
		stackPoint++;
	}

	public int pop() {		
		if( stackPoint <= 0 )
			return -1;
		stackPoint--;
		return stack[stackPoint];
	}
}