package fr.liquide.launcher.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AppOrderingTest {
    private fun app(id: String, label: String) = TvApp(id, label, id, "Main")

    @Test fun `favorite identity survives duplicate labels and missing packages`() {
        val apps = listOf(app("b", "Video"), app("a", "Video"), app("c", "Alpha"))
        assertEquals(listOf("b", "c", "a"), orderedApps(apps, setOf("b", "removed")).map { it.id })
    }

    @Test fun `component duplicates collapse without merging separate activities`() {
        val a = app("pkgOne,Main", "Video")
        val b = app("pkgOne,Other", "Video")
        assertEquals(2, orderedApps(listOf(a, a, b), emptySet()).size)
    }
}
