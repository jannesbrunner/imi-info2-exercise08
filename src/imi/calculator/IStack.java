package imi.calculator;


public interface IStack<T> {
	
	public void push(T value);
	
	public T pop();
	
	public T top();
	
	public String toString();
	
	public boolean isEmpty();
	
}
