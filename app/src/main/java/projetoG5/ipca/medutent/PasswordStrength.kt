package projetoG5.ipca.medutent

import android.graphics.Color

enum class PasswordStrength

constructor(var resId: Int, color: Int) {

    FRACA(R.string.passwordLevel, Color.RED),
    MEDIA(R.string.passwordLevel, Color.YELLOW),
    FORTE(R.string.passwordLevel, Color.GREEN),
    MUITO_FORTE(R.string.passwordLevel, Color.BLUE);

    var color: Int = 0
        internal set

    init {
        this.color = color
    }

    fun getText(ctx: android.content.Context): CharSequence {
        return ctx.getText(resId)
    }

    companion object {


        var REQUIRED_LENGTH = 8

        var MAXIMUM_LENGTH = 15

        var REQUIRE_SPECIAL_CHARACTERS = true

        var REQUIRE_DIGITS = true

        var REQUIRE_LOWER_CASE = true

        var REQUIRE_UPPER_CASE = false

        fun calculateStrength(password: String): PasswordStrength {
            var currentScore = 0
            var sawUpper = false
            var sawLower = false
            var sawDigit = false
            var sawSpecial = false


            for (i in 0 until password.length) {
                val c = password[i]

                if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                    currentScore += 1
                    sawSpecial = true
                } else {
                    if (!sawDigit && Character.isDigit(c)) {
                        currentScore += 1
                        sawDigit = true
                    } else {
                        if (!sawUpper || !sawLower) {
                            if (Character.isUpperCase(c))
                                sawUpper = true
                            else
                                sawLower = true
                            if (sawUpper && sawLower)
                                currentScore += 1
                        }
                    }
                }

            }

            if (password.length > REQUIRED_LENGTH) {
                if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial
                        || REQUIRE_UPPER_CASE && !sawUpper
                        || REQUIRE_LOWER_CASE && !sawLower
                        || REQUIRE_DIGITS && !sawDigit) {
                    currentScore = 1
                } else {
                    currentScore = 2
                    if (password.length > MAXIMUM_LENGTH) {
                        currentScore = 3
                    }
                }
            } else {
                currentScore = 0
            }

            when (currentScore) {
                0 -> return FRACA
                1 -> return MEDIA
                2 -> return FORTE
                3 -> return MUITO_FORTE
            }

            return MUITO_FORTE
        }
    }

}