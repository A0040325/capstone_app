package com.example.capstone

import android.view.View

object LoadingHelper {
    fun toggleLoading(view1: View?, view2: View?, isLoading: Boolean) {
        if (!isLoading) {
            view1?.visibility = View.GONE
            view2?.visibility = View.VISIBLE
        } else {
            view1?.visibility = View.VISIBLE
            view2?.visibility = View.GONE
        }
    }
}