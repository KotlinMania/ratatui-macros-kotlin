package io.github.kotlinmania.ratatuimacros

import ratatui.layout.Constraint
import ratatui.layout.Layout
import ratatui.layout.Rect
import ratatui.style.Color
import ratatui.style.Modifier
import ratatui.style.Style
import ratatui.text.Line
import ratatui.text.Span
import ratatui.text.Text
import ratatui.widgets.table.Cell
import ratatui.widgets.table.Row
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MacroHelpersTest {
    @Test
    fun spanCreatesRawAndFormattedContent() {
        assertEquals(Span.raw("test content"), span("test content"))
        assertEquals(Span.raw("test content"), span("test {}", "content"))
        assertEquals(Span.raw("test content"), span("test {content}", mapOf("content" to "content")))
        assertEquals(Span.raw("value 0007"), span("value {id:04}", mapOf("id" to 7)))
        assertEquals(Span.raw("{name} ok"), span("{{name}} {}", "ok"))
        assertEquals(Span.raw("42"), span(42))
    }

    @Test
    fun spanCreatesStyledContent() {
        val style = Style.from(Color.Green)

        assertEquals(Span.styled("test content", style), span(style, "test content"))
        assertEquals(Span.styled("test content", style), span(style, "test {}", "content"))
        assertEquals(Span.styled("test content", Style.from(Color.Green)), span(Color.Green, "test content"))
        assertEquals(Span.styled("test content", Style.from(Modifier.BOLD)), span(Modifier.BOLD, "test content"))
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

    @Test
    fun lineCreatesDefaultRepeatedAndListedSpans() {
        assertEquals(Line.default(), line())
        assertEquals(Line.from(listOf(Span.from("hello"), Span.from("world"))), line("hello", "world"))
        assertEquals(Line.from(listOf(Span.from("same"), Span.from("same"))), line("same", 2))
        assertEquals(Line.from(listOf(span("formatted ok"))), line(span("formatted {}", "ok")))
    }

    @Test
    fun textCreatesDefaultRepeatedAndListedLines() {
        assertEquals(Text.default(), text())
        assertEquals(Text.from(listOf(Line.from("hello"), Line.from("world"))), text("hello", "world"))
        assertEquals(Text.from(listOf(Line.from("same"), Line.from("same"))), text("same", 2))
        assertEquals(
            Text.from(listOf(line("hello", "world"), Line.from(span("formatted ok")))),
            text(line("hello", "world"), span("formatted {}", "ok")),
        )
    }

    @Test
    fun rowCreatesDefaultRepeatedAndListedCells() {
        assertEquals(Row.default(), row())
        assertEquals(Row.new(listOf(Cell.new("hello"), Cell.new("world"))), row("hello", "world"))
        assertEquals(Row.new(listOf(Cell.new("same"), Cell.new("same"))), row("same", 2))
        assertEquals(
            Row.new(listOf(Cell.new(Line.from("hello")), Cell.new(Span.raw("world")), Cell.new(Text.from("done")))),
            row(line("hello"), span("world"), text("done")),
        )
    }

    @Test
    fun layoutHelpersCreateConstraintsAndLayouts() {
        val all =
            listOf(
                Constraint.Percentage(20),
                Constraint.Min(1),
                Constraint.Max(10),
                Constraint.Ratio(1u, 3u),
                Constraint.Length(5),
                Constraint.Fill(2),
            )

        assertEquals(all, constraints(percent(20), ge(1), le(10), ratio(1, 3), eq(5), fill(2)))
        assertEquals(
            all,
            constraints(
                constraintPercentage(20),
                constraintMin(1),
                constraintMax(10),
                constraintRatio(1, 3),
                constraintLength(5),
                constraintFill(2),
            ),
        )
        assertEquals(listOf(Constraint.Length(3), Constraint.Length(3)), constraints(Constraint.Length(3), 2))
        assertEquals(Layout.vertical(all), vertical(all))
        assertEquals(Layout.vertical(all), vertical(*all.toTypedArray()))
        assertEquals(Layout.horizontal(all), horizontal(all))
        assertEquals(Layout.horizontal(all), horizontal(*all.toTypedArray()))
    }

    @Test
    fun layoutHelpersSplitAreasLikeUpstreamMacroTest() {
        val rect = Rect.new(0, 0, 10, 10)

        assertEquals(
            listOf(
                Rect.new(0, 0, 10, 7),
                Rect.new(0, 7, 10, 3),
            ),
            vertical(eq(7), le(3)).split(rect),
        )
        assertEquals(
            listOf(
                Rect.new(0, 0, 7, 10),
                Rect.new(7, 0, 3, 10),
            ),
            horizontal(eq(7), le(3)).split(rect),
        )

        val one = 1
        val two = 2
        val ten = 10
        val zero = 0
        val expected =
            listOf(
                Rect.new(0, 0, 1, 10),
                Rect.new(1, 0, 1, 10),
                Rect.new(2, 0, 1, 10),
                Rect.new(3, 0, 5, 10),
                Rect.new(8, 0, 1, 10),
                Rect.new(9, 0, 1, 10),
            )

        assertEquals(
            expected,
            horizontal(eq(one), ge(one), le(one), ratio(1, two), percent(ten), ge(zero)).split(rect),
        )
        assertEquals(
            expected,
            horizontal(eq(one * one), ge(one + zero), le(one - zero), ratio(1, two), percent(ten), ge(zero))
                .split(rect),
        )

        assertEquals(
            listOf(
                Constraint.Min(0),
                Constraint.Length(1),
                Constraint.Max(5),
                Constraint.Percentage(10),
                Constraint.Ratio(1u, 2u),
            ),
            constraints(ge(0), eq(1), le(5), percent(10), ratio(1, 2)),
        )
        assertEquals(List(5) { Constraint.Min(0) }, constraints(ge(0), 5))
        assertEquals(List(5) { Constraint.Max(0) }, constraints(le(0), 5))
        assertEquals(List(2) { Constraint.Length(0) }, constraints(eq(0), 2))
        assertEquals(List(2) { Constraint.Percentage(50) }, constraints(percent(50), 2))
        assertEquals(List(2) { Constraint.Ratio(1u, 2u) }, constraints(ratio(1, 2), 2))
    }

    @Test
    fun repeatedFormsRejectNegativeCounts() {
        assertFailsWith<IllegalArgumentException> { line("bad", -1) }
        assertFailsWith<IllegalArgumentException> { text("bad", -1) }
        assertFailsWith<IllegalArgumentException> { row("bad", -1) }
        assertFailsWith<IllegalArgumentException> { constraints(Constraint.Length(1), -1) }
    }
}
