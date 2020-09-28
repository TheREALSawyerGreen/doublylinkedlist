/*
NAME: Sawyer Green
INST: WARD
CLASS: CSC 364 ONLINE
Program that creates a doubly linked list with a dummy head node, and the methods required to add, 
compare, clone, remove, set, etc. elements from the list(s)
 */
import java.util.ListIterator ;
import java.util.NoSuchElementException ;

public class MyDoublyLinkedList<E> extends MyAbstractSequentialList<E> implements Cloneable
{
    //create the head node of the list and point both sides to itself
    Node<E> head = new Node<E>(null) ;
    public MyDoublyLinkedList() {
        head.next = head ;
        head.prev = head ;
    }
    //add a node and give it two elements which will be used to hold pointers for next and prev nodes, along with an element
    public class Node<E> {
        E element ;
        Node<E> next ;
        Node<E> prev ;
        public Node(E e)
        {
            this.element = e ;
        }
    }
    @Override
    //passes whatever chosen index to listIterator, returns the result
    public ListIterator<E> listIterator(int index)
    {
        return new listIterator(index) ;
    }
    //Equals Method, checks against the object in question first (a=a), then if the object in question exists in MyList, then compares the size, 
    // and finally uses an 2 iterators to compare every object to eachother
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true ;
        }
        else if (!(o instanceof MyList)) {
            return false ;
        }
        else if (((MyList<E>)o).size() != this.size()) {
            return false ;
        }
        else {
            listIterator doublyLinkedIterator = (MyDoublyLinkedList<E>.listIterator) ((MyDoublyLinkedList<E>) o).listIterator(0) ;
            listIterator listIterator = (MyDoublyLinkedList<E>.listIterator) this.listIterator(0) ;
            for(int i = 0 ; i < size ; i++) {
                if(doublyLinkedIterator.current.element != listIterator.current.element)
                    return false ;
                doublyLinkedIterator.next() ;
                listIterator.next() ;
            }
            return true ;
        }
    }

    //clone method that does non-shallow cloning, by actually cloning values and pointers instead of a shallow copy 
    @Override
    public Object clone() {
        try {
            MyDoublyLinkedList listClone = (MyDoublyLinkedList)super.clone() ;
            Node<E> newHead = new Node<E>(null) ;
            newHead.next = newHead ;
            newHead.prev = newHead ;
            int size = 0 ;
            listClone.head = newHead ;
            listIterator myIterator = (MyDoublyLinkedList<E>.listIterator) this.listIterator(0) ;
            listIterator mySecondIterator = (MyDoublyLinkedList<E>.listIterator) listClone.listIterator(0) ;
            while(myIterator.hasNext()) {
                mySecondIterator.add(myIterator.current.element) ;
                size++ ;
                myIterator.next() ;
            }
            listClone.size = size ;
            return listClone ;
        }
        //catch
        catch(CloneNotSupportedException ex) {
            return null ;
        }
    }

    //Add Methods, takes an element and an index. iterates through the linkedlist to put the node where desired, then sets the pointers of the nodes it is in between. 
    // Checks the easy cases of if the index is last or first so that no loop is needed
    public void add(E e)
    {
        addLast(e) ;
    }
    @Override
    public void add(int index, E e)
    { if (index == 0)
            addFirst(e) ;
        else if (index >= size)
            addLast(e) ;
        else {
            Node<E> current = head.next ;
            for (int i = 0 ; i < index ; i++) {
                current = current.next ;
            }
            Node<E> temp = new Node<E>(e) ;
            temp.prev = current.prev ;
            temp.next = current ;
            current.prev.next = temp ;
            current.prev = temp ;
            size++ ;
        }
    }
    @Override
    public void addFirst(E e) {
// add a node right after the dummy head node
        Node<E> tempNewNode = new Node<>(e) ;
        size++ ;
        head.next.prev = tempNewNode ;
        tempNewNode.next = head.next ;
        tempNewNode.prev = head ;
        head.next = tempNewNode ;

    }

    @Override
    public void addLast(E e) {
// add a node right before the dummy head node
        Node<E> tempNewNode = new Node<>(e) ;
        if(head.next.element == null && head.prev.element == null) {
            head.next = tempNewNode ;
            head.prev = tempNewNode ;
        }
        else {
            tempNewNode.prev = head.prev ;
            tempNewNode.next = head ;
            head.prev.next = tempNewNode ;
            head.prev = tempNewNode ;
        }
        size++ ;
    }

    //Remove method to remove nodes from the list, and then change pointers to account for their absence. 
    // Checks the easy cases of if the index is last or first so that no loop is needed
    public boolean remove(E e) {
        remove(this.indexOf(e)) ;
        return true ;
    }
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException() ;
        }
        else if (index == 0) {
            return removeFirst() ;
        }
        else if (index == size -1) {
            return removeLast() ;
        }
        else {
            Node<E> current = head.next ;
            for (int i = 0 ; i < index ; i++) {
                current = current.next ;
            }
            current.prev.next = current.next ;
            current.next.prev = current.prev ;
            size-- ;
            return current.element ;
        }
    }
    @Override
    public E removeFirst() {
        if(head.next.element == null) {
            throw new NoSuchElementException() ;
        }
        E e = head.next.element ;
        head.next.next.prev = head ;
        head.next = head.next.next ;
        size-- ;
        return e ;
    }
    @Override
    public E removeLast() {
        if(head.prev.element == null) {
            throw new NoSuchElementException() ;
        }
        E e = head.prev.element ;
        head.prev.prev.next = head ;
        head.prev = head.prev.prev ;
        size-- ;
        return e ;
    }


    @Override
    public void clear() {
        head.next = head ;
        head.prev = head ;
        size = 0 ;
    }

    //Contains, get, set, and IndexOf methods
    @Override
    public boolean contains(E e) {
//Checks to see if an element exists in the linked list using .equals, as this allows it to check for NULL.
// == would not allow that, and would result in error
        Node<E> current = head.next ;
        for (int i = 0 ; i < size ; i++) {
            if (current.element.equals(e)) {
                return true ;
            }
            current = current.next ;
        }
        return false ;
    }
    @Override
    //gets the element at any given index by iterating through and checking the element against what the node contains
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException() ;
        }
        Node<E> current = head.next ;
        for (int i = 0 ; i < index ; i++) {
            current = current.next ;
        }
        return current.element ;
    }
    @Override
    public int 
    //fetches the index in the linked list of whatever element the user inputs. Same method as get 
    indexOf(E e) {
        Node<E> current = head.next ;
        for (int i = 0 ; i < size ; i++) {
            if (e == null) {
                if(e == current.element) {
                    return i ;
                }
            }
            else if (current.element.equals(e)) {
                return i ;
            }
            current = current.next ;
        }
        throw new RuntimeException() ;
    }
    @Override
    //same as indexOf, except this checks for the last occurence of the element in the list instead of any/first. 
    // Stores the index in a temp variable until either another instance of the element is found or the head node is reached
    public int lastIndexOf(E e) {
        Node<E> current = head.next ;
        int last = -1 ;
        for (int i = 0 ; i < size ; i++) {
            if (current.element.equals(e)) {
                last = i ;
            }
            current = current.next ;
        }
        if (last > -1) {
            return last ;
        }
        return last ;
    }
    @Override
    //sets the element at a given index. this is done by just making another node with the desired element, 
    // iterating through until current is the desired index, and assigning our new node the pointers that the node it was replacing had
    public Object set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException() ;
        }
        Node<E> current = head.next ;
        //for some reason, in the final tests head.next.prev was being set to NULL and ended up throwing a nullponterexception, this line guarantees that head.next.prev is set
        current.prev = head ;
        for (int i = 0 ; i < index ; i++) {
            current = current.next ;
        }
        Node<E> temp = new Node<E>(e) ;
        //setting all pointers on the nodes before and after the added one, then return the element
       //TEMP COMMENT, REMOVE WHEN HEAD.NEXT.PREV IS BEING SET CORRECTLY 
        temp.next = current.next ;
        temp.prev = current.prev ;
        current.next.prev = temp ;
        current.prev.next = temp ;
        return temp.element ;
    }
    @Override
    public E getFirst()
    {
        return head.next.element ;
    }
    @Override
    public E getLast()
    {
        return head.prev.element ;
    }

    @Override
    //exports the elements in the link lists to a string and displays it for the user
    public String toString() {
        StringBuilder result = new StringBuilder("[") ;
        Node<E> current = head.next ;
        for (int i = 0 ; i < size ; i++) {
            result.append(current.element) ;
            if (current.next != head) {
                current = current.next ;
                result.append(", ") ;

            }
        }
        result.append("]") ;
        return result.toString() ;
    }
