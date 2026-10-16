// port-lint: tests row.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.layout.HorizontalAlignment
import ratatui.text.Text
import ratatui.widgets.table.Cell
import ratatui.widgets.table.Row
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RowTest {
    @Test
    fun rowLiteral() {
        val row = row("hello", "world")
        assertEquals(
            Row.new(listOf(Cell.new("hello"), Cell.new("world"))),
            row,
        )
    }

    @Test
    fun rowEmpty() {
        val row = row()
        assertEquals(Row.default(), row)
    }

    @Test
    fun rowSingleCell() {
        val row = row(Cell.new("foo"))
        assertEquals(Row.new(listOf(Cell.new("foo"))), row)
    }

    @Test
    fun rowRepeatedCell() {
        val row = row(Cell.new("foo"), 2)
        assertEquals(Row.new(listOf(Cell.new("foo"), Cell.new("foo"))), row)
    }

    @Test
    fun rowExplicitUseOfSpanAndLine() {
        val row = row(line("hello"), span("world"))
        assertEquals(
            Row.new(listOf(Cell.new("hello"), Cell.new("world"))),
            row,
        )
    }

    @Test
    fun rowVecCountSyntax() {
        val row = row("hello", 2)
        assertEquals(
            Row.new(listOf(Cell.new("hello"), Cell.new("hello"))),
            row,
        )
    }

    @Test
    fun multipleRows() {
        val rows =
            listOf(
                row("Find File", text("ctrl+f").rightAligned()),
                row("Open recent", text("ctrl+r").rightAligned()),
                row("Open config", text("ctrl+k").rightAligned()),
            )
        assertEquals(
            listOf(
                Row.new(
                    listOf(
                        Cell.new("Find File"),
                        Cell.new(Text.raw("ctrl+f").alignment(HorizontalAlignment.Right)),
                    ),
                ),
                Row.new(
                    listOf(
                        Cell.new("Open recent"),
                        Cell.new(Text.raw("ctrl+r").alignment(HorizontalAlignment.Right)),
                    ),
                ),
                Row.new(
                    listOf(
                        Cell.new("Open config"),
                        Cell.new(Text.raw("ctrl+k").alignment(HorizontalAlignment.Right)),
                    ),
                ),
            ),
            rows,
        )
    }

    @Test
    fun rowRejectsNegativeCount() {
        assertFailsWith<IllegalArgumentException> { row("bad", -1) }
    }
}
