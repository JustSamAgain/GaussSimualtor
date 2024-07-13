//made by JustSamAgain

import java.util.ArrayList;

public class LaTeXOutput {
    public ArrayList<Matrix> mtxHistory;
    public ArrayList<String> changesMade;
    public int columns, rows;

    LaTeXOutput(){
        mtxHistory = new ArrayList<Matrix>();
        changesMade = new ArrayList<String>();
        columns = 0;
        rows = 0;
    }

    public String makeLaTeX(){
        ArrayList<Matrix> mtxList = this.mtxHistory;
        Matrix toConvert;
        String output = "$";

        int pointChangesMadeList = 0;
        for (int i = 0; i < mtxList.size(); i++) {
            toConvert=mtxList.get(i);

            if(i>0){
                output += "\n\\xrightarrow{\\rom{" + this.changesMade.get(pointChangesMadeList+1) + "}";
                switch (this.changesMade.get(pointChangesMadeList).charAt(0)){
                    case 's': //swap        "s", [first], [second]
                        output += "\\leftrightarrow";
                        output += "\\rom{" + this.changesMade.get(pointChangesMadeList+2) + "}";
                        pointChangesMadeList += 3;
                        break;
                    case 'a': //add         "a", [to], [factor], [what]
                        output += "=\\rom{" + this.changesMade.get(pointChangesMadeList+1) + "}";
                        output += "+";
                        output += this.changesMade.get(pointChangesMadeList+2).compareTo("1.0")==0 ? "" :
                                parseIfParseable(this.changesMade.get(pointChangesMadeList+2));
                        output += "\\rom{" + this.changesMade.get(pointChangesMadeList+3) + "}";
                        pointChangesMadeList += 4;
                        break;
                    case 'u': //s[u]b       "u", [to], [fac], [what]
                        output += "=\\rom{" + this.changesMade.get(pointChangesMadeList+1) + "}";
                        output += "-";
                        output += this.changesMade.get(pointChangesMadeList+2).compareTo("1.0")==0 ? "" :
                                parseIfParseable(this.changesMade.get(pointChangesMadeList+2));

                        output += "\\rom{" + this.changesMade.get(pointChangesMadeList+3) + "}";
                        pointChangesMadeList += 4;
                        break;
                    case 'm': //multiply         "m", [row], [times]
                        output+= "=";
                        output += this.changesMade.get(pointChangesMadeList+2).compareTo("1.0")==0 ? "" :
                                parseIfParseable(this.changesMade.get(pointChangesMadeList+2));

                        output += "\\cdot \\rom{" + this.changesMade.get(pointChangesMadeList+1) + "}";
                        pointChangesMadeList += 3;
                        break;
                    default:
                        throw new RuntimeException();
                }
                output += "}\n";
            }

            output += "\\begin{pmatrix}\n";
            for (int j = 1; j <= rows; j++) {
                for (int k = 1; k <= columns; k++) {
                    output+= parseIfParseable(toConvert.valueAt(j, k));
                    output += k<columns?  " & ": "";
                }
                output += j<columns? "\\\\\n" : "\n";
            }
            output+= "\\end{pmatrix}";
        }
        
        output += "$";
        return output;
    }

    public boolean isLosslessConvertible (float value){
        return (value == (float) (int) value);
    }
    private boolean isLosslessConvertible (String valueS){
        float value = Float.parseFloat(valueS);
        return (value == (float) (int) value);
    }
    private String parseIfParseable(float value){
        if(isLosslessConvertible(value)) return ""+(int)value;
        return ""+value;
    }
    private String parseIfParseable(String value){
        if(isLosslessConvertible(value)) return ""+(int)Float.parseFloat(value);
        return ""+value;
    }

}
