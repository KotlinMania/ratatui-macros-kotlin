// port-lint: source line.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.text.Line
import ratatui.text.Span

/**
 * Creates a [Line] from zero or more span-like values.
 *
 * The repeated form creates a [Line] from the same span-like value repeated
 * [count] times. Values may be a [Span] or a [String].
 */
fun line(): Line =
    Line.default()

fun line(span: Any, count: Int): Line {
    require(count >= 0) { "count must be non-negative, got $count" }
    return Line.from(List(count) { intoSpan(span) })
}

fun line(vararg spans: Any): Line =
    Line.from(spans.map { intoSpan(it) })

private fun intoSpan(value: Any): Span =
    when (value) {
        is Span -> Span.from(value)
        is String -> Span.from(value)
        else -> throw IllegalArgumentException("Unsupported span value: $value")
    }
