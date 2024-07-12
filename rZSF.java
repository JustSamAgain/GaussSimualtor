//made by Sawani

public class rZSF {

    // Bringt eine beliebige Spalte in die Form, dass ganz oben eine Zahl ungleich 0 steht und in den Spalten drunter
    // nur Nullen
    private static Matrix column(Matrix mtx, int j, int firstRow) {
        int rows = mtx.getRows();
        int columns = mtx.getColumns();
        int i = firstRow;

        // Wenn die Matrix 1x1 oder 0 ist, ist sie schon in Zeilenstufenform, ich gebe sie unverändert zurück:
        if (rows <= 1) {
            return mtx;
        }


        // Ich suche nach der ersten Zahl ungleich Null in der 1. Spalte, sonst in der 2. Spalte, etc
        // Wenn ich die Zahl gefunden habe, tausche ich die entsprechende Zeile mit der ersten, um auf mein erstes
        // Pivot Element ganz oben zu haben:
        while (j <= columns && mtx.valueAt(i, j) == 0) {
            while (i <= rows && mtx.valueAt(i, j) == 0) {
                i++;
            }
            if (i > rows) {
                j++;
                i = firstRow;
            }
        }
        // Wenn die Matrix nur aus Nullen besteht, ist sie bereits in Zeilenstufenform, ich gebe sie unberändert zurück
        if (j > columns) {
            return mtx;
        }
        mtx.swapRows(i, firstRow);

        for (int r = firstRow+1; r <= rows; r++) {
            mtx.addRowTimesNrTo(firstRow, -(mtx.valueAt(r, j) / mtx.valueAt(firstRow, j)), r);
        }
        return mtx;
    }



    // Wendet die Hilfsmethode nach und nach auf jede Spalte der Matrix mtx an und bringt sie so in Zeilenstufenform

    public static Matrix ZSF(Matrix mtx, Boolean reduced) {
        int currentCol = 1;
        int firstRow = 1;
        int columns = mtx.getColumns();
        int rows = mtx.getRows();
        while (currentCol <= columns && firstRow <= rows) {
            mtx = column(mtx, currentCol, firstRow);
            currentCol++;
            firstRow++;
        }

        // Die Zeilenstufenform ist jetzt fertig. Wenn reduced true ist, wird nun die reduzierte Zeilenstufenform
        // berechnet, sonst wird die Matrix so ausgegeben.
        if (reduced) {
            mtx = reduced(mtx);
        }
        return mtx;
    }

    private static Matrix reduced(Matrix mtxZSF) {
        int i = 1;
        int j = 1;
        int rows = mtxZSF.getRows();
        int columns = mtxZSF.getColumns();
        // Alle Pivot Elemente werden durch Multiplikation mit dem Inversen auf 1 gesetzt.
        // Wenn eine Stufe dabei mehrere Zahlen lang ist, wird zur nächsten Spalte gegangen, die ien Pivot Element hat
        while (i <= rows && j <= columns) {
            if (mtxZSF.valueAt(i, j) != 0) {
                mtxZSF.multiplyRowWith(i, 1/mtxZSF.valueAt(i, j));
                i++;
                j++;
            }
            else {
                j++;
            }
        }


        // Nun werden alle Zahlen über den Pivots auf 0 gesetzt, dabei werden die Spalten übersprungen, in denen
        // kein Pivot Element steht, da die Zahlen darüber auch ungleich 0 sein dürfen.
        i = rows;
        j = columns;
        while (i >= 1 && j >= 2) {
            if ((mtxZSF.valueAt(i, j) != 0) && (mtxZSF.valueAt(i, j-1) == 0)) {
                for (int makeZero = 1; makeZero<i; makeZero++) {
                    mtxZSF.addRowTimesNrTo(i, -(mtxZSF.valueAt(i-makeZero, j) / mtxZSF.valueAt(i, j)), i-makeZero);
                }
            }
            else {
                j--;
            }
            i--;
            j--;
        }
        return mtxZSF;

    }
}
