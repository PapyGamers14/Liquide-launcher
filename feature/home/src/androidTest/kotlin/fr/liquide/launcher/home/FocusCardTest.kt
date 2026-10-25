package fr.liquide.launcher.home

import androidx.compose.material3.Text
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.pressKey
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class FocusCardTest {
    @get:Rule val compose = createComposeRule()

    @Test fun directionalCenterLaunchesExactlyOnce() {
        var launches = 0
        compose.setContent { LiquideTheme { FocusCard(true, { launches++ }) { Text("Video") } } }
        compose.onNodeWithText("Video").performSemanticsAction(SemanticsActions.RequestFocus)
        compose.onNodeWithText("Video").performKeyInput { pressKey(Key.DirectionCenter) }
        compose.runOnIdle { assertEquals(1, launches) }
    }
}
