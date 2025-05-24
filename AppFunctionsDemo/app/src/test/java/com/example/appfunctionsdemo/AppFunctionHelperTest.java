package com.example.appfunctionsdemo;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.app.appfunctions.AppFunctionException;
import android.app.appfunctions.AppFunctionManager;
import android.app.appfunctions.ExecuteAppFunctionRequest;
import android.app.appfunctions.ExecuteAppFunctionResponse;
import android.app.appsearch.GenericDocument;
import android.content.Context; 
import android.os.Bundle;
import android.os.OutcomeReceiver;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

// --- Mock/Test Implementations ---

class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}

// Minimal Context mock for AppFunctionManager constructor
class MockTestContext extends Context {
    private final DirectExecutor directExecutor = new DirectExecutor();
    @Override public Executor getMainExecutor() { return directExecutor; }
    // Implement other abstract methods with default/null behavior
    @Override public android.content.res.AssetManager getAssets() { return null; }
    @Override public android.content.res.Resources getResources() { return null; }
    @Override public android.content.pm.PackageManager getPackageManager() { return null; }
    @Override public android.content.ContentResolver getContentResolver() { return null; }
    @Override public Looper getMainLooper() {
        // Ensure Looper for tests that might need it for Handlers.
        // AppFunctionManager uses Context.getMainExecutor(), which might involve Looper.
        if (Looper.myLooper() == null) {
             // Looper.prepare() can only be called once per thread.
            try {
                Looper.prepare();
            } catch (RuntimeException e) {
                // If already prepared, Looper.myLooper() will return it.
            }
        }
        return Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper();
    }
    @Override public android.content.Context getApplicationContext() { return this; }
    @Override public void setTheme(int resid) {}
    @Override public android.content.res.Resources.Theme getTheme() { return null; }
    @Override public ClassLoader getClassLoader() { return ClassLoader.getSystemClassLoader(); }
    @Override public String getPackageName() { return "com.example.appfunctionsdemo.test"; }
    @Override public android.content.pm.ApplicationInfo getApplicationInfo() { return null; }
    @Override public String getPackageResourcePath() { return null; }
    @Override public String getPackageCodePath() { return null; }
    @Override public android.content.SharedPreferences getSharedPreferences(String name, int mode) { return null; }
    @Override public boolean moveSharedPreferencesFrom(Context sourceContext, String name) { return false; }
    @Override public boolean deleteSharedPreferences(String name) { return false; }
    @Override public java.io.FileInputStream openFileInput(String name) throws java.io.FileNotFoundException { throw new java.io.FileNotFoundException(name); }
    @Override public java.io.FileOutputStream openFileOutput(String name, int mode) throws java.io.FileNotFoundException { throw new java.io.FileNotFoundException(name); }
    @Override public boolean deleteFile(String name) { return false; }
    @Override public java.io.File getFileStreamPath(String name) { return null; }
    @Override public java.io.File getDataDir() { return null; }
    @Override public java.io.File getFilesDir() { return null; }
    @Override public java.io.File getNoBackupFilesDir() { return null; }
    @Override public java.io.File getExternalFilesDir(String type) { return null; }
    @Override public java.io.File[] getExternalFilesDirs(String type) { return null; }
    @Override public java.io.File getObbDir() { return null; }
    @Override public java.io.File[] getObbDirs() { return null; }
    @Override public java.io.File getCacheDir() { return null; }
    @Override public java.io.File getCodeCacheDir() { return null; }
    @Override public java.io.File getExternalCacheDir() { return null; }
    @Override public java.io.File[] getExternalCacheDirs() { return null; }
    @Override public java.io.File getDir(String name, int mode) { return null; }
    @Override public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(String name, int mode, android.database.sqlite.SQLiteDatabase.CursorFactory factory) { return null; }
    @Override public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(String name, int mode, android.database.sqlite.SQLiteDatabase.CursorFactory factory, android.database.DatabaseErrorHandler errorHandler) { return null; }
    @Override public boolean moveDatabaseFrom(Context sourceContext, String name) { return false; }
    @Override public boolean deleteDatabase(String name) { return false; }
    @Override public java.io.File getDatabasePath(String name) { return null; }
    @Override public String[] databaseList() { return new String[0]; }
    @Override @Deprecated public android.graphics.drawable.Drawable getWallpaper() { return null; }
    @Override @Deprecated public android.graphics.drawable.Drawable peekWallpaper() { return null; }
    @Override @Deprecated public int getWallpaperDesiredMinimumWidth() { return 0; }
    @Override @Deprecated public int getWallpaperDesiredMinimumHeight() { return 0; }
    @Override @Deprecated public void setWallpaper(android.graphics.Bitmap bitmap) throws java.io.IOException {}
    @Override @Deprecated public void setWallpaper(java.io.InputStream data) throws java.io.IOException {}
    @Override @Deprecated public void clearWallpaper() throws java.io.IOException {}
    @Override public void startActivity(android.content.Intent intent) {}
    @Override public void startActivity(android.content.Intent intent, Bundle options) {}
    @Override public void startActivities(android.content.Intent[] intents) {}
    @Override public void startActivities(android.content.Intent[] intents, Bundle options) {}
    @Override public void startIntentSender(android.content.IntentSender intent, android.content.Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws android.content.IntentSender.SendIntentException {}
    @Override public void startIntentSender(android.content.IntentSender intent, android.content.Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws android.content.IntentSender.SendIntentException {}
    @Override public void sendBroadcast(android.content.Intent intent) {}
    @Override public void sendBroadcast(android.content.Intent intent, String receiverPermission) {}
    @Override public void sendOrderedBroadcast(android.content.Intent intent, String receiverPermission) {}
    @Override public void sendOrderedBroadcast(android.content.Intent intent, String receiverPermission, android.content.BroadcastReceiver resultReceiver, android.os.Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {}
    @Override public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user) {}
    @Override public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user, String receiverPermission) {}
    @Override public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user, String receiverPermission, android.content.BroadcastReceiver resultReceiver, android.os.Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {}
    @Override public void sendStickyBroadcast(android.content.Intent intent) {}
    @Override public void sendStickyOrderedBroadcast(android.content.Intent intent, android.content.BroadcastReceiver resultReceiver, android.os.Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {}
    @Override public void removeStickyBroadcast(android.content.Intent intent) {}
    @Override public void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user) {}
    @Override public void sendStickyOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user, android.content.BroadcastReceiver resultReceiver, android.os.Handler scheduler, int initialCode, String initialData, Bundle initialExtras) {}
    @Override public void removeStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle user) {}
    @Override public android.content.Intent registerReceiver(android.content.BroadcastReceiver receiver, android.content.IntentFilter filter) { return null; }
    @Override public android.content.Intent registerReceiver(android.content.BroadcastReceiver receiver, android.content.IntentFilter filter, int flags) { return null; }
    @Override public android.content.Intent registerReceiver(android.content.BroadcastReceiver receiver, android.content.IntentFilter filter, String broadcastPermission, android.os.Handler scheduler) { return null; }
    @Override public android.content.Intent registerReceiver(android.content.BroadcastReceiver receiver, android.content.IntentFilter filter, String broadcastPermission, android.os.Handler scheduler, int flags) { return null; }
    @Override public void unregisterReceiver(android.content.BroadcastReceiver receiver) {}
    @Override public android.content.ComponentName startService(android.content.Intent service) { return null; }
    @Override public android.content.ComponentName startForegroundService(android.content.Intent service) { return null; }
    @Override public boolean stopService(android.content.Intent service) { return false; }
    @Override public boolean bindService(android.content.Intent service, android.content.ServiceConnection conn, int flags) { return false; }
    @Override public void unbindService(android.content.ServiceConnection conn) {}
    @Override public boolean isRestricted() { return false; }
    @Override public Context createPackageContext(String packageName, int flags) throws android.content.pm.PackageManager.NameNotFoundException { return this; }
    @Override public Context createContextForSplit(String splitName) throws android.content.pm.PackageManager.NameNotFoundException { return this; }
    @Override public Context createConfigurationContext(android.content.res.Configuration overrideConfiguration) { return this; }
    @Override public Context createDisplayContext(android.view.Display display) { return this; }
    @Override public Context createDeviceProtectedStorageContext() { return this; }
    @Override public boolean isDeviceProtectedStorage() { return false; }
    @Override public Object getSystemService(String name) { return null; }
    @Override public String getSystemServiceName(Class<?> serviceClass) { return null; }
    @Override public int checkPermission(String permission, int pid, int uid) { return android.content.pm.PackageManager.PERMISSION_DENIED; }
    @Override public int checkCallingPermission(String permission) { return android.content.pm.PackageManager.PERMISSION_DENIED; }
    @Override public int checkCallingOrSelfPermission(String permission) { return android.content.pm.PackageManager.PERMISSION_DENIED; }
    @Override public int checkSelfPermission(String permission) { return android.content.pm.PackageManager.PERMISSION_DENIED; }
    @Override public void enforcePermission(String permission, int pid, int uid, String message) {}
    @Override public void enforceCallingPermission(String permission, String message) {}
    @Override public void enforceCallingOrSelfPermission(String permission, String message) {}
    @Override public void grantUriPermission(String toPackage, android.net.Uri uri, int modeFlags) {}
    @Override public void revokeUriPermission(android.net.Uri uri, int modeFlags) {}
    @Override public void revokeUriPermission(String toPackage, android.net.Uri uri, int modeFlags) {}
    @Override public int checkUriPermission(android.net.Uri uri, int pid, int uid, int modeFlags) { return android.content.pm.PackageManager.PERMISSION_DENIED; }
}


