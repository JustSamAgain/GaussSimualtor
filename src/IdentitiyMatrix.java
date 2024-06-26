//made by JustSamAgain

public class IdentitiyMatrix extends Matrix{
    IdentitiyMatrix(int dimension){
        super(dimension, dimension);
        for (int i = 1; i <= dimension; i++) {
            this.setValueAt(i, i, 1);
        }
        setTarget('S'); //S=Matrix für Zeilenoperationen bzw Matrix-Inverse
        this.latexOut.rowsS = dimension;                                                                   //case
        this.latexOut.columnsS = dimension;
    }

    public void makeT(){
        this.setTarget('T');
        this.latexOut.dimensionT = this.getRows();                                                                   //case
    }
}
