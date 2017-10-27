package com.access.accessonandroid.FacialRecog;

/**
 * Created by suzey on 10/27/2017.
 */

public abstract class FacialRecogCallbackFuncObj {
    /**
     * The function invoked when a facial comparison procedure is finished
     * @param facialComparResult Weather the given faces matched each other or not
     */
    public abstract void execute(boolean facialComparResult);
}
