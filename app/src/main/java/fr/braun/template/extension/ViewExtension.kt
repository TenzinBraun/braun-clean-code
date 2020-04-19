package fr.braun.template.extension

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.show(){
    this.visibility = VISIBLE
}

fun View.hide(){
    this.visibility = INVISIBLE
}