// port-lint: source layout.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.layout.Constraint
import ratatui.layout.Layout

/**
 * Creates a percentage constraint.
 */
fun constraintPercentage(token: Int): Constraint =
    Constraint.Percentage(token)

/**
 * Creates a minimum constraint.
 */
fun constraintMin(expr: Int): Constraint =
    Constraint.Min(expr)

fun ge(expr: Int): Constraint =
    constraintMin(expr)

/**
 * Creates a maximum constraint.
 */
fun constraintMax(expr: Int): Constraint =
    Constraint.Max(expr)

fun le(expr: Int): Constraint =
    constraintMax(expr)

/**
 * Creates a ratio constraint.
 */
fun constraintRatio(num: Int, denom: Int): Constraint {
    require(num >= 0) { "ratio numerator must be non-negative, got $num" }
    require(denom >= 0) { "ratio denominator must be non-negative, got $denom" }
    return Constraint.Ratio(num.toUInt(), denom.toUInt())
}

fun ratio(num: Int, denom: Int): Constraint =
    constraintRatio(num, denom)

/**
 * Creates a length constraint.
 */
fun constraintLength(expr: Int): Constraint =
    Constraint.Length(expr)

fun eq(expr: Int): Constraint =
    constraintLength(expr)

/**
 * Creates a fill constraint.
 */
fun constraintFill(expr: Int): Constraint =
    Constraint.Fill(expr)

fun fill(expr: Int): Constraint =
    constraintFill(expr)

fun percent(token: Int): Constraint =
    constraintPercentage(token)

/**
 * Creates a list of constraints.
 */
fun constraints(vararg constraints: Constraint): List<Constraint> =
    constraints.toList()

/**
 * Creates a list by repeating a single constraint.
 */
fun constraints(constraint: Constraint, count: Int): List<Constraint> {
    require(count >= 0) { "count must be non-negative, got $count" }
    return List(count) { constraint }
}

/**
 * Creates a vertical layout with the specified constraints.
 */
fun vertical(vararg constraints: Constraint): Layout =
    Layout.vertical(constraints.toList())

fun vertical(constraints: List<Constraint>): Layout =
    Layout.vertical(constraints)

/**
 * Creates a horizontal layout with the specified constraints.
 */
fun horizontal(vararg constraints: Constraint): Layout =
    Layout.horizontal(constraints.toList())

fun horizontal(constraints: List<Constraint>): Layout =
    Layout.horizontal(constraints)
