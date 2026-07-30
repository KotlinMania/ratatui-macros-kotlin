// port-lint: source span.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.style.Color
import ratatui.style.Modifier
import ratatui.style.Style
import ratatui.text.Span

/**
 * Creates a [Span] using formatting syntax.
 *
 * The unstyled forms return a raw [Span]. The styled forms take any supported
 * style value first and return a styled [Span]. Kotlin checks these patterns at
 * runtime, supporting positional `{}`, named `{name}`, escaped braces, and
 * simple width or zero-padding specifiers such as `{number:04}`.
 */
fun span(string: String): Span =
    Span.raw(string)

fun span(format: String, vararg args: Any?): Span =
    Span.raw(renderFormat(format, emptyMap(), args))

fun span(format: String, namedArgs: Map<String, Any?>): Span =
    Span.raw(renderFormat(format, namedArgs, emptyArray()))

fun span(format: String, namedArgs: Map<String, Any?>, vararg args: Any?): Span =
    Span.raw(renderFormat(format, namedArgs, args))

fun span(expr: Any?): Span =
    Span.raw(expr.toString())

fun span(style: Style, string: String): Span =
    Span.styled(string, style)

fun span(style: Color, string: String): Span =
    span(Style.from(style), string)

fun span(style: Modifier, string: String): Span =
    span(Style.from(style), string)

fun span(style: Style, format: String, vararg args: Any?): Span =
    Span.styled(renderFormat(format, emptyMap(), args), style)

fun span(style: Color, format: String, vararg args: Any?): Span =
    span(Style.from(style), format, *args)

fun span(style: Modifier, format: String, vararg args: Any?): Span =
    span(Style.from(style), format, *args)

fun span(style: Style, format: String, namedArgs: Map<String, Any?>): Span =
    Span.styled(renderFormat(format, namedArgs, emptyArray()), style)

fun span(style: Color, format: String, namedArgs: Map<String, Any?>): Span =
    span(Style.from(style), format, namedArgs)

fun span(style: Modifier, format: String, namedArgs: Map<String, Any?>): Span =
    span(Style.from(style), format, namedArgs)

fun span(style: Style, format: String, namedArgs: Map<String, Any?>, vararg args: Any?): Span =
    Span.styled(renderFormat(format, namedArgs, args), style)

fun span(style: Color, format: String, namedArgs: Map<String, Any?>, vararg args: Any?): Span =
    span(Style.from(style), format, namedArgs, *args)

fun span(style: Modifier, format: String, namedArgs: Map<String, Any?>, vararg args: Any?): Span =
    span(Style.from(style), format, namedArgs, *args)

fun span(style: Style, expr: Any?): Span =
    Span.styled(expr.toString(), style)

fun span(style: Color, expr: Any?): Span =
    span(Style.from(style), expr)

fun span(style: Modifier, expr: Any?): Span =
    span(Style.from(style), expr)

private fun renderFormat(format: String, namedArgs: Map<String, Any?>, args: Array<out Any?>): String {
    val output = StringBuilder(format.length + 16)
    var index = 0
    var positionalIndex = 0

    while (index < format.length) {
        val char = format[index]
        when (char) {
            '{' -> {
                if (index + 1 < format.length && format[index + 1] == '{') {
                    output.append('{')
                    index += 2
                } else {
                    val close = format.indexOf('}', startIndex = index + 1)
                    require(close >= 0) { "Unclosed '{' in format string: $format" }
                    val placeholder = format.substring(index + 1, close)
                    val split = splitOnce(placeholder, ':')
                    val name = split.first.takeIf { it.isNotBlank() }
                    val value =
                        if (name == null) {
                            require(positionalIndex < args.size) { "Not enough format args for: $format" }
                            args[positionalIndex++]
                        } else {
                            require(namedArgs.containsKey(name)) { "Missing named arg '$name' for: $format" }
                            namedArgs[name]
                        }
                    output.append(applyPaddingSpec(value, split.second))
                    index = close + 1
                }
            }
            '}' -> {
                require(index + 1 < format.length && format[index + 1] == '}') {
                    "Unmatched '}' in format string: $format"
                }
                output.append('}')
                index += 2
            }
            else -> {
                output.append(char)
                index += 1
            }
        }
    }

    require(positionalIndex == args.size) {
        "Too many format args for: $format (expected $positionalIndex, got ${args.size})"
    }
    return output.toString()
}

private fun splitOnce(value: String, delimiter: Char): Pair<String, String?> {
    val index = value.indexOf(delimiter)
    return if (index < 0) {
        value to null
    } else {
        value.substring(0, index) to value.substring(index + 1)
    }
}

private fun applyPaddingSpec(value: Any?, spec: String?): String {
    val raw = value.toString()
    if (spec.isNullOrBlank()) {
        return raw
    }

    val trimmed = spec.trim()
    val width = trimmed.toIntOrNull() ?: trimmed.dropWhile { it == '0' }.toIntOrNull() ?: return raw
    val padChar = if (trimmed.startsWith('0')) '0' else ' '
    return raw.padStart(width, padChar)
}
