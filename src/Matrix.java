//made by JustSamAgain

public class Matrix {
    private int rows, columns;
    private float[][] mtx;
    private float[] swapRow;
    private float[] swapColumn;
    LaTeXOutput latexOut;
    RuntimeException rowDoesntExist = new RuntimeException("This row doesn't exist, sorry");
    RuntimeException columnDoesntExit = new RuntimeException("This column doesn't exist, sorry");

    Matrix(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.mtx = new float[columns][rows]; //x, y
        this.swapRow = new float[columns];
        this.swapColumn = new float[rows];
        this.latexOut = new LaTeXOutput();
    }
    Matrix(int rows, int columns, float[][] values){
        this(rows, columns);
        mtx = values;
    }

    public void fillMxEx(){
        int value = 1;
        for(int i=rows-1; 0<=i; i--){
            for(int j = 0; j<columns; j++){
                mtx[j][i]=value;
                value++;
            }
        }
    }
    public void fillRow(float[] values, int rowNr){
        if(rowNr>rows || rowNr==0) return;
        int y = rows-rowNr;
        for(int i = 0; i<columns; i++){
            mtx[i][y]=values[i];
        }
    }
    public void printMx(){
        for(int i = rows-1; 0<=i; i--){
            if((rows-i-1)<Math.floor(rows/2)){
                for (int k =(int)(Math.floor(rows/2)-(rows-i-1)); k>0; k--){
                    System.out.printf(" ");
                }
                System.out.printf("/");
                for (int k =(rows-i-1); k>0; k--){
                    System.out.printf(" ");
                }
            } else if ((rows-i-1)==(int)(Math.floor(rows/2))) {
                System.out.printf("|");
                for (int k = (rows-i-1); k>0; k--){
                    System.out.printf(" ");
                }
            } else if ((rows-i-1)>Math.floor(rows/2)) {
                for (int k =(rows-i-1)-(int)(Math.floor(rows/2)); k>0; k--){
                    System.out.printf(" ");
                }
                System.out.printf("\\");
                for (int k =-(rows-i-1)+2*(int)(Math.floor(rows/2)); k>0; k--){
                    System.out.printf(" ");
                }
            }
            for(int j = 0; j<columns; j++){
                System.out.print(mtx[j][i]+" ");
            }
            System.out.print("\n");
        }
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    public float[][] getMtx() {
        return this.mtx;
    }

    public void setMtx(float[][] mtx) {
        this.mtx = mtx;
    }

    public float valueAt(int i, int j){
        if(i>rows || i==0) throw rowDoesntExist;
        if (j>columns || j==0) throw columnDoesntExit;
        int x=(j-1);
        int y=rows-i;
        return mtx[x][y];
    }
    public void setValueAt(int i, int j, float value){
        if(i>rows || i==0) throw rowDoesntExist;
        if (j>columns || j==0) throw columnDoesntExit;
        int x=(j-1);
        int y=rows-i;
        mtx[x][y]=value;
    }
    //row operations
    public void swapRows(int first, int with){
        if(first>rows || with>rows || first==0 || with == 0) throw rowDoesntExist;
        copyRow(with);
        moveRow(first, with);
        fillCopiedRowInto(first);

        addMtxToLatexHistory();
        latexOut.changesMade.add("s");
        latexOut.changesMade.add(first +"");
        latexOut.changesMade.add(with+"");
    }
        private void copyRow(int rowNr){
        if(rowNr>rows || rowNr==0) throw rowDoesntExist;
        int y = rows-rowNr;
        for(int i = 0; i<columns; i++){
            swapRow[i]=mtx[i][y];
        }
    }
        private void fillCopiedRowInto(int rowNr){
        if(rowNr>rows || rowNr==0) throw rowDoesntExist;
        int y=rows-rowNr;
        for(int i=0; i<columns; i++){
            mtx[i][y]=swapRow[i];
        }
    }
        private void moveRow(int from, int to){
        if(from>rows || to>rows || from==0 || to==0) throw rowDoesntExist;
        int y1 = rows-from;
        int y2 = rows-to;
        for(int i = 0; i<columns; i++){
            mtx[i][y2]=mtx[i][y1];
        }
    }
    public void addRowTimesNrTo (int first, float times, int to){
        if(first>rows || to>rows || first==0 || to==0) throw rowDoesntExist;
        copyRow(first);
        multiplySwapRowWith(times);
        int y = rows-to;
        for(int i=0; i<columns; i++){
            mtx[i][y]+=swapRow[i];
        }

        addMtxToLatexHistory();
        if(times>0){
            latexOut.changesMade.add("a");
            latexOut.changesMade.add(to+"");
            latexOut.changesMade.add(times + "");
            latexOut.changesMade.add(first+"");
        }else {
            latexOut.changesMade.add("u");
            latexOut.changesMade.add(to+"");
            latexOut.changesMade.add((0-times)+"");
            latexOut.changesMade.add(first+"");
        }
    }
        private void multiplySwapRowWith(float factor){
        for(int i = 0; i<columns; i++){
            swapRow[i] *= factor;
        }
    }
    public void multiplyRowWith(int row, float times){
        if(row>rows || row==0) throw rowDoesntExist;
        copyRow(row);
        multiplySwapRowWith(times);
        fillCopiedRowInto(row);

        addMtxToLatexHistory();
        latexOut.changesMade.add("m");
        latexOut.changesMade.add(row + "");
        latexOut.changesMade.add(times + "");
    }
    //column operations
    public void swapColumns(int first, int with){
        if(first > columns || with > columns || first == 0 || with == 0) throw columnDoesntExit;
        copyColumn(first);
        moveColumn(with, first);
        fillCopiedColumnInto(with);
    }
        private void copyColumn(int columnNr){
        if(columnNr>columns || columnNr==0) throw columnDoesntExit;
        int x = columnNr-1;
        for(int i = 0; i<rows; i++){
            swapColumn[i]=mtx[x][i];
        }
    }
        private void fillCopiedColumnInto(int columnNr){
        if(columnNr>columns || columnNr==0) throw columnDoesntExit;
        int x = columnNr-1;
        for(int i = 0; i<rows; i++){
            mtx[x][i]=swapColumn[i];
        }
    }
        private void moveColumn(int from, int to){
        if(from>columns || to>columns || from == 0 || to ==0) throw columnDoesntExit;
        int xfrom = from-1;
        int xTo = to-1;
        for(int i=0; i<rows; i++){
            mtx[xTo][i]=mtx[xfrom][i];
        }
    }
    public void multiplyColumnWith(int which, float factor){
        if(which>columns || which==0) throw columnDoesntExit;
        copyColumn(which);
        multiplySwapColumnWith(factor);
        fillCopiedColumnInto(which);
    }
        private void multiplySwapColumnWith(float factor){
            for(int i = 0; i<rows; i++){
                swapColumn[i] *= factor;
            }
        }
    public void addColumnsTimesNrTo(int first, float times, int to){
        if(first>columns || to>columns || first == 0 || to == 0) throw columnDoesntExit;
        int x = to-1;
        copyColumn(first);
        multiplySwapColumnWith(times);
        for(int i = 0; i<rows; i++){
            mtx[x][i]+=swapColumn[i];
        }

    }

    public void addMtxToLatexHistory(){
        Matrix mtxAdd = new Matrix(this.rows, this.columns);
        float[][] temp = new float[columns][rows];
        for (int i = 0; i < columns; i++) {
            System.arraycopy(this.getMtx()[i], 0, temp[i], 0, rows);
        }
        mtxAdd.setMtx(temp);
        latexOut.mtxHistory.add(mtxAdd);                                                                                 //case  M/S/T!!
    }
}
