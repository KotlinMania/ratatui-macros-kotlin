// port-lint: tests line.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.text.Line
import ratatui.text.Span
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LineTest {
    @Test
    fun lineLiteral() {
        val line = line("hello", "world")
        assertEquals(Line.from(listOf(Span.from("hello"), Span.from("world"))), line)
    }

    @Test
    fun lineRawInsteadOfLiteral() {
        val line = line(Span.raw("hello"), "world")
        assertEquals(Line.from(listOf(Span.from("hello"), Span.from("world"))), line)
    }

    @Test
    fun lineVecCountSyntax() {
        val line = line("hello", 2)
        assertEquals(Line.from(listOf(Span.from("hello"), Span.from("hello"))), line)
    }

    @Test
    fun lineVecCountSyntaxWithSpan() {
        val line = line(span("hello"), 2)
        assertEquals(Line.from(listOf(Span.from("hello"), Span.from("hello"))), line)
    }

    @Test
    fun lineEmpty() {
        val line = line()
        assertEquals(Line.default(), line)
    }

    @Test
    fun lineSingleSpan() {
        val line = line(Span.raw("foo"))
        assertEquals(Line.from(listOf(Span.from("foo"))), line)
    }

    @Test
    fun lineRepeatedSpan() {
        val line = line(Span.raw("foo"), 2)
        assertEquals(Line.from(listOf(Span.from("foo"), Span.from("foo"))), line)
    }

    @Test
    fun lineRejectsNegativeCount() {
        assertFailsWith<IllegalArgumentException> { line("bad", -1) }
    }
}
