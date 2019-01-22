package nl.gassapp.gassapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class UITestCase {

    protected void fillInFieldInfo(int id, String value) {
        onView(withId(id)).perform(clearText(), typeText(value));
        pauseTestFor(200);
    }

    protected void pauseTestFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
