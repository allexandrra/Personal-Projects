function r = baza(sursa, b1, b2)
  lungsursa = length(sursa);
  nrb2 = ''; %retin numarul in noua baza
  nrb1 = 0; %numarul din baza initiala trecut in baza 10
  nr = ''; %variabila auxiliara
  bazaliterar = ['abcdefghijklmnopqrst'];
  bazanumerar = [10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29];
  %vectori folositi pentru numere in baze mai mari de 10
  if (b1 <= 10)
    for i = 1:lungsursa
      nrb1 += str2num(sursa(i))*(b1^(lungsursa - i));
      %calculul numarului in baza 10 cu formula
    endfor
  else 
    for i = 1:lungsursa
      ok = 0;
      for j = 1:20
        if (strcmp(sursa(i),bazaliterar(j)) == 1)
          nrb1 += bazanumerar(j)*(b1^(lungsursa - i));
          %cazul in care numarul contine litere
          ok = 1;
          break
        endif
      endfor
      if(ok == 0) 
        nrb1 += str2num(sursa(i))*(b1^(lungsursa - i));
      endif 
    endfor 
  endif
  %trecerea din baza 10 in b2, prin impartiri succesive
  while (nrb1 != 0)
    if (mod(nrb1, b2) < 10)
      nr = strcat(nr, num2str(mod(nrb1, b2)));
    else 
      nr = strcat(nr, bazaliterar(mod(nrb1, b2)-9));
    endif 
    nrb1 = (nrb1 - mod(nrb1, b2))/b2;
  endwhile
  %cifrele numarului de returnat sunt in ordine inversa fata de cea a lui nr
  for i = 0:(length(nr) - 1)
    nrb2(i+1) = nr(length(nr)-i);
  endfor  
  r = nrb2;  
endfunction