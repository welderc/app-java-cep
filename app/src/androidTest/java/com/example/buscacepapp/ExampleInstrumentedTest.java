package com.example.buscacepapp;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Contexto da aplicação sob teste.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.buscacepapp", appContext.getPackageName());
    }

    @Test
    public void testBuscaCep() {
        // Insere o CEP válido
        onView(withId(R.id.txtCep)).perform(replaceText("01001000"), closeSoftKeyboard());
        onView(withId(R.id.btnBuscaCep)).perform(click());

        // Adiciona espera para carregamento assíncrono
        try {
            Thread.sleep(2000); // Espera 2 segundos para a resposta da API
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica se o resultado contém o estado SP
        onView(withId(R.id.lblResposta))
                .check(matches(withText(containsString("Estado: SP"))));
    }

    // Implementação de um matcher personalizado para verificar o texto
    public static Matcher<View> withTextContainingState(final String state) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("Has state: " + state);
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                String text = textView.getText().toString();
                return text.contains("Estado: " + state);
            }
        };
    }
}