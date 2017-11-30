package com.access.accessonandroid.FacialRecog;

/**
 * @author Zeyang Su
 * This is the class you can use to "store" the callback function
 */

public abstract class FacialRecogCallbackFuncObj {
    /**
     * The function invoked when a facial comparison procedure is finished
     * @param facialComparResult Weather the given faces matched each other or not
     */
    public abstract void execute(boolean facialComparResult);
}