class TestAppFunctionManager extends AppFunctionManager {
    public ExecuteAppFunctionRequest lastRequest;
    public Executor lastExecutor;
    public OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> lastCallback;

    public TestAppFunctionManager(Context context) {
        super(context); 
    }

    @Override
    public void executeAppFunction(
            ExecuteAppFunctionRequest request,
            Executor executor,
            OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback) {
        this.lastRequest = request;
        this.lastExecutor = executor;
        this.lastCallback = callback;
    }
}

class TestAppFunctionException extends AppFunctionException {
    public TestAppFunctionException(String message) {
        super(message);
    }
}

class TestExecuteAppFunctionResponse extends ExecuteAppFunctionResponse {
    public TestExecuteAppFunctionResponse() {
        // The ExecuteAppFunctionResponse constructor is not public.
        // We must use its Builder.
        super(new ExecuteAppFunctionResponse.Builder().build());
    }
}


@RunWith(JUnit4.class)
public class AppFunctionHelperTest {

    private static final String TEST_PACKAGE_NAME = "com.example.test";
    private static final String TEST_FUNCTION_ID = "testFunction";
    private static final MockTestContext MOCK_CONTEXT = new MockTestContext();

    // Ensure Looper is prepared for tests that might need it for Handlers (e.g. AppFunctionManager's default executor)
    private static void prepareTestLooper() {
        if (Looper.myLooper() == null) {
            try {
                Looper.prepare();
            } catch (RuntimeException e) {
                // Ignore if already prepared
            }
        }
    }

