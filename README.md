# GaussSimualtor <- yeah "Simualtor"..

#currently only in german!#

Quick and simple java-programm for creating and editing matrix.

## How to use:
If you want to add/subtract two rows, you can use the roman numbers for assigning the rows:<br>
e.g.: I, II, III, IV, V, ...<br>

You need to remember following syntax:

[first-row][operator][factor][second-row] whereas the operators can be '+' or '-'<br>
e.g.: II+(2)III or III-IV

If you just want to mulitply a row with some factor please use following syntax:<br>
[faktor][row-to-multiply-with]<br>
e.g.: 0.5III or 0.5*IV

| commands | explanation |
|----------|----------|
| manual, man, help, ? | use if you need help and wand to see the manual |
| stop, stop!, ! | use if you want to stop |
| latex, tex, output, print, out, x | to get the latex-output |
| again, new, + | use if you want to calculate a new Matrix |

## dependencys
If you want to use the LaTeX output it's necessary to import the following command into the header of your LaTeX file:
```
\newcommand{\rom}[1]{\uppercase\expandafter{\romannumeral #1\relax}}
```

still in work but don't hesitate to contribute ;)
