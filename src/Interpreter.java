//made by JustSamAgain

import java.util.Scanner;

public class Interpreter {
    static public Matrix mtx;
    public void test(Matrix mtx1){
        mtx=mtx1;
        getNewInput();
    }

    public void test(){
        Scanner Inp = new Scanner(System.in);
        String newTest = Inp.nextLine();
        char letter = newTest.charAt(0);
        if (letter>=48 && 57>=letter) {
            System.out.println("true");
            System.out.println((int)letter);
        }else {
            System.out.println("nee");
        }
        if (newTest.compareTo("stop")==0 || newTest.compareTo("stop!")==0 || newTest.compareTo("!")==0 ||
                newTest.compareTo("stop, stop!, !")==0) {
            return;
        }
        test();
    }

    public void run(Interpreter inp){
        Scanner Inp = new Scanner(System.in);
        boolean execute = true;
        System.out.println("What do you want to do? (customZSF / automatedZSF / automatedRZSF)");
        String input = Inp.next();
        while (!(input.compareTo("customZSF")==0 ||
                input.compareTo("automatedZSF")==0 ||
                input.compareTo("automatedRZSF")==0 ||
                input.compareTo("exit")==0 ||
                input.compareTo("stop")==0||
                input.compareTo("!")==0||
                input.compareTo("help")==0||
                input.compareTo("man")==0||
                input.compareTo("?")==0 ||
                input.compareTo("")==0)) {
            System.out.println("Ich benötige einen gültigen Input");
            input = Inp.next();
        }
        if(input.compareTo("customZSF")==0){
            inp.customZSF();
        } else if (input.compareTo("automatedZSF")==0) {
            automatedZSF(false);
        } else if (input.compareTo("automatedRZSF")==0) {
            automatedZSF(true);
        } else if (input.compareTo("help")==0||
                input.compareTo("man")==0||
                input.compareTo("?")==0 ) {
            printManual();
        }
    }

    public void automatedZSF(boolean reduced){
        boolean execute = true;
        Scanner Inp = new Scanner(System.in);
        String input;
        while(execute){
            makeMtx();
            mtx.printMx();
            mtx = rZSF.ZSF(this.mtx, reduced);
            mtx.printMx();
            System.out.println("LaTeX output? y/n");
            input = Inp.next();
            input = input.toLowerCase();
            while (!(input.compareTo("y")==0 ||
                    input.compareTo("n")==0 ||
                    input.compareTo("j")==0 ||
                    input.compareTo("x")==0)) {
                System.out.println("Ich benötige einen gültigen Input");
                input = Inp.next();
                input = input.toLowerCase();
            }
            if (input.compareTo("y")==0 || input.compareTo("j")==0){
                System.out.println(mtx.latexOut.makeLaTeX());
            }
            System.out.println("again? y/n");
            input = Inp.next();
            input = input.toLowerCase();
            while (!(input.compareTo("y")==0 ||
                    input.compareTo("n")==0 ||
                    input.compareTo("j")==0 ||
                    input.compareTo("x")==0)) {
                System.out.println("Ich benötige einen gültigen Input");
                input = Inp.next();
                input = input.toLowerCase();
            }
            execute = (input.compareTo("y")==0 || input.compareTo("j")==0);
        }
    }

    public void customZSF(){
        boolean execute = true;
        while(execute){
            makeMtx();
            mtx.printMx();
            int k;
            do {
                k = getNewInput();
            } while (k == 1);
            execute = (k==2);
        }
    }
    private Matrix makeMtx(){
        int rows, columns;
        Scanner Inp = new Scanner(System.in);
        Scanner Inp2 = new Scanner(System.in);
        do {
            System.out.println("Anzahl Zeilen:");
            while (!Inp2.hasNextInt()) {
                System.out.println("Bitte eine Nummer größer als Null.");
                Inp2.next();
            }
            rows = Inp2.nextInt();
        } while (rows <= 0);

        do {
            System.out.println("Anzahl Spalten: ");
            while (!Inp2.hasNextInt()) {
                System.out.println("Bitte eine Nummer größer als Null.");
                Inp2.next();
            }
            columns = Inp2.nextInt();
        } while (columns <= 0);

        mtx = new Matrix(rows, columns);
        float[] rowValues = new float[columns];

        mtx.latexOut.rows = rows;
        mtx.latexOut.columns = columns;

        for (int i = 1; i<=rows; i++) {
            System.out.println("Bitte geben Sie die Werte der Zeile Nr."+ i + " (abgetrennt durch Leerzeichen) ein:");
            String InputRow = Inp.nextLine();
            String[] numberString = InputRow.split(" ");
            for (int j = 0; j < columns; j++) {
                try {
                    rowValues[j]=Float.parseFloat(numberString[j]);
                }catch (Exception e){
                    rowValues[j]=0;
                }
            }
            mtx.fillRow(rowValues, i);
        }
        mtx.addMtxToLatexHistory();
        return mtx;
    }

