//==== client code =================================================
// this is the test framework where you make some queues
// and exercise them
// our goal is for each to behave the same regardless
// of the way the LIST is implemented

class BridgeQueueListDemo {

    public static void main( String[] args ) {

        // create front end objects, of type abstract Queue
        // but with different back end implementation objects

        Queue qLL = new Queue("link"); // one with links LIST implementation
        Queue qAL = new Queue("array"); // one with array LIST implementation

        // speak the same interface "language" to each
        // show the results of each are the same

        // follow the guidelines in the assignment 2 description
        // about reading from command line args
        // and manipulating each queue
        // and printing its contents

        if(args.length == 0){
            System.out.println("No arguments supplied");
            return;
        }

        qLL.enq(args[0]);
        qAL.enq(args[0]);
        System.out.println(qLL.size());
        System.out.println(qAL.size());
        System.out.println(qLL.front());
        System.out.println(qLL.front());
        qLL.deq();
        qAL.deq();
        System.out.println(qLL.size());
        System.out.println(qAL.size());

        for(int i=1; i<args.length; i++){
            qLL.enq(args[i]);
            qAL.enq(args[i]);
        }

        for(int i=0; i<args.length-1; i++){
            System.out.println(qLL.front());
            System.out.println(qAL.front());
            qLL.deq();
            qAL.deq();
            System.out.println(qLL.size());
            System.out.println(qAL.size());
        }
    }

}



//=== Abstraction side ==================================
// this is the "front end"
// the client code talks to this abstract object
// the abstraction delegates executions of its operations
// to a "back end" object that contains the implementation details

class Queue {
//    new:                  --> QUEUE
//    enq:   QUEUE x String --> QUEUE
//    deq:   QUEUE          --> QUEUE
//    front: QUEUE          --> String
//    size:  QUEUE          --> int
//    empty: QUEUE          --> boolean

    protected ListImp imp;
    public Queue( String iType ) {
        // decide which LIST implementation to install in imp ...
        if(iType.trim().equalsIgnoreCase("link")){
            imp = new ListImpLinks();
        }else{
            imp = new ListImpArray();
        }
    }
    public Queue() {
        // pick a default implementation
        // the default implementation is an array list
        imp = new ListImpArray();
    }
    //
    // other QUEUE operations
    // in these ops you have to decide how to make use of the
    // LIST imp ops to get QUEUE behavior to happen

    public void enq(String val){
        imp.ins(val, size());
    }

    public void deq(){
        imp.rem(0);
    }

    public String front(){
        return imp.get(0);
    }

    public int size(){
        return imp.size();
    }

    public boolean empty(){
        return imp.empty();
    }
}


//=== Implementation side =======================================
// one of these is hooked into each front end object

interface ListImp {
//    new                        --> LIST
//    ins:   LIST x String x int --> LIST
//    rem:   LIST x int          --> LIST
//    get:   LIST x int          --> String
//    size:  LIST                --> int
//    empty: LIST                --> boolean

    void ins(String val, int pos);
    void rem(int pos);
    String get(int pos);
    int size();
    boolean empty();

}

class ListImpLinks implements ListImp {
    // code for the LIST common ops
    // using linked cells to make the magic happen

    LinkCell first;
    LinkCell last;
    private int size;

    public ListImpLinks(){

    }

    public void ins(String val, int pos){
        LinkCell cell = new LinkCell(val);
        if(empty()){
            first = cell;
            last = cell;
            size++;
        }else if(pos == size()){
            last.next = cell;
            last = last.next;
            size++;
        }else {
            LinkCell temp = first;
            for (int i = 0; i < pos-1; i++) {
                temp = temp.getNext();
            }
            cell.next = temp.next;
            temp.next = cell;
            size++;
        }
    }

    public void rem(int pos){
        if(pos == 0){
            first = first.next;
            size--;
        }else if(pos == size()){
            LinkCell temp = first;
            for (int i = 0; i < pos-1; i++) {
                temp = temp.getNext();
            }
            temp.next = null;
            last = temp;
            size--;
        }else{
            LinkCell temp = first;
            for (int i = 0; i < pos-1; i++) {
                temp = temp.getNext();
            }
            temp.next = temp.next.next;
            size--;
        }
    }

    public String get(int pos){
        LinkCell temp = first;
        for (int i = 0; i < pos-1; i++) {
            temp = temp.getNext();
        }
        return temp.getVal();
    }

    public int size(){
        return size;
    }

    public boolean empty(){
        return first == null;
    }
}

class LinkCell{
    String val;
    LinkCell next;

    LinkCell(String v){
        val = v;
    }

    String getVal(){
        return val;
    }

    LinkCell getNext(){
        return next;
    }
}

class ListImpArray implements ListImp {
    // code for the LIST common ops
    // using array to make the magic happen

    final static int MAX = 100;
    String[] array;

    public ListImpArray(){
        array = new String[MAX];
    }

    public void ins(String val, int pos){
        String temp1 = val;
        String temp2;
        for (int i=pos; i<size()+1; i++){
            temp2 = temp1;
            temp1 = array[i];
            array[i] = temp2;
        }
    }

    public void rem(int pos){
        for(int i=pos; i<array.length; i++){
            if(i == array.length-1){
                array[i] = null;
                return;
            }
            array[i] = array[i+1];
        }
    }

    public String get(int pos){
        return array[pos];
    }

    public int size(){
        int counter = 0;
        while(counter < array.length){
            if(array[counter] == null){
                return counter;
            }
            counter++;
        }
        return -1;
    }

    public boolean empty(){
        return array[0] == null;
    }
}