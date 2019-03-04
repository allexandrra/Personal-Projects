function valoare = verifica_personal(v, i, j)
  valoare = 0;
  if (strcmp(v(1), v(2)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(3), j) == 0)
    valoare = v(3);
  elseif (strcmp(v(1), v(3)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(2), j) == 0)
    valoare = v(2);
  elseif (strcmp(v(1), v(4)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(7), j) == 0)
    valoare = v(7);
  elseif (strcmp(v(1), v(7)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(4), j) == 0)
    valoare = v(4);
  elseif (strcmp(v(1), v(5)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(9), j) == 0)
    valoare = v(9);
  elseif (strcmp(v(1), v(9)) == 1 && strcmp(v(1), i) == 1 && strcmp(v(5), j) == 0)
    valoare = v(5);
  elseif (strcmp(v(2), v(3)) == 1 && strcmp(v(2), i) == 1 && strcmp(v(1), j) == 0)
    valoare = v(1);
  elseif (strcmp(v(2), v(5)) == 1 && strcmp(v(2), i) == 1 && strcmp(v(8), j) == 0)
    valoare = v(8);
  elseif (strcmp(v(2), v(8)) == 1 && strcmp(v(2), i) == 1 && strcmp(v(5), j) == 0)
    valoare = v(5);
  elseif (strcmp(v(3), v(6)) == 1 && strcmp(v(3), i) == 1 && strcmp(v(9), j) == 0)
    valoare = v(9);
  elseif (strcmp(v(3), v(9)) == 1 && strcmp(v(3), i) == 1 && strcmp(v(6), j) == 0)
    valoare = v(6);
  elseif (strcmp(v(3), v(5)) == 1 && strcmp(v(3), i) == 1 && strcmp(v(7), j) == 0)
    valoare = v(7);
  elseif (strcmp(v(3), v(7)) == 1 && strcmp(v(3), i) == 1 && strcmp(v(5), j) == 0)
    valoare = v(5);
  elseif (strcmp(v(4), v(5)) == 1 && strcmp(v(4), i) == 1 && strcmp(v(6), j) == 0)
    valoare = v(6);
  elseif (strcmp(v(4), v(6)) == 1 && strcmp(v(4), i) == 1 && strcmp(v(5), j) == 0)
    valoare = v(5);
  elseif (strcmp(v(4), v(7)) == 1 && strcmp(v(4), i) == 1 && strcmp(v(1), j) == 0)
    valoare = v(1);
  elseif (strcmp(v(5), v(8)) == 1 && strcmp(v(5), i) == 1 && strcmp(v(2), j) == 0)
    valoare = v(2);
  elseif (strcmp(v(5), v(9)) == 1 && strcmp(v(5), i) == 1 && strcmp(v(1), j) == 0)
    valoare = v(1);
  elseif (strcmp(v(5), v(6)) == 1 && strcmp(v(5), i) == 1 && strcmp(v(4), j) == 0)
    valoare = v(4);
  elseif (strcmp(v(5), v(7)) == 1 && strcmp(v(5), i) == 1 && strcmp(v(3), j) == 0)
    valoare = v(3);
  elseif (strcmp(v(6), v(9)) == 1 && strcmp(v(6), i) == 1 && strcmp(v(3), j) == 0)
    valoare = v(3);
  elseif (strcmp(v(7), v(8)) == 1 && strcmp(v(7), i) == 1 && strcmp(v(9), j) == 0)
    valoare = v(9);
  elseif (strcmp(v(7), v(9)) == 1 && strcmp(v(7), i) == 1 && strcmp(v(8), j) == 0)
    valoare = v(8);
  elseif (strcmp(v(8), v(9)) == 1 && strcmp(v(8), i) == 1 && strcmp(v(7), j) == 0)
    valoare = v(7);
  endif
endfunction