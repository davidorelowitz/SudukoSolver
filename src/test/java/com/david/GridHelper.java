package com.david;

class GridHelper {

    void setGrid(String strLine, Grid grid){
        System.out.println (strLine);

        char [] buff = strLine.toCharArray();
        int     row = 1;
        int     col = 1;

        for (char ch : buff){
            if (ch >= '1' && ch <= '9') {
                int val = Character.digit(ch, 10);
                if (!grid.setVal(val, new Position(row, col), null))
                    return;
            }
            col++;
            if (col > 9) {
                row++;
                col = 1;
            }
        }
    }

    boolean compareStringToGrid(String input, Grid grid){
        char[] inputBuffer = new char[81];
        for (int i = 0; i < 81; i++) {
            inputBuffer[i] = ' ';
        }

        int offset = 0;
        for(char letter : input.toCharArray()){
            inputBuffer[offset] = letter;
            offset++;
        }

        char[] gridBuffer = grid.dumpAsString().toCharArray();

        for (int i = 0; i < 81; i++) {
            if(inputBuffer[i] != gridBuffer[i]){
                return(false);
            }
        }
        return(true);
    }
}
