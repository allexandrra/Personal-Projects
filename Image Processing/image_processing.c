#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "bmp_header.h"

int main()
{
    int i,j,x,threshold; /*x-variabila folosita in calcularea formulelor pentru 
filtre*/
    short p, q; //se memoreaza nr_linie si nr_coloana, task 4
    unsigned char auxiliar; //folosita pentru inversarea R si G
    char r, b, g; //se memoreaza cele 3 canale de culoare, task 4

    char input_file[] = "input.txt";
    FILE *file = fopen(input_file, "r");
    char *nume = malloc(50), *completare = malloc(50), *f1 = malloc(50), 
*f2 = malloc(50), *f3 = malloc(50), *compress = malloc(50), decompressed[] = 
"decompressed.bmp";
    fscanf(file, "%s", nume);
    fscanf(file, "%d", &threshold);
    fscanf(file, "%s", compress);
    fclose(file);

    strncpy(completare, nume, strlen(nume) - 4);
    completare[strlen(nume) - 4] = '\0';
    strcat(completare, "_black_white.bmp");

    struct bmp_fileheader fileheader;
    struct bmp_infoheader infoheader;

    FILE *image = fopen(nume, "rb");

    fread(&fileheader, sizeof(struct bmp_fileheader), 1, image);
    fread(&infoheader, sizeof(struct bmp_infoheader), 1, image);

    fseek(image, fileheader.imageDataOffset, SEEK_SET);

    int padding = 0; //verificare padding
    while((infoheader.width + padding) % 4 != 0)
    {
        padding++;
    }

    unsigned char **paddingmat, **matrix, **nmatrix, **filtru, **f_1, **f_2, 
**f_3, **aux, **help1, **help2, **help3, **matrice, **help4;
    char **F1, **F2, **F3;
    short **v;

    paddingmat = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        paddingmat[i] = calloc(3 * infoheader.width + padding, sizeof(unsigned char));
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width + padding; j++)
        {
            fread(&paddingmat[i][j], sizeof(unsigned char), 1, image);
        }
    }
    matrix = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        matrix[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width + padding; j++)
        {
            matrix[i][j] = paddingmat[i][j];
        }
    }

    //task 1

    nmatrix = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        nmatrix[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }

    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j+=3)
        {
             nmatrix[i][j + 0] = (matrix[i][j + 0] + matrix[i][j + 1] + 
matrix[i][j + 2]) / 3;
             nmatrix[i][j + 1] = (matrix[i][j + 0] + matrix[i][j + 1] + 
matrix[i][j + 2]) / 3;
             nmatrix[i][j + 2] = (matrix[i][j + 0] + matrix[i][j + 1] + 
matrix[i][j + 2]) / 3;
        }
    }

    free(matrix);
    fclose(image);

    FILE *image1 = fopen(completare, "wb");
    fwrite(&fileheader, sizeof(struct bmp_fileheader), 1, image1);
    fwrite(&infoheader, sizeof(struct bmp_infoheader), 1, image1);
    fseek(image1, fileheader.imageDataOffset, SEEK_SET);
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            fwrite(&nmatrix[i][j], sizeof(unsigned char), 1, image1);
        }
    }
    fclose(image1);

    //task 2
    F1 = malloc(3 * sizeof(char *));
    for(i = 0; i < 3; i++)
    {
        F1[i] = calloc(3, sizeof(char));
    }
    F1[0][0]=F1[0][1]=F1[0][2]=F1[1][0]=F1[1][2]=F1[2][0]=F1[2][1]=F1[2][2]=-1;
    F1[1][1]=8;

    F2 = malloc(3 * sizeof(char *));
    for(i = 0; i < 3; i++)
    {
        F2[i] = calloc(3, sizeof(char));
    }
    F2[0][0]=F2[0][2]=F2[2][0]=F2[2][2]=0;
    F2[0][1]=F2[1][0]=F2[1][2]=F2[2][1]=1;
    F2[1][1]=-4;

    F3 = malloc(3 * sizeof(char *));
    for(i = 0; i < 3; i++)
    {
        F3[i] = calloc(3, sizeof(char));
    }
    F3[0][0]=F3[2][2]=1;
    F3[0][2]=F3[2][0]=-1;
    F3[0][1]=F3[1][0]=F3[1][1]=F3[1][2]=F3[2][1]=0;

    strncpy(f1, nume, strlen(nume) - 4);
    f1[strlen(nume) - 4] = '\0';
    strcat(f1, "_f1.bmp");

    strncpy(f2, nume, strlen(nume) - 4);
    f2[strlen(nume) - 4] = '\0';
    strcat(f2, "_f2.bmp");

    strncpy(f3, nume, strlen(nume) - 4);
    f3[strlen(nume) - 4] = '\0';
    strcat(f3, "_f3.bmp");

    filtru = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        filtru[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }

    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            filtru[i][j] = nmatrix[infoheader.height - 1 - i][j];
        }
    }

    aux = malloc((infoheader.height + 2) * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height + 2; i++)
    {
        aux[i] = calloc(3 * (infoheader.width + 2), sizeof(unsigned char));
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            aux[i+1][j+3] = filtru[i][j];
        }
    }
    free(filtru);

    f_1 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        f_1[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    help1 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        help1[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    for(i = 1; i < infoheader.height + 1; i++)
    {
        for(j = 3; j < 3 * infoheader.width + 3; j++)
        {
            x = (int)aux[i - 1][j - 3] * F1[0][0] + (int)aux[i - 1][j] * F1[0][1] 
+ (int)aux[i - 1][j + 3] * F1[0][2] + (int)aux[i][j - 3] * F1[1][0] + (int)aux[i][j] 
* F1[1][1] + (int)aux[i][j + 3] * F1[1][2] + (int)aux[i + 1][j - 3] * F1[2][0] + 
(int)aux[i + 1][j] * F1[2][1] + (int)aux[i + 1][j + 3] * F1[2][2];
            if(x < 0)
            {
                help1[i - 1][j - 3] = 0;
            }
            else if(x > 255)
            {
                help1[i - 1][j - 3] = 255;
            }
            else
            {
                help1[i - 1][j - 3] = (unsigned char)x;
            }
        }
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            f_1[i][j] = help1[infoheader.height - 1 - i][j];
        }
    }
    free(help1);
    FILE *image2 = fopen(f1, "wb");
    fwrite(&fileheader, sizeof(struct bmp_fileheader), 1, image2);
    fwrite(&infoheader, sizeof(struct bmp_infoheader), 1, image2);
    fseek(image2, fileheader.imageDataOffset, SEEK_SET);
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            fwrite(&f_1[i][j], sizeof(unsigned char), 1, image2);
        }
    }
    fclose(image2);

    f_2 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        f_2[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    help2= malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        help2[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    for(i = 1; i < infoheader.height + 1; i++)
    {
        for(j = 3; j < 3 * infoheader.width + 3; j++)
        {
            x = (int)aux[i - 1][j - 3] * F2[0][0] + (int)aux[i - 1][j] * F2[0][1] 
+ (int)aux[i - 1][j + 3] * F2[0][2] + (int)aux[i][j - 3] * F2[1][0] + (int)aux[i][j] * 
F2[1][1] + (int)aux[i][j + 3] * F2[1][2] + (int)aux[i + 1][j - 3] * F2[2][0] + 
(int)aux[i + 1][j] * F2[2][1] + (int)aux[i + 1][j + 3] * F2[2][2];
            if(x < 0)
            {
                help2[i - 1][j - 3] = 0;
            }
            else if(x > 255)
            {
                help2[i - 1][j - 3] = 255;
            }
            else
            {
                help2[i - 1][j - 3] = (unsigned char)x;
            }
        }
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            f_2[i][j] = help2[infoheader.height - 1 - i][j];
        }
    }
    free(help2);
    FILE *image3 = fopen(f2, "wb");
    fwrite(&fileheader, sizeof(struct bmp_fileheader), 1, image3);
    fwrite(&infoheader, sizeof(struct bmp_infoheader), 1, image3);
    fseek(image3, fileheader.imageDataOffset, SEEK_SET);
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            fwrite(&f_2[i][j], sizeof(unsigned char), 1, image3);
        }
    }
    fclose(image3);

    f_3 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        f_3[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    help3 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        help3[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    for(i = 1; i < infoheader.height + 1; i++)
    {
        for(j = 3; j < 3 * infoheader.width + 3; j++)
        {
            x = (int)aux[i - 1][j - 3] * F3[0][0] + (int)aux[i - 1][j] * F3[0][1] 
+ (int)aux[i - 1][j + 3] * F3[0][2] + (int)aux[i][j - 3] * F3[1][0] + (int)aux[i][j] * 
F3[1][1] + (int)aux[i][j + 3] * F3[1][2] + (int)aux[i + 1][j - 3] * F3[2][0] + 
(int)aux[i + 1][j] * F3[2][1] + (int)aux[i + 1][j + 3] * F3[2][2];
            if(x < 0)
            {
                help3[i - 1][j - 3] = 0;
            }
            else if(x > 255)
            {
                help3[i - 1][j - 3] = 255;
            }
            else
            {
                help3[i - 1][j - 3] = (unsigned char)x;
            }
        }
    }
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            f_3[i][j] = help3[infoheader.height - 1 - i][j];
        }
    }
    free(help3);
    FILE *image4 = fopen(f3, "wb");
    fwrite(&fileheader, sizeof(struct bmp_fileheader), 1, image4);
    fwrite(&infoheader, sizeof(struct bmp_infoheader), 1, image4);
    fseek(image4, fileheader.imageDataOffset, SEEK_SET);
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            fwrite(&f_3[i][j], sizeof(unsigned char), 1, image4);
        }
    }
    fclose(image4);

    //task 3

    //task 4
    FILE *bin = fopen(compress, "rb");
    fseek(bin, fileheader.imageDataOffset, SEEK_SET);

    v = malloc(infoheader.height * sizeof(short *));
    for(i = 0; i < infoheader.height; i++)
    {
        v[i] = calloc(3 * infoheader.width, sizeof(short));
    }

    matrice = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        matrice[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }
    help4 = malloc(infoheader.height * sizeof(unsigned char *));
    for(i = 0; i < infoheader.height; i++)
    {
        help4[i] = calloc(3 * infoheader.width, sizeof(unsigned char));
    }

    while(!feof(bin))
    {
        fread(&p, sizeof(short), 1, bin);
        fread(&q, sizeof(short), 1, bin);
        fread(&r, sizeof(char), 1, bin);
        fread(&b, sizeof(char), 1, bin);
        fread(&g, sizeof(char), 1, bin);
        v[p - 1][3 * (q - 1) + 0] = 3 * (q - 1) + 0;
        v[p - 1][3 * (q - 1) + 1] = 3 * (q - 1) + 1;
        v[p - 1][3 * (q - 1) + 2] = 3 * (q - 1) + 2;

        matrice[p - 1][3 * (q - 1) + 0] = r;
        matrice[p - 1][3 * (q - 1) + 1] = b;
        matrice[p - 1][3 * (q - 1) + 2] = g;
    }

    fclose(bin);

    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            if (j != v[i][j])
            {
                matrice[i][j] = matrice[i - 1][j];
            }

        }
    }
    free(v);

    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            help4[i][j] = matrice[infoheader.height - 1 - i][j];
        }
    }

    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < infoheader.width; j++)
        {
            auxiliar = help4[i][3 * j + 0];
            help4[i][3 * j + 0] = help4[i][3 * j + 2];
            help4[i][3 * j + 2] = auxiliar;
        }
    }

    free(matrice);
    FILE *image5 = fopen(decompressed, "wb");
    fwrite(&fileheader, sizeof(struct bmp_fileheader), 1, image5);
    fwrite(&infoheader, sizeof(struct bmp_infoheader), 1, image5);
    fseek(image5, fileheader.imageDataOffset, SEEK_SET);
    for(i = 0; i < infoheader.height; i++)
    {
        for(j = 0; j < 3 * infoheader.width; j++)
        {
            fwrite(&help4[i][j], sizeof(unsigned char), 1, image5);
        }
    }
    fclose(image5);

    return 0;
}
