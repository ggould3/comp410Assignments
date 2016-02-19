import java.util.Scanner;
import java.util.Random;

class Assn3Main {

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException{
        // in here code for doing the
        // interactive builder/tester mode
        // and the command line arg auto-execution mode

        Scanner keyboard = new Scanner(System.in);

        String cmd;

        if ((args.length==0)) {
            //no args, interactive op mode
            System.out.println("No args supplied, beginning interactive operation mode");

            Tree t = new BST();

            while(true){
                System.out.println("Enter a command (q to quit)");
                cmd = keyboard.next().toLowerCase().trim();

                switch (cmd){
                    case "new":
                        t = new BST();
                        break;
                    case "i":
                        t.insert(getArg());
                        break;
                    case "r":
                        t.remove(getArg());
                        break;
                    case "c":
                        System.out.println(t.contains(getArg()));
                        break;
                    case "g":
                        t.get(getArg());
                        break;
                    case "x":
                        System.out.println(t.findMax());
                        break;
                    case "n":
                        System.out.println(t.findMin());
                        break;
                    case "v":
                        t.val();
                        break;
                    case "e":
                        System.out.println(t.empty());
                        break;
                    case "s":
                        System.out.println(t.size());
                        break;
                    case "h":
                        System.out.println(t.height());
                        break;
                    case "p":
                        printTree(t, null, true);
                        break;
                    case "f":
                        fillRandomData(t, 100);
                        break;
                    case "q":
                        return;
                    default:
                        System.out.println("Unrecognized command");
                        break;
                }
            }
        }else{
            //args, command op mode
            System.out.println("Args supplied, beginning scripted operation mode");

            Tree t = new BST();
            int index = 0;

            while(index < args.length){
                cmd = args[index++];

                switch (cmd){
                    case "new":
                        t = new BST();
                        break;
                    case "i":
                        t.insert(args[index++]);
                        break;
                    case "r":
                        t.remove(args[index++]);
                        break;
                    case "c":
                        System.out.println(t.contains(args[index++]));
                        break;
                    case "g":
                        t.get(args[index++]);
                        break;
                    case "x":
                        System.out.println(t.findMax());
                        break;
                    case "n":
                        System.out.println(t.findMin());
                        break;
                    case "v":
                        t.val();
                        break;
                    case "e":
                        System.out.println(t.empty());
                        break;
                    case "s":
                        System.out.println(t.size());
                        break;
                    case "h":
                        System.out.println(t.height());
                        break;
                    case "p":
                        printTree(t, null, true);
                        break;
                    case "f":
                        fillRandomData(t, 100);
                        break;
                    case "q":
                        return;
                    default:
                        System.out.println("Unrecognized command");
                        break;
                }
            }
        }
    }

    private static String getArg(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter an argument for the this command");

        return keyboard.nextLine();
    }

    private static void printTree(Tree tree, String indent, boolean preOrder){
        if(tree.val() == null){
            return;
        }
        if(indent == null){
            indent = "";
        }
        Node root = tree.get(tree.val());

        //if preOrder is false, print will be reverse order
        if(preOrder) {
            System.out.println(indent + root.getVal());

            indent = "\t" + indent;

            if (root.getLeft() != null) {
                printTree(new BST(root.getLeft()), indent, true);
            }
            if (root.getRight() != null) {
                printTree(new BST(root.getRight()), indent, true);
            }
        }else{
            if (root.getRight() != null) {
                printTree(new BST(root.getRight()), "\t"+indent, false);
            }

            System.out.println(indent + root.getVal());

            if (root.getLeft() != null) {
                printTree(new BST(root.getLeft()), "\t"+indent, false);
            }
        }

    }

    private static void fillRandomData(Tree tree, int strings){
        int randLength;
        Random random = new Random();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        String insertion;
        int randIndex;

        for(int i=0; i<strings; i++){
            insertion = "";
            randLength = random.nextInt(15)+5;
            for(int j=0; j<randLength; j++){
                randIndex = random.nextInt(25);
                insertion += chars.charAt(randIndex);
            }
            tree.insert(insertion);
        }
    }
}

// then classes for the BST itself

interface Tree{

//    new       :          -->  BST
//    insert    :  String  -->      (or maybe a boolean showing success)
//    remove    :  String  -->      (or maybe a boolean showing success)
//    findMin   :          -->  String
//    findMax   :          -->  String
//    contains  :  String  -->  boolean
//    get       :  String  -->  BST  (a node actually)
//    val       :          -->  String  (returns the key stored in the root)
//    empty     :          -->  boolean
//    size      :          -->  int+ (an integer zero or larger)
//    height    :          -->  int+ (an integer zero or larger)

    boolean insert(String val);
    boolean remove(String val);
    String findMin();
    String findMax();
    boolean contains(String val);
    Node get(String val);
    String val();
    boolean empty();
    int size();
    int height();

}

