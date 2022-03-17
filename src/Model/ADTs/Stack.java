package Model.ADTs;

public class Stack<T> implements IStack<T> {
    java.util.Stack<T> stack;

    public Stack() {
        stack = new java.util.Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        java.util.Stack<T> stackStr = new java.util.Stack<>();
        stackStr.addAll(stack);
        while(stackStr.size() > 1) {
            str.append((stackStr.pop()).toString());
            str.append("\n");
        }
        if (stackStr.size() > 0) {
            str.append(stackStr.pop());
            str.append("\n");
        }
        return str.toString();
    }

    public Stack<T> deepCopy() {
        Stack<T> newStack = new Stack<>();
        newStack.stack.addAll(stack);
        return newStack;
    }
}
