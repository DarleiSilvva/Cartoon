package vm.caatsoft.cartoon.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import vm.caatsoft.cartoon.R

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Test 1: Checking the initial UI state
    @Test
    fun testInitialUIState() {
        // Given: The MainActivity is launched
        // When: The UI elements are visible
        // Then: Ensure the initial state of the UI matches the expected layout and text

        onView(withId(R.id.button_discover))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.button_discover_text)))

        onView(withId(R.id.image_character))
            .check(matches(isDisplayed()))
            .check(matches(withContentDescription(R.string.image_character_content_description)))

        onView(withId(R.id.text_character_info))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.character_info_text)))
    }

    // Test 2: Discover character functionality
    @Test
    fun testDiscoverCharacter() {
        // Given: The MainActivity is launched and the button is visible
        // When: The user clicks on the "Discover" button
        // Then: The character information and image should be displayed

        // Given
        onView(withId(R.id.button_discover))
            .check(matches(isDisplayed()))

        // When
        onView(withId(R.id.button_discover))
            .perform(click())

        // Then
        onView(withId(R.id.text_character_info))
            .check(matches(isDisplayed()))

        onView(withId(R.id.image_character))
            .check(matches(isDisplayed()))
    }
}
