package com.example.dictionaryapp.ui.main

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.dictionaryapp.R
import com.example.dictionaryapp.domain.model.License
import com.example.dictionaryapp.domain.model.WordInfo
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    instrumentedPackages = ["androidx.loader.content"],
    sdk = [Build.VERSION_CODES.P]
)
class MainFragmentTest {

    private lateinit var scenario: FragmentScenario<MainFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(
            themeResId = R.style.Theme_DictionaryApp,
            initialState = Lifecycle.State.STARTED
        ) {
            MainFragment.newInstance()
        }
    }

    @Test
    fun `fragment should display loading`() {
        // Given
        val uiState = MainUiState(listOf(), true)

        // When
        scenario.onFragment {
            it.viewModel._uiState.value = uiState

            // Then
            onView(withId(R.id.tilSearch)).check(matches(isDisplayed()))
            onView(withId(R.id.etSearch)).check(matches(isDisplayed()))
            onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
            onView(withId(R.id.recyclerView)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun `fragment should display list`() {
        // Given
        val wordInfos = listOf(
            WordInfo(
                License("abc", "def"), listOf(), "Phonetic Bank", listOf(), listOf(), "Word Bank"
            )
        )
        val uiState = MainUiState(wordInfos, false)

        // When
        scenario.onFragment {
            it.viewModel._uiState.value = uiState

            // Then
            onView(withId(R.id.tilSearch)).check(matches(isDisplayed()))
            onView(withId(R.id.etSearch)).check(matches(isDisplayed()))
            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
            onView(withText("Word Bank")).check(matches(isDisplayed()))
            onView(withText("Phonetic Bank")).check(matches(isDisplayed()))
            onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        }
    }
}
