#include <stdio.h>
#include <stdlib.h>

int main()
{
	int opCode, width, height, start_col, start_line, end_col, end_line, 
width_r, height_r, r, g, b, num_rot, num_iter;
	int i, j, k;
	int vs=3;
	int ***img;

	while (vs==3) {
		scanf("%d", &opCode);	

		if((opCode >= 0) && (opCode <= 8) ) {
			if (opCode == 0){
				vs=2;
			}

		if (opCode == 1){

			scanf("%d %d", &width, &height);

			if ((width < 1 ) || (width > 1024) || (height > 1024) || (height < 1)) {
							
				fprintf(stderr, "%d\n", 2);
				exit(EXIT_FAILURE);
			}
			else {
				
			img = malloc(height * sizeof(int **));

			for (i = 0; i < height; i++) {
				img[i] = calloc(width, sizeof(int*));
			}

			for (i = 0; i < height; i++) {
                        	for (j = 0; j < width; j++){
                        		img[i][j] = calloc(3, sizeof(int));
                    		}
			}	

                	for (i = 0; i < height; i++){
                    		for (j = 0; j < width; j++){
                        		for(k = 0; k < 3; k++){
                            			scanf("%d", &img[i][j][k]);
                       			 }		
                    		}	
                	}
			}
            	}

		if (opCode == 2) {
			int a = 0, b = 0;
			int ***img2;

			scanf("%d %d %d %d", &start_col, &start_line, &end_col, &end_line);

			if ((start_line < 0) || (start_col < 0) || (end_line < 0) || (end_col < 0) || (start_line >= height) || (end_line >= height) || (start_col >= width) || (end_col >= width) || (start_line > end_line) || (start_col > end_col)) {
								
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}
			else{
		
			for (i = start_line; i < end_line+1; i++){
				a++;
			}
			for (j = start_col; j < end_col+1; j++){
				b++;
			}
                
			img2 = malloc(a * sizeof(int **));

			for (i = 0; i < a; i++) {
				img2[i] = calloc(b, sizeof(int*));
			}

			for (i = 0; i < a; i++) {
                        	for (j = 0; j < b; j++){
                            		img2[i][j] = calloc(3, sizeof(int));
                    		}
                	}

		
                	for (i = start_line; i < end_line+1; i++){
                		for (j = start_col; j < end_col+1; j++){
                			for (k = 0; k < 3; k++){
                				img2[i - start_line][j - 
start_col][k] = img[i][j][k];
      					}
                		}
              		}
		
			for (i = 0; i < height; i++){
                    		free(img[i]);
                	}
			free(img);
		
			img = img2;
			width = b;
			height = a;
}		
		}

		if (opCode == 3) {
			int ***img3;			

			scanf("%d %d", &width_r, &height_r);

			if ((width_r < 1) || (width_r > 1024) || (height_r < 1) || (height_r > 1024)) {
								
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}

			else {
                
			img3 = malloc(height_r * sizeof(int **));

			for (i = 0; i < height_r; i++) {
				img3[i] = calloc(width_r, sizeof(int*));
			}

			for (i = 0; i < height_r; i++) {
                        	for (j = 0; j < width_r; j++){
                            		img3[i][j] = calloc(3, sizeof(int));
                    		}
                	}

			for (i = 0; i < height_r; i++)
				for (j = 0; j < width_r; j++)
					for (k = 0; k < 3; k++)
						img3[i][j][k] = 255;
					


			if ((width_r < width) && (height_r < height)) {
                 		for(i = 0; i < height_r; i++) {
                  			for(j = 0; j < width_r; j++) {
                   				for(k = 0; k < 3; k++){
                      					img3[i][j][k] = img[i][j][k];
                    				}
                  			}
                 		}
                 		
                	}
                
			else if	((width_r < width) && (height_r > height)) {
                		for(i = 0; i < height; i++) {
                  			for(j = 0; j < width_r; j++) {
                   				for(k = 0; k < 3; k++) {
                      					img3[i][j][k] = img[i][j][k];
                    				}
                  			}
                 		}
                 		
				
                 	
                	}
                
			else if ((width_r > width) && (height_r < height)) {
                		for(i = 0; i < height_r; i++) {
                  			for(j = 0; j < width; j++) {
                   				for(k = 0; k < 3; k++) {
                      					img3[i][j][k] = img[i][j][k];
                    				}
                  			}
                 		}
                 		
                	}
                
			else if ((width_r > width) && (height_r > height)) {
                		for(i = 0; i < height; i++) {
                  			for(j = 0; j < width; j++) {
                   				for(k = 0; k < 3; k++) {
                      					img3[i][j][k]=img[i][j][k];
                    				}
                  			}
                 		}
				
                	}
		
			for (i = 0; i < height; i++){
                    		free(img[i]);
                	}
			free(img);
		
			img = img3;
			width = width_r;
			height = height_r;		

}		
		}
	
		if (opCode == 4) {
			scanf("%d %d %d %d %d %d %d", &start_col, &start_line, 
&end_col, &end_line, &r, &g, &b);

			if ((start_line < 0) || (start_col < 0) || (end_line < 0) || (end_col < 0) || (start_line >= height) || (end_line >= height) || (start_col >= width) || (end_col >= width) || (start_line > end_line) || (start_col > end_col) || (r < 0) || (r > 255) || (g < 0) || (g > 255) || (b < 0) || (b > 255))
{
							
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}
			else{

			for (i = start_line; i < end_line + 1; i++) {
				for (j = start_col; j < end_col + 1; j++) {
					img[i][j][0] = r;
					img[i][j][1] = g;
					img[i][j][2] = b;
				}
			}
}
		}

		if (opCode == 5) {
			scanf("%d", &num_iter);
			int ***img5;
			int s;
			
			if ((num_iter < 0) || (num_iter > 2000)) {
				fflush(stdout);				
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}
			else

				if (num_iter == 0)
				{
					;
				}
				else {
			
			for (s = 0; s < num_iter; s++) {

                    		img5 = malloc(height * sizeof(int **));

                    		for (i = 0; i < height; i++) {
                        		img5[i] = calloc(width, sizeof(int*));
                    		}

                    		for (i = 0; i < height; i++) {
                        		for (j = 0; j < width; j++){
                            			img5[i][j] = calloc(3, sizeof(int));
                        		}
                    		}

                    		for (k = 0; k < 3; k++) {
                        		img5[0][0][k]=(img[0][1][k] + img[1][0][k]) / 2;
                        		img5[0][width-1][k]=(img[0][width-2][k] + img[1][width-1][k]) / 2;
                        		img5[height-1][width-1][k]=(img[height-1][width-2][k] + img[height-2][width-1][k]) / 2;
                        		img5[height-1][0][k]=(img[height-2][0][k] + img[height-1][1][k]) / 2;
                        	}

                    		for (j = 1; j < width-1; j++) {
                        		for (k = 0; k < 3; k++) {
                            			img5[0][j][k]=(img[0][j-1][k] + img[0][j+1][k] + img[1][j][k]) / 3;
                        		}
                    		}

                    		for (j = 1; j < width-1; j++) {
                        		for (k = 0; k < 3; k++) {
                            			img5[height-1][j][k]=(img[height-1][j-1][k] + img[height-1][j+1][k] + img[height-2][j][k]) / 3;
                        		}
                    		}

                    		for (i = 1; i < height-1; i++) {
                        		for (k = 0; k < 3; k++) {
                            			img5[i][0][k]=(img[i-1][0][k] + img[i+1][0][k] + img[i][1][k]) / 3;
                        		}
                    		}

                    		for (i = 1; i < height-1; i++) {
                        		for (k = 0; k < 3; k++) {
                            			img5[i][width-1][k]=(img[i-1][width-1][k] + img[i+1][width-1][k] + img[i][width-2][k]) / 3;
                        		}
                    		}

                    		for (i = 1; i < height-1; i++) {
                        		for (j = 1; j < width-1; j++) {
                            			for (k = 0; k < 3; k++) {
                                			img5[i][j][k]=(img[i-1][j][k] + img[i][j+1][k] + img[i+1][j][k] + img[i][j-1][k]) / 4;
                            			}
                        		}
                    		}

                    		for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
                    		img = img5;

                	}
}
		}	
		
		if (opCode == 6) {
			scanf("%d", &num_rot);

			int ***img6;
			int aux, s;

			if ((num_rot < 1) || (num_rot > 3)) {
							
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}
			else
                
			{
			for (s = 0; s < num_rot; s++) {
                
				img6 = malloc(width * sizeof(int **));

                		for (i = 0; i < width; i++) {
                    			img6[i] = calloc(height, sizeof(int*));
                		}

                		for (i = 0; i < width; i++) {
                    			for (j = 0; j < height; j++) {
                        			img6[i][j] = calloc(3, sizeof(int));
                    			}
                		}

                    		for (i = 0; i < height; i++) {
                        		for(j = 0; j < width; j++) {
                            			img6[j][height-i-1][0]=img[i][j][0];
                            			img6[j][height-i-1][1]=img[i][j][1];
                            			img6[j][height-i-1][2]=img[i][j][2];
                            			}
                    			}

                		for (i = 0; i < height; i++) {
                			free(img[i]);
            			}
            			free(img);
            
				img = img6;
            
				aux=width;
            			width=height;
            			height=aux;
                	}
}
		}

		if(opCode == 7) {
			scanf("%d %d %d %d %d", &start_col, &start_line, &r, &g, &b);
			
		
			if ((start_line < 0) || (start_col < 0) || (start_line >= height) || (start_col >= width) || (r < 0) || (r > 255) || (g < 0) || (g > 255) || (b < 0) || (b > 255)) {
				fprintf(stderr, "%d\n", 2);
				for (i = 0; i < height; i++) {
                        		free(img[i]);
                    		}
                    		free(img);
				exit(EXIT_FAILURE);
			}
			break;
		}

		if (opCode == 8) {
			printf("%d %d\n", width, height);
			
			for (i = 0; i < height; i++){
                    		for (j = 0; j < width; j++){
                        		for(k = 0; k < 3; k++){
                           			printf("%d ", img[i][j][k]);
                        		}
                    		}
				printf("\n");
                	}

		
		}
         
		}
	
    else {
		fflush(stdout);
		fprintf(stderr, "%d\n", 1);
		exit(EXIT_FAILURE);
	}
	
}
        

return 0;
}

