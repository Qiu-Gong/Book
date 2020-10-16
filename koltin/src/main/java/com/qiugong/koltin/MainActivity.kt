package com.qiugong.koltin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val a = text?.let {

        }

        val e = text?.also {

        }

        val b = with(text){
            text = ""
            visibility = View.GONE
        }

        val c = text?.run{

        }

        val d = text?.apply {

        }

        val at :AtomicBoolean

        CoroutineScope
    }
}

