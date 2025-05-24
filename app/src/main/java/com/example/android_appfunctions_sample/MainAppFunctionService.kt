package com.example.android_appfunctions_sample

import android.app.appfunctions.AppFunctionException
import android.app.appfunctions.AppFunctionService
import android.app.appfunctions.ExecuteAppFunctionRequest
import android.app.appfunctions.ExecuteAppFunctionResponse
import android.app.appsearch.GenericDocument
import android.content.pm.SigningInfo
import android.os.Build
import android.os.CancellationSignal
import android.os.OutcomeReceiver
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.BAKLAVA)
class MainAppFunctionService : AppFunctionService() {
    override fun onExecuteFunction(
        p0: ExecuteAppFunctionRequest,
        p1: String,
        p2: SigningInfo,
        p3: CancellationSignal,
        p4: OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>
    ) {
        p4.onResult(
            ExecuteAppFunctionResponse(
                GenericDocument.Builder<GenericDocument.Builder<*>>(
                    "com.example.android_appfunctions_sample",
                    "com.example.android_appfunctions_sample",
                    "com.example.android_appfunctions_sample",
                ).setPropertyString("key", "value").build()
            )
        )
    }
}