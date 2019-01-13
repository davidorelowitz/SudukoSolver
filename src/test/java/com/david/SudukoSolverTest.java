package com.david;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SudukoSolverTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        System.out.println("\n*** Start of test - shouldAnserWithTrue");
        Grid grid = new Grid();
        GridHelper gridHelper= new GridHelper();
        String str = new String("123456789456789123");
        gridHelper.setGrid(str, grid);

        assertTrue( gridHelper.compareStringToGrid(str, grid) );
    }

    @Test
    public void fullGrid() {
        System.out.println("\n*** Start of test - fullGrid");
        Grid grid = new Grid();
        GridHelper gridHelper= new GridHelper();
        String str = new String(      " 63  2   "+" 52  9   "+"     7512"+"54  1    "+"9   8   7"+"    2  96"+"8192     "+"   5  32 "+"   4  68 ");
        String strResult = new String("163852974"+"752149863"+"498637512"+"547916238"+"926384157"+"381725496"+"819263745"+"674598321"+"235471689");
        gridHelper.setGrid(str, grid);
        assertTrue( gridHelper.compareStringToGrid(strResult, grid) );
    }

    @Test
    public void hardGrid() {
        System.out.println("\n*** Start of test - hardGrid");
        Grid grid = new Grid();
        GridHelper gridHelper= new GridHelper();
        String str = new String(      "6   4   5"+"   5 9   "+"  52 84  "+" 37   54 "+"5       9"+" 28   67 "+"  98 27  "+"   3 1   "+"2   7   3");
        String strResult = new String("6   4   5"+"   5 9   "+"  52 84  "+" 37  654 "+"5       9"+" 28   671"+"  98 27  "+"   3 1   "+"2   7   3");
        gridHelper.setGrid(str, grid);
        assertTrue( gridHelper.compareStringToGrid(strResult, grid) );
    }
}