class BST implements Tree{

    private Node root;

    public BST(){
        root = null;
    }

    public BST(String val){
        root = new Node(val);
    }

    public BST(Node root){
        this.root = root;
    }

    public boolean insert(String val){
        if(root == null){
            root = new Node(val);
        }

        if(contains(val)){
            //No op if the value is already in the tree
            return false;
        }

        Node parent = assignParent(root, val);
        String par = parent.getVal();
        int comp = par.compareTo(val);

        if(comp > 0){
            parent.setLeft(new Node(val));
        }else{
            parent.setRight(new Node(val));
        }

        return true;
    }

    public boolean remove(String val){
        //Outlining necessary variables
        Node node = searchFor(root, val);
        Node parent = assignParent(root, val);
        int comp;
        boolean isRightOfParent;
        Node rightChild;
        Node leftChild;

        //Check for parent of desired node
        //no parent means it is the root
        if(parent == null){
            return false;
        }

        //Check if node is in tree
        //If it is, get its children
        //If it isn't return false and exit
        if(node != null){
            rightChild = node.getRight();
            leftChild = node.getLeft();
            comp = node.getVal().compareTo(parent.getVal());
            isRightOfParent = (comp > 0);
        }else{
            return false;
        }

        //2 child case
        if(rightChild != null && leftChild != null){
            Node subNode = get(rightChild.getVal());
            Tree subtree = new BST(subNode);
            String min = subtree.findMin();
            node.setVal(min);
            remove(min);
        }
        //No child case
        else if(rightChild == null && leftChild == null){
            if(isRightOfParent){
                parent.setRight(null);
            }else{
                parent.setLeft(null);
            }
        }
        //Left child only case
        else if(rightChild == null){
            if(isRightOfParent){
                parent.setRight(leftChild);
            }else{
                parent.setLeft(leftChild);
            }
        }
        //Else, right child only case
        else{
            if(isRightOfParent){
                parent.setRight(rightChild);
            }else{
                parent.setLeft(rightChild);
            }
        }

        return true;
    }

    public String findMin(){
        return getSmallest(root).getVal();
    }

    public String findMax(){
        return getLargest(root).getVal();
    }

    public boolean contains(String val){
        return searchFor(root, val) != null;
    }

    public Node get(String val){
        Node node = searchFor(root, val);

        if(node != null){
            return node;
        }

        return null;
    }

    public String val(){
        if(root == null){
            return null;
        }

        return root.getVal();
    }

    public boolean empty(){
        return root == null;
    }

    public int size(){
        return count(root);
    }

    public int height(){
        return longestPath(root);
    }

    private Node assignParent(Node root, String val){
        if(root == null){
            return null;
        }

        //<0 val is after, >0 val is before
        int comp = root.getVal().toLowerCase().compareTo(val);

        if(comp > 0){
            if(root.getLeft() == null || root.getLeft().getVal().equals(val)){
                return root;
            }else{
                return assignParent(root.getLeft(), val);
            }

        }else{
            if(root.getRight() == null || root.getRight().getVal().equals(val)){
                return root;
            }else{
                return assignParent(root.getRight(), val);
            }
        }
    }

    private Node searchFor(Node root, String val){
        if(root == null){
            return null;
        }

        if(root.getVal().equals(val)){
            return root;
        }
        if(root.getLeft() != null){
            Node leftSearch = searchFor(root.getLeft(), val);
            if(leftSearch != null){
                return leftSearch;
            }
        }
        if(root.getRight() != null){
            Node rightSearch = searchFor(root.getRight(), val);
            if(rightSearch != null){
                return rightSearch;
            }
        }
        return null;
    }

    private Node getSmallest(Node root){
        if(root == null){
            return null;
        }

        if(root.getLeft() == null){
            return root;
        }else{
            return getSmallest(root.getLeft());
        }
    }

    private Node getLargest(Node root){
        if(root == null){
            return null;
        }

        if(root.getRight() == null){
            return root;
        }else{
            return getLargest(root.getRight());
        }
    }

    private int count(Node root){
        if(root == null){
            return 0;
        }else{
            return 1 + count(root.getLeft()) + count(root.getRight());
        }
    }

    private int longestPath(Node root){
        if(root == null){
            return -1;
        }else{
            int heightL = longestPath(root.getLeft());
            int heightR = longestPath(root.getRight());

            return 1 + Math.max(heightL, heightR);
        }
    }

}

class Node{

    private Node left;
    private Node right;

    private String val;

    public Node(String val){
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public void setLeft(Node left){
        this.left = left;
    }

    public void setRight(Node right){
        this.right = right;
    }

    public void setVal(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }

    public Node getLeft(){
        return left;
    }

    public Node getRight(){
        return right;
    }
}

// then supporting classes for random data,
// etc.
// Random data is a method of the main class, a new class was not necessary