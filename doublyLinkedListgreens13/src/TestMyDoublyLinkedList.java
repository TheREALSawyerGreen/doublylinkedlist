// Test driver for the MyDoublyLinkedList class.
// by Jeff Ward

import java.util.*;

public class TestMyDoublyLinkedList {
    public static void main(String[] args) {
        MyDoublyLinkedList<String> list = new MyDoublyLinkedList<String>();
        try {
            list.removeFirst();
            System.out.println("Test 1 failed");
            System.exit(1);
        }
        catch (NoSuchElementException ex) {
            System.out.println("Test 1 successful");
        }
        try {
            list.removeLast();
            System.out.println("Test 2 failed");
            System.exit(1);
        }
        catch (NoSuchElementException ex) {
            System.out.println("Test 2 successful");
        }

        list.add("Alabama");
        list.add("Alaska");
        list.add("Arizona");
        list.add("Arkansas");

        // The list should now be:
        // [Alabama, Alaska, Arizona, Arkansas]
        String listToString = list.toString();
        if (listToString.equals("[Alabama, Alaska, Arizona, Arkansas]"))
            System.out.println("Test 3 successful");
        else {
            System.out.println("Test 3 failed");
            System.out.println(listToString);
            System.exit(1);
        }

        list.add(4, "California");
        list.add(0, "West Virginia");
        list.add(2, "Wisconsin");
        list.add(6, "Wyoming");
        list.addFirst("Florida");
        list.addLast("Texas");

        // [Florida, West Virginia, Alabama, Wisconsin, Alaska, Arizona, Arkansas, Wyoming, California, Texas]
        listToString = list.toString();
        if (listToString.equals("[Florida, West Virginia, Alabama, Wisconsin, Alaska, Arizona, Arkansas, Wyoming, California, Texas]"))
            System.out.println("Test 4 successful");
        else {
            System.out.println("Test 4 failed");
            System.out.println(listToString);
            System.exit(1);
        }

        list.removeLast();
        list.removeFirst();
        list.remove(list.indexOf("West Virginia"));
        list.remove(list.indexOf("Wisconsin"));
        list.remove("Wyoming");

        // [Alabama, Alaska, Arizona, Arkansas, California]
        listToString = list.toString();
        if (listToString.equals("[Alabama, Alaska, Arizona, Arkansas, California]"))
            System.out.println("Test 5 successful");
        else {
            System.out.println("Test 5 failed");
            System.out.println(listToString);
            System.exit(1);
        }

        // still [Alabama, Alaska, Arizona, Arkansas, California]"))
        StringBuilder sb = new StringBuilder();
        //for (String s : list) {
        //	sb.append(s);
        //}
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            sb.append(s);
        }
        if (sb.toString().equals("AlabamaAlaskaArizonaArkansasCalifornia"))
            System.out.println("Test 6 successful");
        else {
            System.out.println("Test 6 failed");
            System.out.println(listToString);
            System.out.println(sb.toString());
            System.exit(1);
        }

        list.set(1, "Arkansas");

        // [Alabama, Arkansas, Arizona, Arkansas, California]
        listToString = list.toString();
        if (listToString.equals("[Alabama, Arkansas, Arizona, Arkansas, California]"))
            System.out.println("Test 7 successful");
        else {
            System.out.println("Test 7 failed");
            System.out.println(listToString);
            System.exit(1);
        }

        if (!list.contains("Arizona"))
            throw new RuntimeException();
        if (list.contains("Alaska"))
            throw new RuntimeException();
        if (list.get(2) != "Arizona")
            throw new RuntimeException();
        if (list.indexOf("Arkansas") != 1)
            throw new RuntimeException();
        if (list.lastIndexOf("Arkansas") != 3)
            throw new RuntimeException();
        if (list.getFirst() != "Alabama")
            throw new RuntimeException();
        if (list.getLast() != "California")
            throw new RuntimeException();
        try {
            list.get(-1);
            System.out.println("Test 8 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 8 successful");
        }
        try {
            list.get(5);
            System.out.println("Test 9 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 9 successful");
        }
        try {
            list.set(-1, "Colorado");
            System.out.println("Test 10 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 10 successful");
        }
        try {
            list.set(5, "Connecticut");
            System.out.println("Test 11 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 11 successful");
        }
        try {
            list.remove(-1);
            System.out.println("Test 12 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 12 successful");
        }
        try {
            list.remove(5);
            System.out.println("Test 13 failed");
            System.exit(1);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("Test 13 successful");
        }

