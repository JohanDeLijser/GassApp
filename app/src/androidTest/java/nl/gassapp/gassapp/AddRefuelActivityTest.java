package nl.gassapp.gassapp;

import android.content.Context;

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

public class AddRefuelActivityTest extends UITestCase {

    private String testEmail = "test@test.com";
    private String testPassword = "test";

    private String kilometers = "12.0";
    private String liters = "6";
    private String price = "10.50";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void signUpActivityTest() {

        fillInFieldInfo(R.id.emailField, testEmail);
        fillInFieldInfo(R.id.passwordField, testPassword);

        onView(withId(R.id.loginButton)).perform(click());

        pauseTestFor(2500);

        onView(withId(R.id.addButton)).perform(click());

        pauseTestFor(700);

        fillInFieldInfo(R.id.kilometersInput, kilometers);
        fillInFieldInfo(R.id.litersInput, liters);
        fillInFieldInfo(R.id.priceInput, price);

        onView(withId(R.id.addRefuelButton)).perform(click());

        pauseTestFor(700);

        onView(withId(R.id.logoutButton)).perform(click());

        pauseTestFor(500);

    }

}
