function valoare = verifica_mutare(v, i)
  valoare = 0;
  if (strcmp(v(i), num2str(i)) == 1)
    valoare = 1;
  endif
endfunction