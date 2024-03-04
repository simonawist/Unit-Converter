package converter

import java.util.*

enum class UnitMeasure(val measure: List<String>, val rate: Double, val type: String) {
    METER(listOf("m", "meter", "meters"), 1.0, "distance"),
    KILOMETER(listOf("km", "kilometer", "kilometers"), 1_000.0, "distance"),
    CENTIMETER(listOf("cm", "centimeter", "centimeters"), 0.01, "distance"),
    MILLIMETER(listOf("mm", "millimeter", "millimeters"), 0.001, "distance"),
    MILE(listOf("mi", "mile", "miles"), 1_609.35, "distance"),
    YARD(listOf("yd", "yard", "yards"), 0.9144, "distance"),
    FOOT(listOf("ft", "foot", "feet"), 0.3048, "distance"),
    INCH(listOf("in", "inch", "inches"), 0.0254, "distance"),
    GRAM(listOf("g", "gram", "grams"), 1.0, "weight"),
    KILOGRAM(listOf("kg", "kilogram", "kilograms"), 1_000.0, "weight"),
    MILLIGRAM(listOf("mg", "milligram", "milligrams"), 0.001, "weight"),
    POUND(listOf("lb", "pound", "pounds"), 453.592, "weight"),
    OUNCE(listOf("oz", "ounce", "ounces"), 28.3495, "weight"),
    CELSIUS(listOf("c", "degree Celsius", "degrees Celsius", "celsius", "dc"), 1.0, "temperature"),
    FAHRENHEIT(listOf("f", "degree Fahrenheit", "degrees Fahrenheit", "fahrenheit", "df"), 1.0, "temperature"),
    KELVIN(listOf("k", "kelvin", "kelvins"), 1.0, "temperature")
}

fun main() {
    while (true) {
        var foundMeasure = false
        var foundMeasure2 = false
        var unitOne: UnitMeasure = UnitMeasure.METER
        var unitTwo: UnitMeasure = UnitMeasure.METER

        var value = 0.0
        var convertedValue = 0.0
        var transitionWord = ""
        var sourceUnit = ""
        var targetUnit = ""

        print("Enter what you want to convert (or exit): ")
        val line = readln().split(" ")

        if (line[0].lowercase() == "exit") {
            break
        }

        try {
            value = line[0].toDouble()
        } catch (e: Exception) {
            println("Parse error")
            continue
        }

        sourceUnit = line[1].lowercase()
        if (sourceUnit.startsWith("degree")) {
            sourceUnit += " " + line[2].lowercase().replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            transitionWord = line[3]
            targetUnit = line[4].lowercase()
            if (targetUnit.startsWith("degree")) {
                targetUnit += " " + line[5].lowercase().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
        } else {
            transitionWord = line[2]
            targetUnit = line[3].lowercase()
            if (targetUnit.startsWith("degree")) {
                targetUnit += " " + line[4].lowercase().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
        }

        for (unit1 in UnitMeasure.values()) {
            if (sourceUnit in unit1.measure) {
                foundMeasure = true
                unitOne = unit1
            }
        }
        for (unit2 in UnitMeasure.values()) {
            if (targetUnit in unit2.measure) {
                foundMeasure2 = true
                unitTwo = unit2
            }
        }

        if (!foundMeasure || !foundMeasure2) {
            println(
                "Conversion from ${
                    if (foundMeasure) unitOne.measure[2]
                    else "???"
                } to ${
                    if (foundMeasure2) unitTwo.measure[2]
                    else "???"
                } is impossible"
            )
            continue
        }

        if (unitOne.type == unitTwo.type) {
            if (unitOne.type == "distance" && value < 0.0) {
                println("Length shouldn't be negative")
                continue
            }
            if (unitOne.type == "weight" && value < 0.0) {
                println("Weight shouldn't be negative")
                continue
            }
            if (unitOne.type == "temperature") {
                if (unitOne == UnitMeasure.CELSIUS && unitTwo == UnitMeasure.FAHRENHEIT) {
                    convertedValue = value * 9 / 5 + 32
                } else if (unitOne == UnitMeasure.CELSIUS && unitTwo == UnitMeasure.KELVIN) {
                    convertedValue = value + 273.15
                } else if (unitOne == UnitMeasure.FAHRENHEIT && unitTwo == UnitMeasure.CELSIUS) {
                    convertedValue = (value - 32) * 5 / 9
                } else if (unitOne == UnitMeasure.FAHRENHEIT && unitTwo == UnitMeasure.KELVIN) {
                    convertedValue = (value + 459.67) * 5 / 9
                } else if (unitOne == UnitMeasure.KELVIN && unitTwo == UnitMeasure.CELSIUS) {
                    convertedValue = value - 273.15
                } else if (unitOne == UnitMeasure.KELVIN && unitTwo == UnitMeasure.FAHRENHEIT) {
                    convertedValue = value * 9 / 5 - 459.67
                } else {
                    convertedValue = value * unitOne.rate / unitTwo.rate
                }
            } else {
                convertedValue = value * unitOne.rate / unitTwo.rate
            }
            println(
                "$value ${
                    if (value == 1.0) unitOne.measure[1]
                    else unitOne.measure[2]
                } is $convertedValue ${
                    if (convertedValue == 1.0) unitTwo.measure[1]
                    else unitTwo.measure[2]
                }"
            )
        } else {
            println("Conversion from ${unitOne.measure[2]} to ${unitTwo.measure[2]} is impossible")
            continue
        }
    }
}
