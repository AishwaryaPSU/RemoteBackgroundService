package tech.lemnis.remotebackgroundservice.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


public class RemoteDataService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private IRemoteService.Stub binder = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //Do nothing
        }

        @Override
        public double doSomething(String largeString, int viewId, int delayInMilliSeconds) throws RemoteException {
            try {
                Thread.sleep(delayInMilliSeconds);
                double size = largeString.getBytes("UTF-8").length / 1024.0;
                return size;
            } catch (Exception exception){
                Log.e("Error", exception.getMessage());
                exception.printStackTrace();
            }
            return 0.0;
        }

        @Override
        public void doSomethingAsync(String largeString, int viewId, int delayInMilliSeconds, IAsyncCallback asyncCallback) throws RemoteException {
            double size = doSomething(largeString,viewId,delayInMilliSeconds);
            Log.i("Info","about to call asyncCallback handleResponse with "+ size);
            asyncCallback.handleResponse(""+size);
        }


    };
}
