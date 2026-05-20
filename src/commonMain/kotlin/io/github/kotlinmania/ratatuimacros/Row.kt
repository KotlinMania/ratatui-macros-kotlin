// port-lint: source row.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.text.Line
import ratatui.text.Span
import ratatui.text.Text
import ratatui.text.ToText
import ratatui.widgets.table.Cell
import ratatui.widgets.table.Row

/**
 * Creates a [Row] from zero or more cell-like values.
 *
 * The repeated form creates a [Row] from the same cell-like value repeated
 * [count] times. Values may be [Cell], [Text], [Line], [Span], [String], or
 * any value that can provide [Text].
 */
fun row(): Row =
    Row.default()

fun row(cell: Any, count: Int): Row {
    require(count >= 0) { "count must be non-negative, got $count" }
    return Row.new(List(count) { intoCell(cell) })
}

fun row(vararg cells: Any): Row =
    Row.new(cells.map { intoCell(it) })

private fun intoCell(value: Any): Cell =
    when (value) {
        is Cell -> value
        is Text -> Cell.new(value)
        is Line -> Cell.new(value)
        is Span -> Cell.new(value)
        is String -> Cell.new(value)
        is ToText -> Cell.new(value.toText())
        else -> Cell.new(value.toString())
    }
