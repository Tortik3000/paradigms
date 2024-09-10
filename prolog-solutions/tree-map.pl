avl(null).
avl(Key, Val, Height, L, R) :-
    avl(L), avl(R),
    height(L, HL), height(R, HR),
    max(HL, HR, Height1).
    Height is 1 + Height1.
    abs(HL- HR) =< 2.

height(null, 0).
height(avl(_, _, Height, _, _), Height).

diff(avl(_, _, _, L, R), Diff) :-
    height(L, HL), height(R, HR),
    Diff is HL - HR.

find_elem([H|T], 0, H) :- !.
find_elem([H|T], Index, Elem) :-
    Index1 is Index - 1,
    find_elem(T, Index1, Elem).

check_sort([_]) :- !.
check_sort([H1, H2|T]) :- H1 =< H2, check_sort([H2|T]).

find_min(avl(Key, Val, Height, null, _), Key, Val) :- !.
find_min(avl(Key, Val, Height, L, R), KeyMin, ValMin) :- find_min(L, KeyMin, ValMin).

max(A, B, A) :- A >= B, !.
max(A, B, B) :- A < B.

avl_get(null, _, _) :- false.
avl_get(avl(Key, Val, _, L, R), QKey, QVal) :- Key = QKey, !, QVal = Val.
avl_get(avl(Key, Val, _, L, R), QKey, QVal) :- Key < QKey, !, avl_get(R, QKey, QVal).
avl_get(avl(Key, Val, _, L, R), QKey, QVal) :- Key > QKey, !, avl_get(L, QKey, QVal).

balance(avl(Key, Val, H, L, R), NewTree) :-
    diff(avl(Key, Val, H, L, R), Diff),
    Diff = 2, diff(L, DiffL),
        (DiffL = -1 -> big_rotate_right(avl(Key, Val, H, L, R), NewTree);
        rotate_rigth(avl(Key, Val, H, L, R), NewTree)), !.

balance(avl(Key, Val, H, L, R), NewTree) :-
    diff(avl(Key, Val, H, L, R), Diff),
    Diff = -2,  diff(R, DiffR),
        (DiffR = 1 -> big_rotate_left(avl(Key, Val, H, L, R), NewTree);
        rotate_left(avl(Key, Val, H, L, R), NewTree)), !.

balance(avl(Key, Val, _,  L, R), avl(Key, Val, H1, L, R)) :-
    height(L, LH), height(R, RH),
    max(LH, RH, H), H1 is H + 1.

rotate_left(avl(K1, V1, _, L, avl(K2, V2, _, RL, RR)), avl(K2, V2, H2, avl(K1, V1, H1, L, RL), RR)) :-
    height(L, HL), height(RL, HRL),
    max(HL, HRL, H0),
    H1 is H0 + 1,
    height(avl(K1, V1, H1, L, RL), H1L), height(RR, HRR),
    max(H1L, HRR, H20),
    H2 is 1 + H20.

rotate_right(avl(K1, V1, _, avl(K2, V2, _, LL, LR), R), avl(K2, V2, H2, LL, avl(K1, V1, H1, LR, R))) :-
    height(R, HR), height(LR, HLR),
    max(HR, HLR, H0),
    H1 is 1 + H0,
    height(LL, HLL), height(avl(K1, V1, H1, LR, R), H1R),
    max(HLL, H1R, H20),
    H2 is 1 + H20.

big_rotate_left(avl(K1, V1, _, L, R), NewTree) :-
    rotate_right(R, R1),
    rotate_left(avl(K1, V1, _, L, R1), NewTree).

big_rotate_right(avl(K1, V1, _, L, R), NewTree) :-
    rotate_left(L, L1),
    rotate_right(avl(K1, V1, _, L1, R), NewTree).

