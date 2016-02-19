(*
-------------------------------
   LIST = List of int
-------------------------------
*)

datatype LIST = New  
| ins of LIST * int * int ;

fun size (New) = 0
| size (ins(L,e,i)) = size(L)+1;

fun empty (New) = true
| empty (ins(L,e,i)) = (size(L)=0) ;

exception getFromEmptyList;

fun get (New,i) = raise getFromEmptyList
| get (ins(L,e,k),i) = if(i=k)
			then e
			else if (i<k) then get(L,i)
			else (*i>k*) get(L,i-1) ;

exception findInEmptyList

fun find (New, e) = raise findInEmptyList 
  | find (ins(L,e,i),f) = if(e=f)
				then i
				else if (find(L,f)<i)
				then find(L,f)
				else find(L,f) + 1 ;

fun rem (New,i) = New
  | rem (ins(L,e,k),i) = if(i=k) then L
				else if (i>k)
				then ins(rem(L,i-1),e,k)
				else ins(rem(L,i),e,k-1) ;


(*---------------------------------------*)
(*   test data points                    *)
(*---------------------------------------*)

  val l0 = New;
  val l1 = ins(l0,10,0);
  val l2 = ins(l1,20,1);
  val l3 = ins(l2,30,2);
  val l4 = ins(l3,40,3);

(*---------------------------------------*)
(*   test cases                          *)
(*---------------------------------------*)

size(l2) = 2;
size(l0) = 0;
size(rem(l3,0)) = 2;
size(l4) = 4;
rem(l0,1) = New;
rem(l4,3) = l3;
empty(rem(l1,0)) = true;
empty(l3) = false;
get(l3,0) = 10;
find(l4,40) = 3;

(*---------------------------------------*)
(*   test exceptions                     *)
(*---------------------------------------*)

get(l0,1);
find(rem(l1,0),10);
  