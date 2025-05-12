# GaussSimualtor

#currently only in german!#

Quick and simple java-programm for creating and editing matrix.

How to use:
If you want to add/subtract two rows, you can use the roman numbers for assigning the rows:<br>
e.g.: I, II, III, IV, V, ...<br>

You need to remember following syntax:<br>
[first-row][operator][factor][second-row] while the operators can be '+' or '-'<br>
e.g.: II+(2)III oder III-IV<br>
If you just want to mulitply a row with some factor please use following syntax:<br>
[faktor][row-to-multiply-with]<br>
e.g.: 0.5III oder 0.5*IV

manual, man, help, ?                    :if you need help and wand to see the manual<br>
stop, stop!, !                          :if you want to stop<br>
latex, tex, output, print, out, x       :to get the latex-output<b>
again, new, +                           :if you want to calculate a new Matrix<br>

For using the LaTeX Output it is necessary to import following command into the header of the LaTeX file:
```
\newcommand{\rom}[1]{\uppercase\expandafter{\romannumeral #1\relax}}
```

still in work
