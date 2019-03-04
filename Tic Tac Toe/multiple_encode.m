function x = multiple_encode(str)
  lungime = length(str);
  sir = '';
  for i = 1:lungime
    %pentru fiecare caracter se apeleaza functia morse_encode
    c = morse_encode(str(i));
    sir = cstrcat(sir, ' ',c);
  endfor
  x = sir;
endfunction