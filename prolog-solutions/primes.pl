init(MAX_N) :- sieve(2, MAX_N); true.

sieve(I, N) :-
    \+ composite(I), I2 is I * I, addComposite(I, I2, N);
    I * I < N, I1 is I + 1, sieve(I1, N).

addComposite(I, J, N) :-
    J < N, assert(composite(J)), assertz(divider(J, I)),
    J1 is J + I, addComposite(I, J1, N).


prime(N) :- \+ composite(N), N =\= 1.
divider(P, P) :- prime(P).

prime_divisors(N, Divizors) :-
    number(N) , !, findDivisors(N, Divizors);
    findN(N, 2,  Divizors).

findDivisors(1, []).
findDivisors(N, [H|T]) :-
    divider(N, H), !,
    N1 is N / H, findDivisors(N1, T).

findN(1, _, []).
findN(N, P, [H|T]) :-
     H >= P, prime(H),
     findN(R, H, T), N is H * R.


gcd(A, B, GCD) :-
    prime_divisors(A, DivsA), prime_divisors(B, DivsB),
    gcdDivs(DivsA, DivsB, GCD).

gcdDivs([], _, 1) :- !.
gcdDivs(_, [], 1) :- !.
gcdDivs([H1|T1], [H2|T2], GCD) :- H1 = H2, gcdDivs(T1, T2, R), GCD is H1 * R.
gcdDivs([H1|T1], [H2|T2], GCD) :- H1 > H2, gcdDivs([H1|T1], T2, GCD).
gcdDivs([H1|T1], [H2|T2], GCD) :- H1 < H2, gcdDivs(T1, [H2|T2], GCD).