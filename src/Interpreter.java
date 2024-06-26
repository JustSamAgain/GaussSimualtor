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

    public void customZSF(){
        makeMtx();
        mtx.printMx();
        getNewInput();
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

        mtx.latexOut.rowsMain = rows;                                                                                     //case ?
        mtx.latexOut.columnsMain = columns;                                                                               //case ?

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

    private void getNewInput(){
        Scanner Inp = new Scanner(System.in);
        System.out.print("Next change: ");
        String command = Inp.nextLine();
        if(command.compareTo("manual")==0 || command.compareTo("man")==0 || command.compareTo("help")==0 ||
        command.compareTo("manual, man, help")==0 || command.compareTo("?")==0){
            printManual();
        } else if (command.compareTo("stop")==0 || command.compareTo("stop!")==0 || command.compareTo("!")==0 ||
        command.compareTo("stop, stop!, !")==0 || command.compareTo("exit")==0) {
            return;
        } else if (command.compareTo("latex")==0 || command.compareTo("output")==0 || command.compareTo("print")==0 ||
                command.compareTo("tex")==0 || command.compareTo("x")==0 || command.compareTo("out")==0) {
            System.out.println(mtx.latexOut.makeLaTeX('M'));                                                       //case!
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
                        if (readPointer == 2) second = add - first;
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
        getNewInput();
    }
        private void execute(String ToDO, int first, int second, float factor){
        if(ToDO.compareTo("mult")==0) mtx.multiplyRowWith(first, factor);
        else if (ToDO.compareTo("add")==0) mtx.addRowTimesNrTo(second, factor, first);
        else if(ToDO.compareTo("swapRows")==0) mtx.swapRows(first, second);
        mtx.printMx();
    }
        public void printManual(){
        System.out.print("Sie können den GaussSimulator nutzen indem Sie sich an die nachfolgenden Befehle halten:\n" +
                "manual, man, help      :falls Sie Hilfe brauchen und diese Anleitung nochmal sehen wollen\n" +
                "Falls Sie zwei Zeilen Addieren/Subtrahieren wollen, ist folgendes Wichtig für Sie:\n" +
                "   Die Werte der Zeilen können Sie mit den Römischen Ziffern eingeben, also: I, II, III, IV, V, ...\n" +
                "   Dabei sollten Sie sich an folgende Konvention halten: [ErsteZeile][Operator]([Faktor])[ZweiteZeile]\n" +
                "   dabei können die Operatoren '+' und '-' sein.\n" +
                "       Also bspw.: II+(2)III oder III-IV\n" +
                "Sollten sie auf eine Zeile einfach nur einen Faktor drauf Multiplizieren gehen Sie bitte wie folgt vor:\n" +
                "   [faktor][BetreffendeZeile]\n" +
                "       also bspw. 0.5III oder 0.5*IV\n" +
                "stop, stop!, !           :if you want to stop\n");
    }
}
