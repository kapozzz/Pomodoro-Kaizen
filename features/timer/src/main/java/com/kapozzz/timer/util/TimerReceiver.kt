package com.kapozzz.timer.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kapozzz.timer.presentation.TimerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TimerReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.Main).launch {
            flow.emit(TimerEvent.SwitchTimer)
        }
    }

    companion object {
        val flow: MutableSharedFlow<TimerEvent> =  MutableSharedFlow()
    }
}