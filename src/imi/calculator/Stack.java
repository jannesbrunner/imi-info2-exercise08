package imi.calculator;

public class Stack<T> implements IStack<T> {
	private Element<T> first;

	public Stack() {
	}

	public void push(T value) {
		try {
			Element<T> el = new Element<T>(value, first);
			first = el;
		} catch (StackOverflowError e) {
			System.out.println("StackOverflowError");
		}

	}

	public T pop() {
		try {
			T temp = first.getValue();
			first = first.getNext();
			return temp;
		} catch (Exception e) {
			System.out.println("StackUnderflowError");
			return null;
		}
	}
	
	public boolean isEmpty() {
		if(first == null) {
			return true;
		}
		else {
			return false;
		}
	}

	public T top() {
		if (isEmpty()){
			return null;
		}
		return first.getValue();
	}

	@Override
	public String toString() {
		Element<T> curr = first;
		String s = "";
		while (curr != null) {
			s += curr.getValue();
			if (curr.hasNext()) {
				s += ", ";
			}
			curr = curr.getNext();
		}
		return s;
	}

	public static void main(String args[]) {
	

	}


}
