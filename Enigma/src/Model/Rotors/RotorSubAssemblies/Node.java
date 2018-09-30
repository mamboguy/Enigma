package Model.Rotors.RotorSubAssemblies;

public class Node<T> {

    private T data;
    private Node next;
    private Node prev;

    public Node(){
        next = null;
        prev = null;
        data = null;
    }

    public Node(T data, Node next, Node prev){
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters">
    public T getData() {
        return data;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setters">
    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setData(T data) {
        this.data = data;
    }
    // </editor-fold>
}