avl_put(null, KeyPut, ValPut, avl(KeyPut, ValPut, 1, null, null)) :- !.
avl_put(avl(K, V, H, L, R), KeyPut, ValPut, avl(K, ValPut, H, L, R)) :- KeyPut = K, !.
avl_put(avl(K, V, H, L, R), KeyPut, ValPut, avl(K1, V1, H1, L1, R1)) :-
    KeyPut < K, avl_put(L, KeyPut, ValPut, L_NEW), !,
    balance(avl(K, V, H, L_NEW, R), avl(K1, V1, H1, L1, R1)).
avl_put(avl(K, V, H, L, R), KeyPut, ValPut, avl(K1, V1, H1, L1, R1)) :-
    KeyPut > K,  avl_put(R, KeyPut, ValPut, R_NEW), !,
    balance(avl(K, V, H, L, R_NEW), avl(K1, V1, H1, L1, R1)) .

avl_remove(null, _, null) :- !.
avl_remove(avl(Key, Val, Height, L, R), KeyRemove, R) :- KeyRemove = Key, L = null, !.
avl_remove(avl(Key, Val, Height, L, R), KeyRemove, L) :- KeyRemove = Key, R = null, !.
avl_remove(avl(Key, Val, Height, L, R), KeyRemove, avl(Key1, Val1, Height1, L1, R2)) :- KeyRemove = Key,
    find_min(R, KeyDel, ValNew),
    avl_remove(R, KeyDel, R1),
    balance(avl(KeyDel, ValNew, Height, L, R1), avl(Key1, Val1, Height1, L1, R2)), !.

avl_remove(avl(Key, Val, Height, L, R), KeyRemove, avl(Key1, Val1, Height1, L2, R1)) :-
    KeyRemove < Key, !, avl_remove(L, KeyRemove, L1),
    balance(avl(Key, Val, Height, L1, R), avl(Key1, Val1, Height1, L2, R1)).
avl_remove(avl(Key, Val, Height, L, R), KeyRemove, avl(Key1, Val1, Height1, L1, R2)) :-
    KeyRemove > Key, !, avl_remove(R, KeyRemove, R1),
    balance(avl(Key, Val, Height, L, R1), avl(Key1, Val1, Height1, L1, R2)).

avl_build([], Tree, Tree) :- !.
avl_build([H|T], Tree, NewTree) :-
    (Key, Val) = H,
    avl_put(Tree, Key, Val, NewTree1),
    avl_build(T, NewTree1, NewTree).

avl_build(SortList, S, E, null) :- S >= E, !.
avl_build(SortList, S, E, avl(Key, Val, Height, L, R)) :-
    M is (S + E) // 2,
    find_elem(SortList, M, Elem),
    (Key, Val) = Elem,
    avl_build(SortList, S, M, L),
    M1 is M + 1,
    avl_build(SortList, M1, E, R),
    height(L, HL), height(R, HR),
    max(HL, HR, Height).
    Height is 1 + Height.



avl_ceilingEntry(avl(K, _, _, L, _), KeyQ, (K1, V1)) :-
    avl_ceilingEntry(L, KeyQ, (K1, V1)), !.
avl_ceilingEntry(avl(K, V, _, _, R), KeyQ, (K1, V1)) :-
    K < KeyQ, avl_ceilingEntry(R, KeyQ, (K1, V1)).
avl_ceilingEntry(avl(K, V, _, _, _), KeyQ, (K, V)) :- K >= KeyQ ,!.


map_build(ListMap, TreeMap) :-
    check_sort(List), !, length(ListMap, L), avl_build(ListMap, 0, L, TreeMap);
    avl_build(ListMap, null, TreeMap).
map_get(TreeMap, Key, Val) :- avl_get(TreeMap, Key, Val).
map_put(TreeMap, Key, Val, Result) :- avl_put(TreeMap, Key, Val, Result).
map_remove(TreeMap, Key, Result) :- avl_remove(TreeMap, Key, Result).
map_ceilingEntry(Map, Key, Entry) :- avl_ceilingEntry(Map, Key, Entry).
map_removeCeiling(null, _, null) :- !.
map_removeCeiling(Map, Key, Result) :- map_ceilingEntry(Map, Key, (K, _)), !, map_remove(Map, K, Result); Result = Map.



