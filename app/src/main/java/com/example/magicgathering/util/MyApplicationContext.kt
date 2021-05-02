package com.example.magicgathering.util

import android.content.Context

abstract class MyApplicationContext {
    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getContext(): Context{
            return context
        }
    }

}