        // still [Alabama, Arkansas, Arizona, Arkansas, California]
        sb = new StringBuilder();
        ListIterator<String> listIter = list.listIterator(0);
        try {
            listIter.previous();
            System.out.println("Test 14 failed");
            System.exit(1);
        }
        catch (NoSuchElementException ex) {
            System.out.println("Test 14 successful");
        }
        while (listIter.hasNext())
            sb.append(listIter.next());
        if (sb.toString().equals("AlabamaArkansasArizonaArkansasCalifornia"))
            System.out.println("Test 15 successful");
        else {
            System.out.println("Test 15 failed");
            System.out.println(sb.toString());
            System.exit(1);
        }
        try {
            listIter.next();
            System.out.println("Test 16 failed");
            System.exit(1);
        }
        catch (NoSuchElementException ex) {
            System.out.println("Test 16 successful");
        }

        sb = new StringBuilder();
        while (listIter.hasPrevious())
            sb.append(listIter.previous());

        if (sb.toString().equals("CaliforniaArkansasArizonaArkansasAlabama"))
            System.out.println("Test 17 successful");
        else {
            System.out.println("Test 17 failed");
            System.out.println(sb.toString());
            System.exit(1);
        }

        int counter = 2;
        listIter = list.listIterator(counter);
        while (listIter.hasNext()) {
            if (listIter.nextIndex() != counter)
                throw new RuntimeException();
            if (listIter.previousIndex() != counter - 1)
                throw new RuntimeException();
            if (listIter.next() != list.get(counter))
                throw new RuntimeException();
            counter++;
        }
        while (listIter.hasPrevious()) {
            if (listIter.nextIndex() != counter)
                throw new RuntimeException();
            if (listIter.previousIndex() != counter - 1)
                throw new RuntimeException();
            if (listIter.previous() != list.get(counter - 1))
                throw new RuntimeException();
            counter--;
        }
        System.out.println("Test 18 successful");

        // still [Alabama, Arkansas, Arizona, Arkansas, California]
        while (listIter.hasNext())
            if (listIter.next().length() == 7)
                listIter.remove();

        while (listIter.hasNext())
            listIter.next();
        while (listIter.hasPrevious())
            if (listIter.previous() == "California")
                listIter.remove();

        // [Arkansas, Arkansas]
        listToString = list.toString();
        if (listToString.equals("[Arkansas, Arkansas]"))
            System.out.println("Test 19 successful");
        else {
            System.out.println("Test 19 failed");
            System.out.println("listToString = " + listToString);
            System.exit(1);
        }

        listIter = list.listIterator();
        listIter.next();
        listIter.set("Indiana");
        listIter.add("Kentucky");
        listIter.next();
        listIter.set("Ohio");

        // [Indiana, Kentucky, Ohio]
        if (list.size() != 3)
            throw new RuntimeException();
        if (list.getFirst() != "Indiana" || list.getLast() != "Ohio")
            throw new RuntimeException();
        if (list.removeFirst() != "Indiana" || list.removeLast() != "Ohio")
            throw new RuntimeException();
        System.out.println("Test 20 successful");

        // [Kentucky]
        listToString = list.toString();
        if (listToString.equals("[Kentucky]"))
            System.out.println("Test 21 successful");
        else {
            System.out.println("Test 21 failed");
            System.out.println("listToString = " + listToString);
            System.exit(1);
        }

        listIter = list.listIterator();

        try {
            listIter.remove();
            System.out.println("Test 22 failed");
            System.exit(1);
        }
        catch (IllegalStateException ex) {
            System.out.println("Test 22 successful");
        }