//ITERATOR. Uses nodes to iterate through the linked list and return whatever values are needed. 
    public class listIterator implements java.util.ListIterator<E> {
        //Variable declarations
        int index ;
        private Node<E> current = head.next ;
        private Node<E> prev = null ;

        //Constructors
        public listIterator() {
            index = 0 ;
            current = head.next ;
        }
        public listIterator(int number) {
            index = number ;
            current = head.next ;
            for(int i = 0 ; i < number ; i++) {
                current = current.next ;
            }
        }
        @Override
        //checks if there is another node to iterate towards
        public boolean hasNext()
        {
            return (index < size) ;
        }
        @Override
        //sets previous to be the new current, the element stored in E to be the previous element, then moves forwards one node. increases index as well
        //NOTE FOR MYSELF: current was holding no info when assigned to previous, so this looks confusing but isn't 
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException() ;
            }
            prev = current ;
            E e = current.element ;
            current = current.next ;
            index++ ;
            return e ;
        }

       
        @Override
        //sets the current node to be the one before it. Same confusing bit as next but just read the thing above 
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException() ;
            }
            current = current.prev ;
            index-- ;
            prev = current ;
            return current.element ;
        }
        @Override
        //checks with a boolean statement
        public boolean hasPrevious()
        {
            return (index > 0) ;
        }
