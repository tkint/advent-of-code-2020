import java.lang.Exception

@ExperimentalStdlibApi
fun main() {
    withInputData("day07.txt") { data ->
        val rules = data.map { Day07.Rule.fromString(it) }

        val bagsContainingAtLeastOneShinyGold = rules.flatMap { rule ->
            Day07.bagsWithAtLeastOne(rule, "shiny gold", rules)
        }.toSet()

        println(bagsContainingAtLeastOneShinyGold.size)

        val nbBagsInShinyGold = Day07.countRequiredBagsInOneColor(rules, "shiny gold")

        println(nbBagsInShinyGold)
    }
}

@ExperimentalStdlibApi
object Day07 {
    data class Rule(
        val color: String,
        val children: Map<String, Int>,
    ) {
        companion object {
            private val ruleRegex = Regex("(.*) bags contain (.*)")
            private val containsRegex = Regex("([0-9]*) (.*) bag.*")

            fun fromString(str: String): Rule {
                return ruleRegex.find(str)?.let {
                    val (color, contains) = it.destructured
                    Rule(
                        color = color,
                        children = strToChildren(contains),
                    )
                } ?: throw Exception("Rule $str not valid")
            }

            private fun strToChildren(str: String): Map<String, Int> {
                return if (str.contains("no other bags")) mapOf()
                else {
                    str.split(",").map { s ->
                        containsRegex.find(s.trim())?.let {
                            val (nb, color) = it.destructured
                            color to nb.toInt()
                        } ?: throw Exception("Contains $str not valid")
                    }.toMap()
                }
            }
        }
    }

    fun bagsWithAtLeastOne(rule: Rule, color: String, rules: List<Rule>): Set<String> {
        val bags = mutableSetOf<String>()

        rule.children.forEach { (childColor, _) ->
            if (childColor == color) {
                bags.add(rule.color)
            }

            val childRule = rules.find { it.color == childColor }
                ?: throw Exception("Rule $childColor not found")

            if (childRule.children.containsKey(color)) {
                bags.add(rule.color)
                bags.add(childColor)
            }

            val subBags = bagsWithAtLeastOne(childRule, color, rules)
            if (subBags.isNotEmpty()) {
                bags.add(rule.color)
                bags.add(childColor)
                bags.plus(subBags)
            }
        }

        return bags
    }

    fun countRequiredBagsInOneColor(rules: List<Rule>, color: String, depth: Int = 0): Int {
        val rule = rules.find { it.color == color }
            ?: throw Exception("Rule $color not found")

        var nb = if (rule.color == color) 1 else 0
        rule.children.map { (childColor, childNb) ->
            nb += countRequiredBagsInOneColor(rules, childColor, depth + 1) * childNb
        }

        return if (depth == 0) nb - 1 else nb
    }
}
