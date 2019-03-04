function [valoare i]= verificare_castig(v)
  valoare = 0;
  i = 0;
  if ((strcmp(v(1), v(2)) == 1) && (strcmp(v(2), v(3)) == 1))
    valoare = 1;
    i = v(1);
  elseif ((strcmp(v(4), v(5)) == 1) && (strcmp(v(5), v(6)) == 1))
    valoare = 1;
    i = v(4);
  elseif ((strcmp(v(7), v(8)) == 1) && (strcmp(v(8), v(9)) == 1))
    valoare = 1;
    i = v(7);
  elseif ((strcmp(v(1), v(4)) == 1) && (strcmp(v(4), v(7)) == 1))
    valoare = 1;
    i = v(1);
  elseif ((strcmp(v(2), v(5)) == 1) && (strcmp(v(5), v(8)) == 1))
    valoare = 1;
    i = v(2);
  elseif ((strcmp(v(3), v(6)) == 1) && (strcmp(v(6), v(9)) == 1))
    valoare = 1;
    i = v(3);
  elseif ((strcmp(v(1), v(5)) == 1) && (strcmp(v(5), v(9)) == 1))
    valoare = 1;
    i = v(1);
  elseif ((strcmp(v(3), v(5)) == 1) && (strcmp(v(5), v(7)) == 1))
    valoare = 1;
    i = v(3);
  elseif ((strcmp(v(1), '1') == 0) && (strcmp(v(2), '2') == 0) && 
    (strcmp(v(3), '3') == 0) && (strcmp(v(4), '4') == 0) && (strcmp(v(5), '5') == 0) 
    && (strcmp(v(6), '6') == 0) && (strcmp(v(7), '7') == 0) && (strcmp(v(8), '8') == 0) 
    && (strcmp(v(9), '9') == 0))
    valoare = 2;
  endif
endfunction