        sb = new StringBuilder();
        sb.append(listIter.next());
        if (sb.toString().equals("Kentucky"))
            System.out.println("Test 23 successful");
        else {
            System.out.println("Test 23 failed");
            System.out.println("sb = " + sb);
            System.exit(1);
        }
        listIter.remove();
        try {
            listIter.remove();
            System.out.println("Test 24 failed");
            System.exit(1);
        }
        catch (IllegalStateException ex) {
            System.out.println("Test 24 successful");
        }
        listIter.add("Michigan");
        try {
            listIter.remove();
            System.out.println("Test 25 failed");
            System.exit(1);
        }
        catch (IllegalStateException ex) {
            System.out.println("Test 25 successful");
        }

        list.add(1, null);

        // [Michigan, null]
        if (list.indexOf("Michigan") != 0 || list.indexOf(null) != 1)
            throw new RuntimeException();
        System.out.println("Test 26 successful");

        System.out.println("Testing clone method:");
        MyAbstractSequentialList<String> theClone = (MyDoublyLinkedList<String>)(list.clone());
        if (theClone.toString().equals("[Michigan, null]"))
            System.out.println("Test 27 successful");
        else
            throw new RuntimeException();
        list.set(1,  "Kentucky");
        // [Michigan, Kentucky]
        if (list.toString().equals("[Michigan, Kentucky]"))
            System.out.println("Test 28 successful");
        else
            throw new RuntimeException();
        if (theClone.toString().equals("[Michigan, null]"))
            System.out.println("Test 29 successful");
        else
            throw new RuntimeException();

        list.clear();
        // []
        if (list.isEmpty() && list.size() == 0)
            System.out.println("Test 30 successful");
        else
            throw new RuntimeException();

        System.out.println("Testing equals method:");
        MyAbstractSequentialList<Integer> intList1 = new MyDoublyLinkedList<>();
        intList1.add(2);
        intList1.add(3);
        intList1.add(5);
        // intList1 = [2, 3, 5]
        if (intList1.equals(intList1))
            System.out.println("Test 31 successful");
        else
            throw new RuntimeException();
        MyAbstractSequentialList<Integer> intList2 = new MyDoublyLinkedList<>();
        if (!intList1.equals(intList2))
            System.out.println("Test 32 successful");
        else
            throw new RuntimeException();
        intList2.add(2);
        intList2.add(3);
        intList2.add(5);
        // intList1 = [2, 3, 5]  intList2 = [2, 3, 5]
        if (intList1.equals(intList2))
            System.out.println("Test 33 successful");
        else
            throw new RuntimeException();
        intList2.add(7);
        // intList1 = [2, 3, 5]  intList2 = [2, 3, 5, 7]
        if (!intList1.equals(intList2))
            System.out.println("Test 34 successful");
        else
            throw new RuntimeException();
        intList1.add(6);
        // intList1 = [2, 3, 5, 6]  intList2 = [2, 3, 5, 7]
        if (!intList1.equals(intList2))
            System.out.println("Test 35 successful");
        else
            throw new RuntimeException();
        intList1.set(3, 7);
        // intList1 = [2, 3, 5, 7]  intList2 = [2, 3, 5, 7]
        if (intList1.equals(intList2))
            System.out.println("Test 36 successful");
        else
            throw new RuntimeException();
        Object o = intList2;
        // intList1 = [2, 3, 5, 7]  o = [2, 3, 5, 7]
        if (intList1.equals(o))
            System.out.println("Test 37 successful");
        else
            throw new RuntimeException();
        intList1.set(2,  null);
        // intList1 = [2, 3, null, 7]  intList2 = [2, 3, 5, 7]
        if (!intList1.equals(intList2))
            System.out.println("Test 38 successful");
        else
            throw new RuntimeException();
        intList1.set(2,  5);
        intList2.set(0,  null);
        // intList1 = [2, 3, 5, 7]  intList2 = [null, 3, 5, 7]
        if (!intList1.equals(intList2))
            System.out.println("Test 39 successful");
        else
            throw new RuntimeException();
        intList1.set(0,  null);
        // intList1 = [null, 3, 5, 7]  intList2 = [null, 3, 5, 7]
        if (intList1.equals(intList2))
            System.out.println("Test 40 successful");
        else
            throw new RuntimeException();
    }
}
