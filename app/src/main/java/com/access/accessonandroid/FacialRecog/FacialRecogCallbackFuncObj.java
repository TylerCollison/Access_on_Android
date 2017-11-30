package com.access.accessonandroid.FacialRecog;

/**
 * This is the class you can use to "store" the callback function
 * Created by Zeyang Su on 10/27/2017.
 */

public abstract class FacialRecogCallbackFuncObj {
    /**
     * The function invoked when a facial comparison procedure is finished
     * @param facialComparResult Weather the given faces matched each other or not
     */
    public abstract void execute(boolean facialComparResult);
}
