package com.wayne.expandabletextview.demo

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.wayne.expandabletextview.ExpandableTextView
import com.wayne.expandabletextview.OnStateChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_test.stateChangeListener = object : OnStateChangeListener {
            override fun onStateChanged(state: ExpandableTextView.State) {
                tv_current_state.text = state.name
            }
        }
        // change font
        et_test.textTypeface = ResourcesCompat.getFont(this, R.font.roboto_bold)
    }
}