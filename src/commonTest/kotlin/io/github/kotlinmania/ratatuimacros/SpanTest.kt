// port-lint: tests span.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.style.Color
import ratatui.style.Modifier
import ratatui.style.Style
import ratatui.text.Span
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SpanTest {
    private val STYLE: Style = Style.new().fg(Color.Green)

    @Test
    fun raw() {
        val test = "test"
        val content = "content"
        val number = 123

        // literal
        val spanLiteral = span("test content")
        assertEquals(Span.raw("test content"), spanLiteral)

        // string
        val spanString = span("test {}", "content")
        assertEquals(Span.raw("test content"), spanString)

        // string variable
        val spanStringVariable = span("test {}", content)
        assertEquals(Span.raw("test content"), spanStringVariable)

        // string variable in the format string
        val spanNamedFormat = span("test {content}", mapOf("content" to content))
        assertEquals(Span.raw("test content"), spanNamedFormat)

        // named variable
        val spanNamedLiteral = span("test {content}", mapOf("content" to "content"))
        assertEquals(Span.raw("test content"), spanNamedLiteral)

        // named variable pointing at a local variable
        val spanNamedLocal = span("test {content}", mapOf("content" to content))
        assertEquals(Span.raw("test content"), spanNamedLocal)

        // two strings
        val spanTwoStrings = span("{} {}", "test", "content")
        assertEquals(Span.raw("test content"), spanTwoStrings)

        // two string variables
        val spanTwoVariables = span("{test} {content}", mapOf("test" to test, "content" to content))
        assertEquals(Span.raw("test content"), spanTwoVariables)

        // a number
        val spanNumber = span("test {number}", mapOf("number" to number))
        assertEquals(Span.raw("test 123"), spanNumber)

        // a number with a format specifier
        val spanNumberFormat = span("test {number:04}", mapOf("number" to number))
        assertEquals(Span.raw("test 0123"), spanNumberFormat)

        // directly pass a number expression
        val spanNumberExpr = span(number)
        assertEquals(Span.raw("123"), spanNumberExpr)

        // directly pass a string expression
        val spanStringExpr = span(test)
        assertEquals(Span.raw("test"), spanStringExpr)
    }

    @Test
    fun styled() {
        val test = "test"
        val content = "content"
        val number = 123

        // literal
        val spanLiteral = span(STYLE, "test content")
        assertEquals(Span.styled("test content", STYLE), spanLiteral)

        // string
        val spanString = span(STYLE, "test {}", "content")
        assertEquals(Span.styled("test content", STYLE), spanString)

        // string variable
        val spanStringVariable = span(STYLE, "test {}", content)
        assertEquals(Span.styled("test content", STYLE), spanStringVariable)

        // string variable in the format string
        val spanNamedFormat = span(STYLE, "test {content}", mapOf("content" to content))
        assertEquals(Span.styled("test content", STYLE), spanNamedFormat)

        // named variable
        val spanNamedLiteral = span(STYLE, "test {content}", mapOf("content" to "content"))
        assertEquals(Span.styled("test content", STYLE), spanNamedLiteral)

        // named variable pointing at a local variable
        val spanNamedLocal = span(STYLE, "test {content}", mapOf("content" to content))
        assertEquals(Span.styled("test content", STYLE), spanNamedLocal)

        // two strings
        val spanTwoStrings = span(STYLE, "{} {}", "test", "content")
        assertEquals(Span.styled("test content", STYLE), spanTwoStrings)

        // two string variables
        val spanTwoVariables = span(STYLE, "{test} {content}", mapOf("test" to test, "content" to content))
        assertEquals(Span.styled("test content", STYLE), spanTwoVariables)

        // a number
        val spanNumber = span(STYLE, "test {number}", mapOf("number" to number))
        assertEquals(Span.styled("test 123", STYLE), spanNumber)

        // a number with a format specifier
        val spanNumberFormat = span(STYLE, "test {number:04}", mapOf("number" to number))
        assertEquals(Span.styled("test 0123", STYLE), spanNumberFormat)

        // accepts any type that is convertible to Style
        val spanColor = span(Color.Green, "test {content}", mapOf("content" to content))
        assertEquals(Span.styled("test content", STYLE), spanColor)

        val spanModifier = span(Modifier.BOLD, "test {content}", mapOf("content" to content))
        assertEquals(Span.styled("test content", Style.new().bold()), spanModifier)

        // directly pass a number expression
        val spanNumberExpr = span(STYLE, number)
        assertEquals(Span.styled("123", STYLE), spanNumberExpr)

        // directly pass a string expression
        val spanStringExpr = span(STYLE, test)
        assertEquals(Span.styled("test", STYLE), spanStringExpr)
    }

    @Test
    fun spanRejectsMalformedFormats() {
        assertFailsWith<IllegalArgumentException> {
            span("test {}", "content", "extra")
        }
        assertFailsWith<IllegalArgumentException> {
            span("test {missing}", mapOf("content" to "content"))
        }
        assertFailsWith<IllegalArgumentException> {
            span("test {", "content")
        }
    }
}
