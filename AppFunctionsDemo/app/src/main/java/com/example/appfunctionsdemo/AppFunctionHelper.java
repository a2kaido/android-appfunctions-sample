package com.example.appfunctionsdemo;

import android.app.appfunctions.ExecuteAppFunctionRequest;
import android.app.appsearch.GenericDocument;
import android.os.Bundle;
import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.ExecuteAppFunctionResponse;
import android.app.appfunctions.AppFunctionException;
import android.os.OutcomeReceiver;

public final class AppFunctionHelper {

    private AppFunctionHelper() {
        // Utility class, not meant to be instantiated.
    }

    public static ExecuteAppFunctionRequest createRequest(
            String targetPackageName,
            String functionIdentifier,
            GenericDocument params,
            Bundle extras) {
        ExecuteAppFunctionRequest.Builder builder =
                new ExecuteAppFunctionRequest.Builder(targetPackageName, functionIdentifier);

        if (params != null) {
            builder.setParameters(params);
        }

        if (extras != null) {
            builder.setExtras(extras);
        }

        return builder.build();
    }

    public static void executeAppFunction(
            AppFunctionManager appFunctionManager,
            ExecuteAppFunctionRequest request,
            OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback) {
        appFunctionManager.executeAppFunction(request, callback);
    }
}
