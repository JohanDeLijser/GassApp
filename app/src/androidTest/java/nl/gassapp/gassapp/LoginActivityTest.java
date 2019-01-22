package nl.gassapp.gassapp;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;
import nl.gassapp.gassapp.Utils.SharedPreferencesUtil;
import nl.gassapp.gassapp.Views.LoginActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LoginActivityTest extends UITestCase {

    private String testEmail = "test@test.com";
    private String testPassword = "test";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void signUpActivityTest() {

        fillInFieldInfo(R.id.emailField, testEmail);
        fillInFieldInfo(R.id.passwordField, testPassword);

        onView(withId(R.id.loginButton)).perform(click());

        pauseTestFor(2500);

        onView(withId(R.id.logoutButton)).perform(click());

        pauseTestFor(500);
    }

    @After
    public void cleanUp() {

        SharedPreferencesUtil.getInstance().setUser(null);

    }

}
