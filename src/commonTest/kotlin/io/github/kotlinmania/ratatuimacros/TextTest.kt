// port-lint: tests text.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.text.Line
import ratatui.text.Text
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import io.github.kotlinmania.ratatuimacros.text as buildText

class TextTest {
    @Test
    fun text() {
        // literal
        val textLiteral = buildText("hello", "world")
        assertEquals(Text.from(listOf(Line.from("hello"), Line.from("world"))), textLiteral)

        // explicit use of span and line
        val textExplicit = buildText(line("hello"), span("world"))
        assertEquals(Text.from(listOf(Line.from("hello"), Line.from("world"))), textExplicit)

        // vec count syntax
        val textCount = buildText("hello", 2)
        assertEquals(Text.from(listOf(Line.from("hello"), Line.from("hello"))), textCount)
    }

    @Test
    fun textEmpty() {
        val empty: Text = buildText()
        assertEquals(Text.default(), empty)
    }

    @Test
    fun textRejectsNegativeCount() {
        assertFailsWith<IllegalArgumentException> { buildText("bad", -1) }
    }
}
