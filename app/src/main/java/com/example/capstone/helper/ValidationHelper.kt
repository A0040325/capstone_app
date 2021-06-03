package com.example.capstone.helper

import android.widget.EditText

object ValidationHelper {
    fun checkEmpty(
        input: EditText,
    ): Boolean {
        if (input.text.toString().isEmpty()) {
            input.error = "harus diisi!"
            return true
        }
        return false
    }

    fun checkSame(
        input1: EditText,
        input2: EditText,
    ): Boolean {
        if (input1.text.toString() != input2.text.toString()) {
            input2.error = "isian tidak sama!"
            return true
        }
        return false
    }

    fun checkMinimum(
        input1: EditText,
        min: Int = 6,
    ): Boolean {
        if (input1.text.toString().length < min) {
            input1.error = "minimal $min karakter!"
            return true
        }

        return false
    }
}