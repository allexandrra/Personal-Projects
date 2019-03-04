function valoare = best_move(v, i)
  valoare  = 0;
  a = 0; b = 0; c = 0; d = 0;
  
  if (i == 1)
    if ((strcmp(v(2), '2') == 1) && (strcmp(v(3), '3') == 1))
      a = 1;
    endif
    if ((strcmp(v(4), '4') == 1) && (strcmp(v(7), '7') == 1))
      b = 1;
    endif 
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(9), '9') == 1))
      c = 1;
    endif 
    if ( a == 1 && b == 0 && c == 0)
      lista = [2 3];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0)
      lista = [4 7];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 0 && b == 0 && c == 1)
      lista = [5 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0)
      lista = [2 3 4 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 0 && c == 1)
      lista = [2 3 5 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 1 && c == 1)
      lista = [4 5 7 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1)
      lista = [2 3 4 7 5 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 3)
    if ((strcmp(v(2), '2') == 1) && (strcmp(v(1), '1') == 1))
      a = 1;
    endif
    if ((strcmp(v(6), '6') == 1) && (strcmp(v(9), '9') == 1))
      b = 1;
    endif 
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(7), '7') == 1))
      c = 1;
    endif 
    if ( a == 1 && b == 0 && c == 0)
      lista = [1 2];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0)
      lista = [6 9];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 0 && b == 0 && c == 1)
      lista = [5 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0)
      lista = [1 2 6 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 0 && c == 1)
      lista = [1 2 5 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 1 && c == 1)
      lista = [5 6 7 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1)
      lista = [1 2 5 6 7 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 7)
    if ((strcmp(v(8), '8') == 1) && (strcmp(v(9), '9') == 1))
      a = 1;
    endif
    if ((strcmp(v(1), '1') == 1) && (strcmp(v(4), '4') == 1))
      b = 1;
    endif 
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(3), '3') == 1))
      c = 1;
    endif 
    if ( a == 1 && b == 0 && c == 0)
      lista = [8 9];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0)
      lista = [1 4];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 0 && b == 0 && c == 1)
      lista = [5 3];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0)
      lista = [1 4 8 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 0 && c == 1)
      lista = [3 5 8 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 1 && c == 1)
      lista = [1 4 3 5];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1)
      lista = [1 3 4 5 8 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 9)
    if ((strcmp(v(7), '7') == 1) && (strcmp(v(8), '8') == 1))
      a = 1;
    endif
    if ((strcmp(v(6), '6') == 1) && (strcmp(v(3), '3') == 1))
      b = 1;
    endif 
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(1), '1') == 1))
      c = 1;
    endif 
    if ( a == 1 && b == 0 && c == 0)
      lista = [7 8];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0)
      lista = [3 6];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 0 && b == 0 && c == 1)
      lista = [5 1];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0)
      lista = [3 6 7 8];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 0 && c == 1)
      lista = [1 5 7 8];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 1 && c == 1)
      lista = [1 3 5 6];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1)
      lista = [1 3 5 6 7 8];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 2)
    if ((strcmp(v(3), '3') == 1) && (strcmp(v(1), '1') == 1))
      a = 1;
    endif
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(8), '8') == 1))
      b = 1;
    endif
    if ( a == 1 && b == 0)
      lista = [1 3];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1)
      lista = [5 8];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 1 && b == 1)
      lista = [1 3 5 8];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 4)
    if ((strcmp(v(7), '7') == 1) && (strcmp(v(1), '1') == 1))
      a = 1;
    endif
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(6), '6') == 1))
      b = 1;
    endif
    if ( a == 1 && b == 0)
      lista = [1 7];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1)
      lista = [5 6];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 1 && b == 1)
      lista = [1 5 6 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 6)
    if ((strcmp(v(3), '3') == 1) && (strcmp(v(9), '9') == 1))
      a = 1;
    endif
    if ((strcmp(v(5), '5') == 1) && (strcmp(v(4), '4') == 1))
      b = 1;
    endif
    if ( a == 1 && b == 0)
      lista = [9 3];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1)
      lista = [5 4];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 1 && b == 1)
      lista = [3 4 5 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 8)
    if ((strcmp(v(2), '2') == 1) && (strcmp(v(5), '5') == 1))
      a = 1;
    endif
    if ((strcmp(v(7), '7') == 1) && (strcmp(v(9), '9') == 1))
      b = 1;
    endif
    if ( a == 1 && b == 0)
      lista = [2 5];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1)
      lista = [7 9];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 1 && b == 1)
      lista = [2 5 7 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
  
  if (i == 5)
    if ((strcmp(v(2), '2') == 1) && (strcmp(v(8), '8') == 1))
      a = 1;
    endif
    if ((strcmp(v(4), '4') == 1) && (strcmp(v(6), '6') == 1))
      b = 1;
    endif 
    if ((strcmp(v(1), '1') == 1) && (strcmp(v(9), '9') == 1))
      c = 1;
    endif
    if ((strcmp(v(3), '3') == 1) && (strcmp(v(7), '7') == 1))
      d = 1;
    endif  
    if ( a == 1 && b == 0 && c == 0 && d == 0)
      lista = [2 8];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0 && d == 0)
      lista = [4 6];
      valoare = lista(ceil(rand(1)*length(lista)));  
    elseif ( a == 0 && b == 0 && c == 1 && d == 0)
      lista = [1 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 0 && c == 0 && d == 1)
      lista = [3 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0 && d == 0)
      lista = [2 4 6 8];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 0 && c == 1 && d == 0)
      lista = [2 8 1 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0 && d == 1)
      lista = [2 8 3 7];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 1 && d == 0)
      lista = [4 6 1 9];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 0 && b == 1 && c == 0 && d == 1)
      lista = [4 6 3 7];
      valoare = lista(ceil(rand(1)*length(lista))); 
    elseif ( a == 1 && b == 0 && c == 1 && d == 1)
      lista = [1 9 3 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1 && d == 0)
      lista = [2 8 4 6 1 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 0 && d == 1)
      lista = [2 8 4 6 3 7];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 0 && b == 1 && c == 1 && d == 1)
      lista = [3 7 4 6 1 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    elseif ( a == 1 && b == 1 && c == 1 && d == 1)
      lista = [1 2 3 4 6 7 8 9];
      valoare = lista(ceil(rand(1)*length(lista)));
    endif
  endif
endfunction