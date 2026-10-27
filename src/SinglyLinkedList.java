public class SinglyLinkedList<E> {
    // This class has a default constructor:
    public SinglyLinkedList() {
        length = 0;
    }

    // This is the only field of the class.  It holds the head of the list
    Node<E> head;

    // Length of the linked list
    private int length = 0;

    // Return the first Node<E> in the list
    public synchronized Node<E> getHead() {
        return head;
    }

    // Insert a Node<E> at the beginning of the list
    public synchronized void insertAtBegin(Node<E> node) {
        node.setNext(head);
        head = node;
        length ++;
    }

    // Insert a Node<E> at the end of the list
    public synchronized void insertAtEnd(Node<E> node) {
        if (head == null)
            head = node;
        else {
            Node<E> p, q;
            for(p = head; (q = p.getNext()) != null; p = q);
            p.setNext(node);
        }
        length ++;
    }

    // Add a new value to the list at a given position.
    // All values at that position to the end move over to make room.
    public void insert(E data, int position) {
        // fix the position
        if (position < 0) {
            position = 0;
        }
        if (position > length) {
            position = length;
        }

        // if the list is empty, make it be the only element
        if (head == null) {
            head = new Node<E>(data);
        }
        // if adding at the front of the list...
        else if (position == 0) {
            Node<E> temp = new Node<E>(data);
            temp.next = head;
            head = temp;
        }
        // else find the correct position and insert
        else {
            Node<E> temp = head;
            for (int i=1; i<position; i+=1) {
                temp = temp.getNext();
            }
            Node<E> newNode = new Node<E>(data);
            newNode.next = temp.next;
            temp.setNext(newNode);
        }
        // the list is now one value longer
        length += 1;
    }

    // Remove and return the Node<E> at the head of the list
    public synchronized Node<E> removeFromBegin() {
        Node<E> node = head;
        if (node != null) {
            head = node.getNext();
            node.setNext(null);
        }
        return node;
    }

    // Remove and return the Node<E> at the end of the list
    public synchronized Node<E> getLast() {
        if (head == null)
            return null;
        if (head.getNext() == null) {
            return head;
        }
        Node<E> p = head.getNext();
        while(p.getNext() != null) {
            p = p.getNext();
        }
        return p;
    }

    // Remove and return the Node<E> at the end of the list
    public synchronized Node<E> removeFromEnd() {
        if (head == null)
            return null;
        Node<E> p = head, q = null, next = head.getNext();
        if (next == null) {
            head = null;
            // reduce the length of the list
            length-=1;
            return p;
        }
        while((next = p.getNext()) != null) {
            q = p;
            p = next;
        }
        q.setNext(null);
        // reduce the length of the list
        length-=1;
        return p;
    }

    // Remove a Node<E> matching the specified Node<E> from the list.
    // Use equals() instead of == to test for a matched Node<E>.
    public synchronized void removeMatched(Node<E> node) {
        if (head == null)
            return;
        if (node.equals(head)) {
            head = head.getNext();
            // reduce the length of the list
            length-=1;
            return;
        }
        Node<E> p = head, q = null;
        while((q = p.getNext()) != null) {
            if (node.equals(q)) {
                p.setNext(q.getNext());
                // reduce the length of the list
                length-=1;
                return;
            }
            p = q;
        }
    }

    // Remove the value at a given position.
    // If the position is less than 0, remove the value at position 0.
    // If the position is greater than 0, remove the value at the last position.
    public void remove(int position) {
        // fix position
        if (position < 0) {
            position = 0;
        }

        if (position >= length) {
            position = length-1;
        }

        // if nothing in the list, do nothing
        if (head == null)
            return;

        // if removing the head element...
        if (position == 0) {
            head = head.getNext();
        }
        // else advance to the correct position and remove
        else {
            Node<E> temp = head;
            for (int i=1; i<position; i+=1) {
                temp = temp.getNext();
            }
            temp.setNext(temp.getNext().getNext());
        }
        // reduce the length of the list
        length -= 1;
    }

    // Return a string representation of this collection, in the form ["str1","str2",...].
    public String toString() {
        String result = "[";
        if (head == null) {
            return result+"]";
        }
        result = result + head.getData();
        Node<E> temp = head.getNext();
        while (temp != null) {
            result = result + "," + temp.getData();
            temp = temp.getNext();
        }
        return result + "]";
    }

    // Return the current length of the list.
    public int length() {
        return length;
    }

    // Find the position of the first value that is equal to a given value.
    // The equals method us used to determine equality.
    public int getPosition(int data) {
        // go looking for the data
        Node<E> temp = head;
        int pos = 0;
        while (temp != null) {
            if (temp.getData().equals(data)) {
                // return the position if found
                return pos;
            }
            pos += 1;
            temp = temp.getNext();
        }
        // else return -1
        return Integer.MIN_VALUE;
    }

    // Size of the list.
    public boolean isEmpty(){
        return length==0;
    }

    // Remove everything from the list.
    public void clearList(){
        head = null;
        length = 0;
    }
}