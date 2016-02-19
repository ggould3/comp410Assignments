(*
——————————————————————————————————————————————————————
BQUE of int

     new: pos       -> BQUE    (here, pos is int > 0)
     enq: BQUE x E  -> BQUE 
     deq: BQUE      -> BQUE
     front: BQUE    -> E
     size: BQUE     -> nat     (naturals, int >=0 )
     max: BQUE      -> pos
     empty: BQUE    -> boolean
     full: BQUE     -> boolean
——————————————————————————————————————————————————————
*)

datatype BQUE = 
   New of int  
   | enq of BQUE * int;

fun empty (New(n)) = true
  | empty(enq(Q,e)) = false ;

fun max (New(n)) = n
  | max (enq(Q,e)) = max(Q) ;

fun deq (New(n)) = New(n)
  | deq (enq(Q,e)) = if (empty(Q)) 
			then New(max(Q))
			else enq(deq(Q),e) ;

exception frontNewQueue;

fun front (New(n)) = raise frontNewQueue
  | front (enq(Q,e)) = if (empty(Q)) then e
				else front(Q) ;

fun size (New(n)) = 0
  | size (enq(Q,e)) = if size(Q)=max(Q)
                         then max(Q)
                         else size(Q)+1 ;

fun full (New(n)) = false
  | full (enq(Q,e)) = if size(Q)>=max(Q)-1
                         then true
                         else false ;

(*---------------------------------------*)
(*   test data points                    *)
(*---------------------------------------*)

val b0 = New(6);
val b1 = enq(b0,3);
val b2 = enq(b1,6);
val b3 = enq(b2,9);
val b4 = enq(b3,12);
val b5 = enq(b4,15);

(*---------------------------------------*)
(*   test cases                          *)
(*---------------------------------------*)

size(b3) = 3;
size(b0) = 0;
size(enq(b5,18)) = 6;
size(enq(enq(b5,18),21)) = 6;
empty(deq(b1)) = true;
empty(deq(deq(deq(b3)))) = true;
empty(b4) = false;
full(b3) = false;
full(enq(b5,18)) = true;
max(b4) = 6;
max(New(45)) = 45;
max(deq(deq(b2))) = 6;
front(b2) = 3;

(*---------------------------------------*)
(*   test exceptions                     *)
(*---------------------------------------*)

front(New(32));