    private int getNewInput(){
        Scanner Inp = new Scanner(System.in);
        System.out.print("Next change: ");
        String command = Inp.nextLine();
        if(command.compareTo("manual")==0 || command.compareTo("man")==0 || command.compareTo("help")==0 ||
        command.compareTo("manual, man, help")==0 || command.compareTo("?")==0){
            printManual();
        } else if (command.compareTo("stop")==0 || command.compareTo("stop!")==0 || command.compareTo("!")==0 ||
        command.compareTo("stop, stop!, !")==0 || command.compareTo("exit")==0) {
            return 0;
        } else if (command.compareTo("latex")==0 || command.compareTo("output")==0 || command.compareTo("print")==0 ||
                command.compareTo("tex")==0 || command.compareTo("x")==0 || command.compareTo("out")==0) {
            System.out.println(mtx.latexOut.makeLaTeX());
        } else if (command.compareTo("again")==0 || command.compareTo("new")==0 || command.compareTo("+")==0) {
            return 2;
        } else {
            String ToDo = "";
            int first = 0; //addr first row
            int second = 0; //addr second row
            float factor = 0f; //factor multiply with
            boolean isFactorNeg = false;
            int readPointer = 1; //points to which address of which row is read
            for (int i = 0; i < command.length(); i++) {
                char letter = command.charAt(i);
                if (letter == 'I' || letter == 'V') {                                 //determine which row
                    int add = 1;
                    if (letter == 'V') {
                        add = 5;
                    }
                    if (letter == 'V' && i != 0 && command.charAt(i - 1) == 'I') {
                        if (readPointer == 1) first = add - first;
                        if (readPointer == 2) second = add - second;
                    } else if (readPointer == 1) {
                        first += add;
                    } else if (readPointer == 2) {
                        second += add;
                    } else throw new RuntimeException();
                } else if (letter == '<' && command.charAt(i + 1) == '-' && command.charAt(i + 2) == '>') {         //determine if op is swap
                    ToDo = "swapRows";
                    i += 2;
                    readPointer++;             //now the first addr is done
                } else if (letter == '-') {
                    if (i != 0)
                        ToDo = "add";       //if isn't the first letter then the first row should already have been mentioned, so op is add
                    readPointer++;
                    isFactorNeg = true;
                } else if (letter == '+') {
                    if (i != 0) ToDo = "add";
                    readPointer++;
                } else if (((int) letter >= 48 && 57 >= (int) letter) || letter == ',' || letter == '.') {    //if literals appear then there is a factor we need to read
                    if (command.charAt(i) == 48 && (command.charAt(i + 1) == '.' || command.charAt(i + 1) == ',') || letter == ',' || letter == '.') {
                        int positionNachkommastelle = 1;
                        if (letter == ',' || letter == '.') {
                            i++;
                        } else i += 2;
                        while (command.charAt(i) >= 48 && command.charAt(i) <= 57) {
                            factor += ((command.charAt(i)) - 48) / (float) (Math.pow(10, positionNachkommastelle));
                            i++;
                            positionNachkommastelle++;
                        }
                        i--;
                    } else {
                        while (command.charAt(i) >= 48 && command.charAt(i) <= 57) {
                            factor *= 10;
                            factor += ((int) command.charAt(i) - 48);
                            i++;
                        }
                        i--;
                    }
                }
            }
            if (second == 0) ToDo = "mult";
            else if (first == 0) {
                first = second;
                ToDo = "mult";
            }
            if (factor == 0) factor = 1;
            if (isFactorNeg) {
                factor *= (-1);
            }
            execute(ToDo, first, second, factor);
        }
        return 1;
    }
        private void execute(String ToDO, int first, int second, float factor){
        if(ToDO.compareTo("mult")==0) mtx.multiplyRowWith(first, factor);
        else if (ToDO.compareTo("add")==0) mtx.addRowTimesNrTo(second, factor, first);
        else if(ToDO.compareTo("swapRows")==0) mtx.swapRows(first, second);
        mtx.printMx();
    }
        public void printManual(){
        System.out.print("""
                Sie können den GaussSimulator nutzen indem Sie sich an die nachfolgenden Befehle halten:
                manual, man, help, ?                    :falls Sie Hilfe brauchen und diese Anleitung nochmal sehen wollen
                
                automatedZSF/automatedRZSF mode:
                    automatically makes the reduced-rows-columns form of the matrix for you
                
                customZSF mode:
                Falls Sie zwei Zeilen Addieren/Subtrahieren wollen, ist folgendes Wichtig für Sie:
                   Die Werte der Zeilen können Sie mit den Römischen Ziffern eingeben, also: I, II, III, IV, V, ...
                   Dabei sollten Sie sich an folgende Konvention halten: [ErsteZeile][Operator]([Faktor])[ZweiteZeile]
                   dabei können die Operatoren '+' und '-' sein.
                       Also bspw.: II+(2)III oder III-IV
                Sollten sie auf eine Zeile einfach nur einen Faktor drauf Multiplizieren gehen Sie bitte wie folgt vor:
                   [faktor][BetreffendeZeile]
                       also bspw. 0.5III oder 0.5*IV
                stop, stop!, !                          :if you want to stop
                latex, tex, output, print, out, x       :to get the latex-output
                again, new, +                           :if you want to calculate a new Matrix
                
                LaTeX-Output: Damit die Darstellung der Römischen Ziffern funktioniert ist es nötig folgenden Command einzurichten:
                '''
                \\newcommand{\\rom}[1]{\\uppercase\\expandafter{\\romannumeral #1\\relax}}
                '''
                """);
    }

    public static void main(String[] args) {
        Interpreter Inp = new Interpreter();
        Inp.run(Inp);
    }
}