//this was rough. the fact that these are pointers really messes with your head sometimes. imagine 1 2 3 things, with 3 being where we are. prevprevnext is the pointer from 1->2, and that is being overwritten with the pointer 
    //that's going from 2->3. So now 1.next -> 3, effectively going over 2. Right now, 2 can only be reached by going backwards, not forwards, and the next statement fixes that. prevnextprev is the pointer from 3->2, which is 
    //overwritten with the pointer from 2->1. This process doesn't remove the data from 2, but there are now 0 pointers to or from it, so it is in some kind of limbo. size and index are decremented. 
        public void remove() {
            if(prev == null) {
                throw new IllegalStateException() ;
            }
            prev.prev.next = prev.next ;
            prev.next.prev = prev.prev ;
            size-- ;
            index-- ;
            prev = null ;
        }
        //Add method
        public void add(E e) {
            Node<E> temp = new Node(e) ;
            Node<E> tempPrev = current.prev ;
            Node<E> tempCurrent = current ;
            tempPrev.next = temp ;
            temp.next = tempCurrent ;
            tempCurrent.prev = temp ;
            temp.prev = tempPrev ;
            size++ ;
            index++ ;
            prev = null ;
        }

        //set the element of the node
        public void set(E e)
        {
            prev.element = e ;
        }


        @Override
        //current index return and index-1 return
        public int nextIndex()
        {
            return index ;
        }
        @Override
        public int previousIndex() {
            return index - 1 ;
        }
    }


}
/*
"C:\Program Files\Java\jdk-10.0.2\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\lib\idea_rt.jar=53635:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\bin" -Dfile.encoding=UTF-8 -classpath "C:\Users\Sawyer\IdeaProjects\doublyLinkedListgreens13\out\production\doublyLinkedListgreens13;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\Kotlin\kotlinc\lib\kotlin-stdlib.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\Kotlin\kotlinc\lib\kotlin-reflect.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\Kotlin\kotlinc\lib\kotlin-test.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\Kotlin\kotlinc\lib\kotlin-stdlib-jdk7.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2018.3.3\plugins\Kotlin\kotlinc\lib\kotlin-stdlib-jdk8.jar" TestMyDoublyLinkedList
Test 1 successful
Test 2 successful
Test 3 successful
Test 4 successful
Test 5 successful
Test 6 successful
Test 7 successful
Test 8 successful
Test 9 successful
Test 10 successful
Test 11 successful
Test 12 successful
Test 13 successful
Test 14 successful
Test 15 successful
Test 16 successful
Test 17 successful
Test 18 successful
Test 19 successful
Test 20 successful
Test 21 successful
Test 22 successful
Test 23 successful
Test 24 successful
Test 25 successful
Test 26 successful
Testing clone method:
Test 27 successful
Test 28 successful
Test 29 successful
Test 30 successful
Testing equals method:
Test 31 successful
Test 32 successful
Test 33 successful
Test 34 successful
Test 35 successful
Test 36 successful
Test 37 successful
Test 38 successful
Test 39 successful
Test 40 successful

Process finished with exit code 0

 */