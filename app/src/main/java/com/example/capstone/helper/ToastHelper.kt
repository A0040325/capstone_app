package com.example.capstone.helper

import android.content.Context
import android.widget.Toast

object ToastHelper {
    fun showToast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}