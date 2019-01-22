package nl.gassapp.gassapp;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.Views.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class SignUpActivityTest extends UITestCase {

    private String testEmail = "test@test.com";
    private String testFirtname = "Test";
    private String testLastname = "Test";
    private String testPassword = "test";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signUpActivityTest() {
        onView(withId(R.id.registerButton)).perform(click());

        pauseTestFor(700);

        fillInFieldInfo(R.id.email, testEmail);
        fillInFieldInfo(R.id.firstname, testFirtname);
        fillInFieldInfo(R.id.lastname, testLastname);
        fillInFieldInfo(R.id.password, testPassword);

        onView(withId(R.id.registerButton)).perform(click());
    }

}
