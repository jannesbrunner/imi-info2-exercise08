package imi.calculator;


public class Element<T> {
	private T value;
	private Element<T> next;

	public Element(T value, Element<T> next) {
		this.value = value;
		this.next = next;
	}

	public Element(T value) {
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	public void setNext(Element<T> next) {
		this.next = next;
	}
	
	public Element<T> getNext() {
		return this.next;
	}
	
	public boolean hasNext(){
		if(this.next==null) {
			return false;
		}
		else {
			return true;
		}
	}
}
