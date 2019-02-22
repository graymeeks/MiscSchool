%----- Colors
color(red).
color(yellow).
color(blue).
color(silver).

%----- Wives
wife(clara).
wife(suzie).
wife(rose).
wife(olivia).

%----- Husbands
husband(robert).
husband(david).
husband(fernando).
husband(frank).

%----- Food
food(potato_sal).
food(mac_sal).
food(snicker_sal).
food(coleslaw).


solve :-

food(ClaraFood), food(SuzieFood),
food(RoseFood), food(OliviaFood),
all_different([ClaraFood, SuzieFood, RoseFood, OliviaFood]),

husband(ClaraHusband), husband(SuzieHusband),
husband(RoseHusband), husband(OliviaHusband),
all_different([ClaraHusband, SuzieHusband, RoseHusband, OliviaHusband]),

color(ClaraColor), color(SuzieColor), color(RoseColor), color(OliviaColor),
all_different([ClaraColor, SuzieColor, RoseColor, OliviaColor]),

Answers = [ [clara,ClaraFood,ClaraHusband,ClaraColor], 
			[suzie,SuzieFood,SuzieHusband,SuzieColor], 
			[rose,RoseFood,RoseHusband,RoseColor], 
			[olivia,OliviaFood,OliviaHusband,OliviaColor] ],
			

%(a) Olivia brought a blue container
member([olivia,_,_,blue], Answers),

%(b) Person who brought yellow container brought either potato salad or coleslaw
\+ member([_,snicker_sal,_,yellow], Answers),
\+ member([_,mac_sal,_,yellow], Answers),

%(c) Rose and David brought macaroni salad
member([rose,mac_sal,david,_], Answers),

%(d) Snicker salad was brought in a red container
member([_,snicker_sal,_,red], Answers),

%(e) Fernando and his non-Olivia wife brought coleslaw
\+ member([olivia,_,fernando,_], Answers),
member([_,coleslaw,fernando,_], Answers),

%(f) Suzie is married to Frank
member([suzie,_,frank,_], Answers),

tell(clara,ClaraHusband,ClaraFood,ClaraColor),
tell(suzie,SuzieHusband,SuzieFood,SuzieColor),
tell(rose,RoseHusband,RoseFood,RoseColor),
tell(olivia,OliviaHusband,OliviaFood,OliviaColor).
			
all_different([H|T]) :- member(H,T), !, fail.
all_different([_|T]) :- all_different(T).
all_different([_]).	

tell(W,X,Y,Z) :-
	write(W), write(' is married to '), write(X),
	write(' and brought '), write(Y), write(' in a '), 
	write(Z), write(' container.'), nl.
			
			
