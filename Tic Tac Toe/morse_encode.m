function x = morse_encode(c)
  arbore = morse;
  %comararea caracterului cu nodurile arborelui
  if (strcmp(arbore{2}{1}, c)== 1)
    x = '.';
  elseif (strcmp(arbore{2}{2}{1}, c)== 1)
    x = '..';
  elseif (strcmp(arbore{2}{2}{2}{1}, c)== 1)
    x = '...';
  elseif (strcmp(arbore{2}{2}{2}{2}{1}, c)== 1) 
    x = '....';
  elseif (strcmp(arbore{2}{2}{2}{3}{1}, c)== 1)
    x = '...-';
  elseif (strcmp(arbore{2}{2}{3}{1}, c)== 1)
    x = '..-';
  elseif (strcmp(arbore{2}{2}{3}{2}{1}, c)== 1)
    x = '..-.';
  elseif (strcmp(arbore{2}{3}{1}, c)== 1)
    x = '.-';
  elseif (strcmp(arbore{2}{3}{2}{1}, c)== 1)
    x = '.-.';
  elseif (strcmp(arbore{2}{3}{2}{2}{1}, c)== 1)
    x = '.-..';
  elseif (strcmp(arbore{2}{3}{3}{1}, c)== 1)
    x = '.--';
  elseif (strcmp(arbore{2}{3}{3}{2}{1}, c)== 1)
    x = '.--.';
  elseif (strcmp(arbore{2}{3}{3}{3}{1}, c)== 1)
    x = '.---';
  elseif (strcmp(arbore{3}{1}, c)== 1)
    x = '-';
  elseif (strcmp(arbore{3}{2}{1}, c)== 1)
    x = '-.';
  elseif (strcmp(arbore{3}{2}{2}{1}, c)== 1)
    x = '-..';
  elseif (strcmp(arbore{3}{2}{2}{2}{1}, c)== 1)
    x = '-...';
  elseif (strcmp(arbore{3}{2}{2}{3}{1}, c)== 1)
    x = '-..-';
  elseif (strcmp(arbore{3}{2}{3}{1}, c)== 1)
    x = '-.-';
  elseif (strcmp(arbore{3}{2}{3}{2}{1}, c)== 1)
    x = '-.-.';
  elseif (strcmp(arbore{3}{2}{3}{3}{1}, c)== 1)
    x = '-.--';
  elseif (strcmp(arbore{3}{3}{1}, c)== 1)
    x = '--';
  elseif (strcmp(arbore{3}{3}{2}{1}, c)== 1)
    x = '--.';
  elseif (strcmp(arbore{3}{3}{2}{2}{1}, c)== 1)
    x = '--..';
  elseif (strcmp(arbore{3}{3}{2}{3}{1}, c)== 1)
    x = '--.-';
  elseif (strcmp(arbore{3}{3}{3}{1}, c)== 1)
    x = '---';
  else
  %nu s-a gasit niciun caracter potrivit
    x = '*';
  endif
endfunction