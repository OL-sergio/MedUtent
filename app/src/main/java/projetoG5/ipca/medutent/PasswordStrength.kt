package projetoG5.ipca.medutent

import android.graphics.Color
import android.icu.util.IslamicCalendar
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import java.util.regex.Matcher
import java.util.regex.Pattern

class PasswordStrength : TextWatcher {

    val strengthLevel: MutableLiveData<String> = MutableLiveData()
    val strengthColor: MutableLiveData<Int> = MutableLiveData()

    var lowerCase: MutableLiveData<Int> = MutableLiveData(0)
    var upperCase: MutableLiveData<Int> = MutableLiveData(0)
    var digit: MutableLiveData<Int> = MutableLiveData(0)
    var specialChar: MutableLiveData<Int> = MutableLiveData(0)

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(char: CharSequence?, start: Int, before: Int, count: Int) {
        if(char != null){
            lowerCase.value = if(char.hasLowerCase()) {1} else {0}
            upperCase.value = if(char.hasUpperCase()) {1} else {0}
            digit.value = if(char.hasDigit()) {1} else {0}
            specialChar.value = if(char.hasSpecialChar()) {1} else {0}
            calculateStrenght(char)
        }
    }

    private fun calculateStrenght(password: CharSequence) {
        if(password.length in 0..8){
            strengthColor.value = R.color.weak
            strengthLevel.value = "Fraca"
        }else if(password.length in 8..10){
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1){
                strengthColor.value = R.color.medium
                strengthLevel.value = "Média"
            }
        }else if (password.length in 11..13){
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1){
               if(lowerCase.value == 1 && upperCase.value == 1){
                   strengthColor.value = R.color.strong
                   strengthLevel.value = "Forte"
               }
            }else if(password.length >= 13){
                if (lowerCase.value == 1 && upperCase.value == 1 && digit.value == 1 && specialChar.value == 1){
                    strengthColor.value = R.color.veryStrong
                    strengthLevel.value = "Muito Forte"
                }
            }
        }
    }

    private fun CharSequence.hasLowerCase() : Boolean{
        val pattern: Pattern = Pattern.compile("[a-z]")
        val hasLowerCase: Matcher = pattern.matcher(this)
        return hasLowerCase.find()
    }
    private fun CharSequence.hasUpperCase(): Boolean{
        val pattern: Pattern = Pattern.compile("[A-Z]")
        val hasUpperCase: Matcher = pattern.matcher(this)
        return hasUpperCase.find()
    }

    private fun CharSequence.hasDigit() : Boolean{
        val pattern: Pattern = Pattern.compile("[0-9]")
        val hasDigit: Matcher = pattern.matcher(this)
        return hasDigit.find()
    }
    private fun CharSequence.hasSpecialChar() : Boolean{
        val pattern: Pattern = Pattern.compile("[!@#$%^&*()_+-{}/.<>|\\[\\]~]")
        val hasSpecialChar: Matcher = pattern.matcher(this)
        return hasSpecialChar.find()
    }



}

