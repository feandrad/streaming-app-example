package com.outcomehealth.app.ui.base

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open fun activityCreated(bundle: Bundle?) {
    }

    open fun activityResumed(context: Context) {
    }

    open fun activityPaused(context: Context) {
    }
}