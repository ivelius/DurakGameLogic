package tests;

/**
 * Created by Yan-Home on 1/22/2015.
 */
public class BaseTest {

    protected void log(String msg) {
        LogUtils.log("[" + getClass().getSimpleName() + "]" + " " + msg);
    }
}
