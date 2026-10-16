// port-lint: tests tests/macros.rs
package io.github.kotlinmania.ratatuimacros

import ratatui.layout.Constraint
import ratatui.layout.Layout
import ratatui.layout.Rect
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LayoutTest {
    @Test
    fun layoutConstraintsMacro() {
        val rect = Rect.new(0, 0, 10, 10)

        val verticalSplit = vertical(eq(7), le(3)).split(rect)
        assertEquals(Rect.new(0, 0, 10, 7), verticalSplit[0])
        assertEquals(Rect.new(0, 7, 10, 3), verticalSplit[1])

        val horizontalSplit = horizontal(eq(7), le(3)).split(rect)
        assertEquals(Rect.new(0, 0, 7, 10), horizontalSplit[0])
        assertEquals(Rect.new(7, 0, 3, 10), horizontalSplit[1])

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

        val split1 = horizontal(eq(one), ge(one), le(one), ratio(1, two), percent(ten), ge(zero)).split(rect)
        assertEquals(expected, split1)

        val split2 =
            horizontal(eq(one * one), ge(one + zero), le(one - zero), ratio(1, two), percent(ten), ge(zero))
                .split(rect)
        assertEquals(expected, split2)

        val list = constraints(ge(0), eq(1), le(5), percent(10), ratio(1, 2))
        assertEquals(Constraint.Min(0), list[0])
        assertEquals(Constraint.Length(1), list[1])
        assertEquals(Constraint.Max(5), list[2])
        assertEquals(Constraint.Percentage(10), list[3])
        assertEquals(Constraint.Ratio(1u, 2u), list[4])

        val repeatedGe = constraints(ge(0), 5)
        for (c in repeatedGe) {
            assertEquals(Constraint.Min(0), c)
        }

        val repeatedLe = constraints(le(0), 5)
        for (c in repeatedLe) {
            assertEquals(Constraint.Max(0), c)
        }

        val repeatedEq = constraints(eq(0), 2)
        assertEquals(Constraint.Length(0), repeatedEq[0])
        assertEquals(Constraint.Length(0), repeatedEq[1])

        val repeatedPercent = constraints(percent(50), 2)
        assertEquals(Constraint.Percentage(50), repeatedPercent[0])
        assertEquals(Constraint.Percentage(50), repeatedPercent[1])

        val repeatedRatio = constraints(ratio(1, 2), 2)
        assertEquals(Constraint.Ratio(1u, 2u), repeatedRatio[0])
        assertEquals(Constraint.Ratio(1u, 2u), repeatedRatio[1])
    }

    // fails: trybuild compile-fail harness cannot be executed in Kotlin commonTest

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
        assertEquals(Layout.vertical(all), vertical(all))
        assertEquals(Layout.vertical(all), vertical(*all.toTypedArray()))
        assertEquals(Layout.horizontal(all), horizontal(all))
        assertEquals(Layout.horizontal(all), horizontal(*all.toTypedArray()))
    }

    @Test
    fun constraintsRejectsNegativeCount() {
        assertFailsWith<IllegalArgumentException> { constraints(Constraint.Length(1), -1) }
    }
}
