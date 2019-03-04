function [] = joc()

  persistent score1 = 0;
  persistent score2 = 0;
  
  board = ['1' '2' '3' '4' '5' '6' '7' '8' '9'];
  
  printf("Start game!\n");
  tabla(board);
  
  jucator1 = input("Cu ce alegi sa joci (X sau O)?\n", "s");
  %programul este case sensitive, trebuie sa fie 'X' sau 'O'
  if (jucator1 == 'X')
    jucator2 = 'O';
  elseif (jucator1 == 'O')
    jucator2 = 'X';
  endif
  %determin cu ce a juca cel de-al 2-lea jucator
  
  if (jucator1 == 'X')
    %prima mutare jucator 1
    mutare1 = input("Jucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %prima mutare jucator 2
    mutare2 = randi([1, 9]);
    check = verifica_mutare(board, mutare2);
    while (check == 0)
      %generare valoare random potrivita
      mutare2 = randi([1, 9]);
      check = verifica_mutare(board, mutare2);
    endwhile
    board(mutare2) = jucator2;
    printf("\nMutare jucator 2\n");
    tabla(board);
  
    %a doua mutare jucator 1
    mutare1 = input("Jucator 1,introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %a doua mutare jucator 2
    muta = verifica_opozitie(board, jucator1, jucator2);
    %valoare random dar care duce la castigat
    if (muta == 0)
      mutare = best_move(board, mutare2);
      if (mutare == 0)
        mutare2 = randi([1, 9]);
        check = verifica_mutare(board, mutare2);
        while (check == 0)
          mutare2 = randi([1, 9]);
          check = verifica_mutare(board, mutare2);
        endwhile
        board(mutare2) = jucator2;
      else 
        board(mutare) = jucator2;
      endif 
    else
      board(str2num(muta)) = jucator2;
    endif
    printf("\nMutare jucator 2\n");
    tabla(board);
  
    %a treia mutare jucator 1
    mutare1 = input("\nJucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %stare joc si/sau mutarea a treia jucator 2
    %se verifica daca jucatorul sau opozitia au sanse de castig
    [win simbol] = verificare_castig(board);
    if (win == 0)
      move = verifica_personal(board, jucator2, jucator1);
      if (move == 0)
        muta = verifica_opozitie(board, jucator1, jucator2);
        if (muta == 0)
          mutare = best_move(board, mutare2);
          if (mutare == 0)
            mutare2 = randi([1, 9]);
            check = verifica_mutare(board, mutare2);
            while (check == 0)
              mutare2 = randi([1, 9]);
              check = verifica_mutare(board, mutare2);
            endwhile
            board(mutare2) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          else 
            board(mutare) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          endif 
        else
          board(str2num(muta)) = jucator2;
          printf("\nMutare jucator 2\n");
          tabla(board);
        endif
      else
        board(str2num(move)) = jucator2;
        printf("\nMutare jucator 2\n");
        tabla(board);
        [winner simb] = verificare_castig(board);
        if (winner == 1)
          if (strcmp(jucator2, simb) == 1)
            printf("\nJucator 2 a castigat!\n");
            printf("\nScorul este:   %d : %d \n", score1, ++score2);
            decizie = input("\nJucati o noua parida?(d/n)\n", "s");
            if (strcmp(decizie, 'd') != 1)
              break;
            elseif (strcmp(decizie, 'd') == 1)
              joc;
            endif
          endif
        endif  
      endif
    else
      if (strcmp(jucator1, simbol) == 1)
        printf("\nJucator 1 a castigat!\n");
        printf("\nScorul este:   %d : %d \n", ++score1, score2);
        decizie = input("\nJucati o noua parida?(d/n)\n", "s");
        if (strcmp(decizie, 'd') != 1)
          break;
        elseif (strcmp(decizie, 'd') == 1)
          joc;
        endif
      endif
    endif
  
    %a patra mutare jucator 1
    mutare1 = input("\nJucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %stare joc si/sau mutarea a patra jucator 2
    [win simbol] = verificare_castig(board);
    if (win == 0)
      move = verifica_personal(board, jucator2, jucator1);
      if (move == 0)
        muta = verifica_opozitie(board, jucator1, jucator2);
        if (muta == 0)
          mutare = best_move(board, mutare2);
          if (mutare == 0)
            mutare2 = randi([1, 9]);
            check = verifica_mutare(board, mutare2);
            while (check == 0)
              mutare2 = randi([1, 9]);
              check = verifica_mutare(board, mutare2);
            endwhile
            board(mutare2) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          else 
            board(mutare) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          endif 
        else
          board(str2num(muta)) = jucator2;
          printf("\nMutare jucator 2\n");
          tabla(board);
        endif
      else
        board(str2num(move)) = jucator2;
        printf("\nMutare jucator 2\n");
        tabla(board);
        [winner simb] = verificare_castig(board);
        if (winner == 1)
          if (strcmp(jucator2, simb) == 1)
            printf("\nJucator 2 a castigat!\n");
            printf("\nScorul este:   %d : %d \n", score1, ++score2);
            decizie = input("\nJucati o noua parida?(d/n)\n", "s");
            if (strcmp(decizie, 'd') != 1)
              break;
            elseif (strcmp(decizie, 'd') == 1)
              joc;
            endif
          endif
        endif  
      endif
    else
      if (strcmp(jucator1, simbol) == 1)
        printf("\nJucator 1 a castigat!\n")
        printf("\nScorul este:   %d : %d \n", ++score1, score2);
        decizie = input("\nJucati o noua parida?(d/n)\n", "s");
        if (strcmp(decizie, 'd') != 1)
          break;
        elseif (strcmp(decizie, 'd') == 1)
          joc;
        endif
      endif
    endif
  
    %ultima mutare
    mutare1 = input("\nJucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("ana\n");
    tabla(board);
  
    [win simbol] = verificare_castig(board);
    if (win == 2)
      printf("\nJocul s-a incheiat la egalitate!\n");
      printf("\nScorul este:   %d : %d \n", score1, score2);
      decizie = input("\nJucati o noua parida?(d/n)\n", "s");
      if (strcmp(decizie, 'd') != 1)
        break;
      elseif (strcmp(decizie, 'd') == 1)
        joc;
      endif
    endif
  
  elseif(jucator2 == 'X')
    %prima mutare jucator 2
    mutare2 = randi([1, 9]);
    check = verifica_mutare(board, mutare2);
    while (check == 0)
      mutare2 = randi([1, 9]);
      check = verifica_mutare(board, mutare2);
    endwhile
    board(mutare2) = jucator2;
    printf("\nMutare jucator 2\n");
    tabla(board);
  
    %prima mutare jucator 1
    mutare1 = input("Jucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %a doua mutare jucator 2
    muta = verifica_opozitie(board, jucator1, jucator2);
    if (muta == 0)
      mutare = best_move(board, mutare2);
      if (mutare == 0)
        mutare2 = randi([1, 9]);
        check = verifica_mutare(board, mutare2);
        while (check == 0)
          mutare2 = randi([1, 9]);
          check = verifica_mutare(board, mutare2);
        endwhile
        board(mutare2) = jucator2;
      else 
        board(mutare) = jucator2;
      endif 
    else
      board(str2num(muta)) = jucator2;
    endif
    printf("\nMutare jucator 2\n");
    tabla(board);
  
    %a doua mutare jucator 1
    mutare1 = input("Jucator 1,introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %stare joc si/sau mutarea a treia jucator 2
    [win simbol] = verificare_castig(board);
    if (win == 0)
      move = verifica_personal(board, jucator2, jucator1);
      if (move == 0)
        muta = verifica_opozitie(board, jucator1, jucator2);
        if (muta == 0)
          mutare = best_move(board, mutare2);
          if (mutare == 0)
            mutare2 = randi([1, 9]);
            check = verifica_mutare(board, mutare2);
            while (check == 0)
              mutare2 = randi([1, 9]);
              check = verifica_mutare(board, mutare2);
            endwhile
            board(mutare2) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          else 
            board(mutare) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          endif 
        else
          board(str2num(muta)) = jucator2;
          printf("\nMutare jucator 2\n");
          tabla(board);
        endif
      else
        board(str2num(move)) = jucator2;
        printf("\nMutare jucator 2\n");
       tabla(board);
        [winner simb] = verificare_castig(board);
        if (winner == 1)
          if (strcmp(jucator2, simb) == 1)
            printf("\nJucator 2 a castigat!\n");
            printf("\nScorul este:   %d : %d \n", score1, ++score2);
            decizie = input("\nJucati o noua parida?(d/n)\n", "s");
            if (strcmp(decizie, 'd') != 1)
              break;
            elseif (strcmp(decizie, 'd') == 1)
              joc;
            endif
          endif
        endif  
      endif
    else
      if (strcmp(jucator1, simbol) == 1)
        printf("\nJucator 1 a castigat!\n");
        printf("\nScorul este:   %d : %d \n", ++score1, score2);
        decizie = input("\nJucati o noua parida?(d/n)\n", "s");
        if (strcmp(decizie, 'd') != 1)
          break;
        elseif (strcmp(decizie, 'd') == 1)
          joc;
        endif
      endif
    endif
  
    %a treia mutare jucator 1
    mutare1 = input("\nJucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %stare joc si/sau a patra mutare jucator 2
    [win simbol] = verificare_castig(board);
    if (win == 0)
      move = verifica_personal(board, jucator2, jucator1);
      if (move == 0)
        muta = verifica_opozitie(board, jucator1, jucator2);
        if (muta == 0)
          mutare = best_move(board, mutare2);
          if (mutare == 0)
            mutare2 = randi([1, 9]);
            check = verifica_mutare(board, mutare2);
            while (check == 0)
              mutare2 = randi([1, 9]);
              check = verifica_mutare(board, mutare2);
            endwhile
            board(mutare2) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          else 
            board(mutare) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          endif 
        else
          board(str2num(muta)) = jucator2;
          printf("\nMutare jucator 2\n");
          tabla(board);
        endif
      else
        board(str2num(move)) = jucator2;
        printf("\nMutare jucator 2\n");
        tabla(board);
        [winner simb] = verificare_castig(board);
        if (winner == 1)
          if (strcmp(jucator2, simb) == 1)
            printf("\nJucator 2 a castigat!\n");
            printf("\nScorul este:   %d : %d \n", score1, ++score2);
            decizie = input("\nJucati o noua parida?(d/n)\n", "s");
            if (strcmp(decizie, 'd') != 1)
              break;
            elseif (strcmp(decizie, 'd') == 1)
              joc;
            endif
          endif
        endif  
      endif
    else
      if (strcmp(jucator1, simbol) == 1)
        printf("\nJucator 1 a castigat!\n");
        printf("\nScorul este:   %d : %d \n", ++score1, score2);
        decizie = input("\nJucati o noua parida?(d/n)\n", "s");
        if (strcmp(decizie, 'd') != 1)
          break;
        elseif (strcmp(decizie, 'd') == 1)
          joc;
        endif
      endif
    endif
  
    %a treia mutare jucator 1
    mutare1 = input("\nJucator 1, introdu valoare de la 1 la 9.\n", "s");
    board(str2num(mutare1)) = jucator1;
    printf("\n");
    tabla(board);
  
    %stare joc si/sau ultima mutare jucator 2
    [win simbol] = verificare_castig(board);
    if (win == 0)
      move = verifica_personal(board, jucator2, jucator1);
      if (move == 0)
        muta = verifica_opozitie(board, jucator1, jucator2);
        if (muta == 0)
          mutare = best_move(board, mutare2);
          if (mutare == 0)
            mutare2 = randi([1, 9]);
            check = verifica_mutare(board, mutare2);
            while (check == 0)
              mutare2 = randi([1, 9]);
              check = verifica_mutare(board, mutare2);
            endwhile
            board(mutare2) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          else 
            board(mutare) = jucator2;
            printf("\nMutare jucator 2\n");
            tabla(board);
          endif 
        else
          board(str2num(muta)) = jucator2;
          printf("\nMutare jucator 2\n");
          tabla(board);
        endif
      else
        board(str2num(move)) = jucator2;
        printf("\nMutare jucator 2\n");
        tabla(board);
        [winner simb] = verificare_castig(board);
        if (winner == 1)
          if (strcmp(jucator2, simb) == 1)
            printf("\nJucator 2 a castigat!\n");
            printf("\nScorul este:   %d : %d \n", score1, ++score2);
            decizie = input("\nJucati o noua parida?(d/n)\n", "s");
            if (strcmp(decizie, 'd') != 1)
              break;
            elseif (strcmp(decizie, 'd') == 1)
              joc;
            endif
          endif
        endif  
      endif
    else
      if (strcmp(jucator1, simbol) == 1)
        printf("\nJucator 1 a castigat!\n");
        printf("\nScorul este:   %d : %d \n", ++score1, score2);
        decizie = input("\nJucati o noua parida?(d/n)\n", "s");
        if (strcmp(decizie, 'd') != 1)
          break;
        elseif (strcmp(decizie, 'd') == 1)
          joc;
        endif
      endif
    endif
  
    [win simbol] = verificare_castig(board);
    if (win == 2)
      printf("\nJocul s-a incheiat la egalitate!\n");
      printf("\nScorul este:   %d : %d \n", score1, score2);
      decizie = input("\nJucati o noua parida?(d/n)\n", "s");
      if (strcmp(decizie, 'd') != 1)
        break;
      elseif (strcmp(decizie, 'd') == 1)
        joc;
      endif
    endif
  endif
endfunction