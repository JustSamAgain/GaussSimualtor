//made by JustSamAgain

public class IdentitiyMatrix extends Matrix{
    IdentitiyMatrix(int dimension){
        super(dimension, dimension);
        for (int i = 1; i <= dimension; i++) {
            this.setValueAt(i, i, 1);
        }

        this.latexOut.rows = dimension;                                                                   //case
        this.latexOut.columns = dimension;  
    }
}
