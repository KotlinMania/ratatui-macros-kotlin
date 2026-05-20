// port-lint: source text.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.text.Line
import ratatui.text.Span
import ratatui.text.Text

/**
 * Creates a [Text] from zero or more line-like values.
 *
 * The repeated form creates a [Text] from the same line-like value repeated
 * [count] times. Values may be a [Line], [Span], or [String].
 */
fun text(): Text =
    Text.default()

fun text(line: Any, count: Int): Text {
    require(count >= 0) { "count must be non-negative, got $count" }
    return Text.from(List(count) { intoLine(line) })
}

fun text(vararg lines: Any): Text =
    Text.from(lines.map { intoLine(it) })

private fun intoLine(value: Any): Line =
    when (value) {
        is Line -> value
        is Span -> Line.from(value)
        is String -> Line.from(value)
        else -> throw IllegalArgumentException("Unsupported line value: $value")
    }