    @Test
    public void createRequest_correctlyCreatesRequest_withPackageAndFunctionId() {
        ExecuteAppFunctionRequest request = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, null);

        assertNotNull(request);
        assertEquals(TEST_PACKAGE_NAME, request.getTargetPackageName());
        assertEquals(TEST_FUNCTION_ID, request.getFunctionIdentifier());
        
        assertNotNull(request.getParameters()); 
        // For GenericDocument, an "empty" one still has a namespace, id, and schema type from its builder.
        // We can't easily check for "emptiness" beyond it not being null and perhaps specific default values if known.
        // The key is that it's not null, as per Builder behavior.
        
        assertNotNull(request.getExtras());     
        assertTrue(request.getExtras().isEmpty());
    }

    @Test
    public void createRequest_includesParams_whenProvided() {
        GenericDocument params = new GenericDocument.Builder<>("namespace", "id", "schemaType")
                .setPropertyString("key", "value")
                .build();
        ExecuteAppFunctionRequest request = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, params, null);

        assertNotNull(request);
        assertEquals(params, request.getParameters());
    }

    @Test
    public void createRequest_includesExtras_whenProvided() {
        Bundle extras = new Bundle();
        extras.putString("testKey", "testValue");
        ExecuteAppFunctionRequest request = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, extras);

        assertNotNull(request);
        assertEquals(extras, request.getExtras());
    }

    @Test
    public void createRequest_handlesNullParamsAndExtras() {
        ExecuteAppFunctionRequest request = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, null);

        assertNotNull(request);
        assertNotNull(request.getParameters()); 
        assertNotNull(request.getExtras());     
        assertTrue(request.getExtras().isEmpty());
    }

    @Test
    public void createRequest_includesBothParamsAndExtras_whenProvided() {
        GenericDocument params = new GenericDocument.Builder<>("ns", "id1", "typeA").build();
        Bundle extras = new Bundle();
        extras.putString("testKey", "testValue");
        ExecuteAppFunctionRequest request = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, params, extras);

        assertNotNull(request);
        assertEquals(params, request.getParameters());
        assertEquals(extras, request.getExtras());
    }

    @Test
    public void executeAppFunction_callsManagerWithCorrectParameters() {
        prepareTestLooper();
        TestAppFunctionManager testManager = new TestAppFunctionManager(MOCK_CONTEXT);
        ExecuteAppFunctionRequest testRequest = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, null);
        
        final AtomicReference<ExecuteAppFunctionResponse> responseRef = new AtomicReference<>();
        final AtomicReference<AppFunctionException> errorRef = new AtomicReference<>();

        OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback =
                new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() {
                    @Override public void onResult(ExecuteAppFunctionResponse result) { responseRef.set(result); }
                    @Override public void onError(AppFunctionException error) { errorRef.set(error); }
                };

        AppFunctionHelper.executeAppFunction(testManager, testRequest, callback);

        assertEquals(testRequest, testManager.lastRequest);
        assertSame(callback, testManager.lastCallback);
        assertNotNull(testManager.lastExecutor); 
        // Check if the executor is the main executor from context
        assertSame(MOCK_CONTEXT.getMainExecutor(), testManager.lastExecutor);
    }

    @Test
    public void executeAppFunction_onResultCalled_onManagerSuccess() {
        prepareTestLooper();
        TestAppFunctionManager testManager = new TestAppFunctionManager(MOCK_CONTEXT);
        ExecuteAppFunctionRequest testRequest = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, null);
        final ExecuteAppFunctionResponse expectedResponse = new TestExecuteAppFunctionResponse();
        
        final AtomicReference<ExecuteAppFunctionResponse> responseRef = new AtomicReference<>();
        final AtomicReference<AppFunctionException> errorRef = new AtomicReference<>();

        OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback =
                new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() {
                    @Override public void onResult(ExecuteAppFunctionResponse result) { responseRef.set(result); }
                    @Override public void onError(AppFunctionException error) { errorRef.set(error); }
                };

        AppFunctionHelper.executeAppFunction(testManager, testRequest, callback);
        
        assertNotNull("Callback should have been captured by TestAppFunctionManager", testManager.lastCallback);
        assertNotNull("Executor should have been captured by TestAppFunctionManager", testManager.lastExecutor);
        testManager.lastExecutor.execute(() -> testManager.lastCallback.onResult(expectedResponse));

        assertEquals(expectedResponse, responseRef.get());
        assertNull(errorRef.get()); 
    }

    @Test
    public void executeAppFunction_onErrorCalled_onManagerError() {
        prepareTestLooper();
        TestAppFunctionManager testManager = new TestAppFunctionManager(MOCK_CONTEXT);
        ExecuteAppFunctionRequest testRequest = AppFunctionHelper.createRequest(
                TEST_PACKAGE_NAME, TEST_FUNCTION_ID, null, null);
        final AppFunctionException expectedException = new TestAppFunctionException("Test error from manager");

        final AtomicReference<ExecuteAppFunctionResponse> responseRef = new AtomicReference<>();
        final AtomicReference<AppFunctionException> errorRef = new AtomicReference<>();

        OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException> callback =
                new OutcomeReceiver<ExecuteAppFunctionResponse, AppFunctionException>() {
                    @Override public void onResult(ExecuteAppFunctionResponse result) { responseRef.set(result); }
                    @Override public void onError(AppFunctionException error) { errorRef.set(error); }
                };

        AppFunctionHelper.executeAppFunction(testManager, testRequest, callback);
        
        assertNotNull("Callback should have been captured by TestAppFunctionManager", testManager.lastCallback);
        assertNotNull("Executor should have been captured by TestAppFunctionManager", testManager.lastExecutor);
        testManager.lastExecutor.execute(() -> testManager.lastCallback.onError(expectedException));

        assertEquals(expectedException, errorRef.get());
        assertNull(responseRef.get());
    }
}
