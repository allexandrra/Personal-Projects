function x = morse_decode(sir)
  lungime = length(sir);
  char = morse;
  if (lungime <= 4)
    %verific fiecare caracter ar sursei
    for i=1:lungime
      if (strcmp(sir(i),'.') == 1)
        %este '.' deci ma deplasez pe stanga
        char = char{2};
      elseif (strcmp(sir(i),'-') == 1)
        %este '-' deci ma deplasez pe dreapta
        char = char{3};
      endif 
    endfor
    if (isempty(char) == 1)
      %am terminat si nodul in care am ajuns este nul
      x = '*';
    elseif (isempty(char) == 0)
      %am terminat si nodul este cel corect
      x = char{1};
    endif
   else
    %sursa este invalida
    x = '*';
   endif 
endfunction