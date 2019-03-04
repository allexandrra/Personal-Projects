function Z = zigzag(n)
  Z = zeros(n);
  ok = 1;
  i = 1;
  j = 1;
  valoare = 1;
  %primele n diagoale de la stanga la dreapta
  while (ok == 1)
    for (k=2:n) 
      if (mod(k,2) == 0) %diagonala cu numar de ordine par
        i=1;
        j=k;
        %indicele liniei creste si al coloanei scade
        while (j!=0)
          Z(i,j)=valoare;
          j--;
          i++;
          valoare++;
          endwhile 
       else
       if (mod(k,2) == 1) %diagonala cu numar de ordine impar
        i=k;
        j=1;
       %indicele liniei scade si al coloanei creste
        while(i!=0)
         Z(i,j) = valoare;
          j++;
          i--;
          valoare++;
          endwhile  
       endif
       endif
      endfor 
    ok = 0;
    endwhile
    %uratoarele n-1 diagonale
    while (ok == 0)
      if(i<j)
      %pentru matrice cu n impar
        for (k=1:n-1)
          if (mod(k,2) == 1) 
            %diagonala cu numar de ordine impar
            i=k+1;
            j=n;
            %indicele liniei creste si al coloanei scade
            while (i!=n+1)
              Z(i,j)=valoare;
              j--;
              i++;
              valoare++;
            endwhile 
          else
           if (mod(k,2) == 0)
            %diagonala cu numar de ordine par
            i=n;
            j=k+1; 
            %indicele liniei scade si al coloanei creste
            while(j!=n+1)
              Z(i,j) = valoare;
              j++;
              i--;
              valoare++;
            endwhile  
          endif  
          endif
        endfor
      elseif (i>j)
        %matrice cu n par
        for (k=1:n-1)
          if (mod(k,2) == 1) 
            %diagonala cu numar de ordine impar
            i=n;
            j=k+1; 
            %indicele liniei scade si al coloanei creste
            while(j!=n+1)
              Z(i,j) = valoare;
              j++;
              i--;
              valoare++;
            endwhile   
          elseif (mod(k,2) == 0)
            %diagonala cu numar de ordine par
            i=k+1;
            j=n;
            %indicele liniei creste si al coloanei scade
            while (i!=n+1)
              Z(i,j)=valoare;
              j--;
              i++;
              valoare++;
            endwhile  
          endif
        endfor 
      endif  
    ok = 1;  
    endwhile
endfunction