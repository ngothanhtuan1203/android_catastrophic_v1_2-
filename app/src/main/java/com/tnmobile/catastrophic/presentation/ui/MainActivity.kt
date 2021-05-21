package com.tnmobile.catastrophic.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tnmobile.catastrophic.R
import com.tnmobile.catastrophic.presentation.ui.screen.MainFragment
import com.tnmobile.catastrophic.utilily.TNLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            TNLog.d("MainActivity","DEBUG onCreate");
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}