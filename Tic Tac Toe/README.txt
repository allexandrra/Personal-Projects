Exercitiu 1:
Am folosit trecerea din baza b1 in baza 10, si din baza 10 in baza b2. In cazul in care b1 este mai mic decat 10, numarul in baza 10 se obtine din suma de produse dintre cifra de ordin i si b1 ridicat la putere lungime maxima minus i. In cazul in care numarul contine litere, la inmultire folosesc echivalentul in cifre (ex: 'a' = 10), sincronizarea facand-o cu 2 vectori, bazaliterar si bazanumerar. Pentru obtinerea numarului final, completez cu restul impartirii numarului in baza 10 la b2. Daca modulul este mai mic decat 10, se completeaza cu cifra respentiva, in caz contrar, cu litera corespunzatoare numarului din rest. In urma operatiei numarul va rezulta in oglinda, deci trebuie inversat.

Exercitiu 2:
Completarea se face pe diagonalele secundare. Matricea fiind initializata cu 0-uri, primul element nu se mai completeaza. Pentru prima jumatate a matricei (primele n diagonale), se completeaza astfel: daca numarul de ordine al diagonalei este par, inseamna ca indicele liniei creste de la 1, si al coloanei scade incepand cu numarul de ordine respectiv, pana cand acesta atinge valoarea 0. Exista variabila valoare care se incrementeaza cu 1 dupa fiecare copletare a unui element din matrice. Daca numarul de ordine al diagonalei este impar inseamna ca indicele liniei scade incepand cu numarul de ordine, iar indicele coloanei creste de la 1.
Pentru completarea celeilalte jumatati, se iau in considerare 2 cazuri: cel in care matricea are numar de linii si coloane par, si impar. Pentru n impar: pentru diagonalele cu numar de ordine impar, indicele liniei creste de la numarul de ordine respectiv plus 1 pana la n, iar indicele coloanei scade; pentru diagonalele cu numar de ordine par se aplica aceleasi reguli pentru indicii inversati. Pentru n par: acelasi procedeu, insa pentru diagonale cu numar de ordin impar indicele liniei scade si al coloanei creste, si invers pentru diagonale cu numar de ordine par.

Exercitiu 3:
Subpunct a):
Am completat arborele pe nivele, pornind de la ultimul pana la radacina
Subpunct b):
In cazul in care lungimea sursei este mai mare de 4, atunci elementul este invalid. In caz contrar pentru fiecare caracter al sursei verific ce este si ma duc pe ramura corespunzatoare. Daca la final am nod nul, atunci nu s-a gasit nimic si se returneaza '*', altfel se returneaza litera din nodul respectiv.
Subpunct c):
Compar nodurile arborelui cu caracterul primit. In cazul in care se face match, se returneaza codul corespunzator caracterului, altfel inseamna ca e caracter invalid, deci se returneaza '*'.
Subpunct d):
Pentru multiple_decode extrag subsirul care corespunde unui caracter si aplic functia morse_decode pentru a determina caracterul.
Pentru multiple_encode apelez pentru fiecare caracter in parte morse_encode, si apoireturnez sirul rezultat prin concatenare.

Exercitiu 4:
Utilizatorul isi alege simbolul, iar calculatorul va lua simbolul ramas. X incepe fiecare partida.
Am definit mai multe functii:
- verifica_mutare: alegerea calculatorului este facuta random si aceasta funtie verifica daca este o mutare valida sau nu. Returneaza 0 daca nu si 1 daca da.
- best_move: verifica daca exista o diagonala cu un singur element ocupat de catre calculator care ar putea duce la castig. Daca exista mai multe o va lua pe prima care apare.
Returneaza 0  daca nu exista, sau numarul casutei respective.
- verifica_opozitie: daca exista o diagonala in care 2 casute sunt ocupate de simbolul jucatorului advers, calculatorul o va ocupa pe cea libera. Daca exista mai multe posibilitati, va alege una dintre ele. Returneaza 0 daca nu gaseste, si numarul casutei in caz contrar.
- verifica_personal: daca exista o diagonala in care 2 casute sunt ocupate cu simbolul calculatorului, acesta va ocupa acea casuta si astfel a castigat, partida se incheie. Returneaza similar functiei verifica_opozitie
- verificare_castig: daca s-a ajuns la o diagonala completata cu acelasi simbol se returneaza 1 si jucatorul respectiv a castigat, daca returneaza 0 nu s-a realizat completarea totala, si daca returneaza 2 s-a completat total tabla dar fara castigator
In funtia joc aceste functii sunt apelate pe masura ce jocul evolueaza.