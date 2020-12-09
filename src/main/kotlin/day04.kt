fun main() {
    withInputData("day04.txt") { data ->
        var i = 0

        val passports = mutableListOf<Day04.Passport>()

        var passport = ""
        while (i < data.size) {
            passport += " ${data[i]}"
            if (data[i].isBlank() || i == data.lastIndex) {
                passports.add(Day04.Passport.decode(passport))
                passport = ""
            }
            i++
        }

        val nbValid = passports.count { it.isValid() }
        println(nbValid)

        val nbStrictlyValid = passports.count { it.isStrictlyValid() }
        println(nbStrictlyValid)
    }
}

object Day04 {
    data class Passport(
        val byr: String?,
        val iyr: String?,
        val eyr: String?,
        val hgt: String?,
        val hcl: String?,
        val ecl: String?,
        val pid: String?,
        val cid: String?,
    ) {
        companion object {
            fun decode(str: String): Passport {
                val fields = str.split(" ")
                return Passport(
                    byr = getFieldValue(fields, "byr"),
                    iyr = getFieldValue(fields, "iyr"),
                    eyr = getFieldValue(fields, "eyr"),
                    hgt = getFieldValue(fields, "hgt"),
                    hcl = getFieldValue(fields, "hcl"),
                    ecl = getFieldValue(fields, "ecl"),
                    pid = getFieldValue(fields, "pid"),
                    cid = getFieldValue(fields, "cid"),
                )
            }

            private fun getFieldValue(fields: List<String>, field: String) =
                fields.firstOrNull { it.startsWith("$field:") }
                    ?.removePrefix("$field:")
        }

        fun isValid(): Boolean = !(byr.isNullOrBlank() ||
                iyr.isNullOrBlank() ||
                eyr.isNullOrBlank() ||
                hgt.isNullOrBlank() ||
                hcl.isNullOrBlank() ||
                ecl.isNullOrBlank() ||
                pid.isNullOrBlank())

        fun isStrictlyValid(): Boolean = isValidYear(byr, 1920, 2002) &&
                isValidYear(iyr, 2010, 2020) &&
                isValidYear(eyr, 2020, 2030) &&
                isValidHeight() &&
                isValidHairColor() &&
                isValidEyeColor() &&
                isValidPassportId()

        private fun isValidYear(str: String?, min: Int, max: Int) =
            str != null && str.length == 4 && str.toInt() in min..max

        private fun isValidHeight() = when {
            hgt.isNullOrBlank() -> false
            hgt.endsWith("cm") -> hgt.removeSuffix("cm").toInt() in 150..193
            hgt.endsWith("in") -> hgt.removeSuffix("in").toInt() in 59..76
            else -> false
        }

        private fun isValidHairColor() =
            hcl != null && Regex("#[0-9a-f]{6}").matches(hcl)

        private fun isValidEyeColor() =
            ecl != null && listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl)

        private fun isValidPassportId() =
            pid != null && pid.length == 9
    }
}
