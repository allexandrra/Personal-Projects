function x = multiple_decode(sir)
  str = '';
  while (isempty(sir) == 0)
    %extragere subsir si aplicare functie morse_decode
    [char sir] = strtok(sir);
    c = morse_decode(char);
    str = strcat(str, c);
  endwhile
  x = str;  
endfunction