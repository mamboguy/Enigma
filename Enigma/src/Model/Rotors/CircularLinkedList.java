package Model.Rotors;

public class CircularLinkedList<T> {

    private Node<T> start;
    private Node<T> end;
    private int size;

    public CircularLinkedList(){
        start = null;
        end = null;
        size = 0;
    }

    public boolean isEmpty(){
        return start == null;
    }

    public int getSize(){
        return size;
    }

    public void insert(T data){

        Node<T> tempNode = new Node<T>(data, null, null);

        if (start == null){

            //If list is empty, then set the start and end to the newly inserted node
            //Additionally, have the singular node point its next/prev to itself
            tempNode.setNext(tempNode);
            tempNode.setPrev(tempNode);
            start = tempNode;
            end = start;

        } else {

            //If list is not empty, then set the current node to start
            //Set the end to point to the new node as next and new node to end as prev

            tempNode.setNext(end);
            tempNode.setPrev(start);
            end.setPrev(tempNode);
            start.setNext(tempNode);

            start = tempNode;
        }

        size++;
    }

    public T getData(){
        return start.getData();
    }

    public void stepNext() {
        this.start = start.getNext();
    }

    public void stepBack(){
        this.start = start.getPrev();
    }

    public Node<T> getNodeAtPosition(int position){
        if (position > size){
            // TODO: 9/29/2018 FIX THIS?
            return null;
        }

        Node temp = start;

        for (int i = 0; i < position; i++) {
            temp = temp.getNext();
        }

        return temp;
    }
}
