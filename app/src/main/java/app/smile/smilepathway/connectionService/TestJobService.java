package app.smile.smilepathway.connectionService;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import app.smile.smilepathway.SmilePathWayApplicaion;
import app.smile.smilepathway.utils.Utils;


/**
 * Created by user on 4/4/18.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {
    private static final String TAG = "Waselconnection";

    @Override
    public boolean onStartJob(JobParameters params) {
      try {
          if (SmilePathWayApplicaion.getSocket(getApplicationContext())!=null && !SmilePathWayApplicaion.getSocket(getApplicationContext()).connected()) {
              Log.d(TAG, "job schudler running");
              Intent service = new Intent(getApplicationContext(), ConnectionService.class);
              getApplicationContext().startService(service);
          } // reschedule the job
      }
      catch (Exception e){
          e.printStackTrace();
      }
        Utils.scheduleJob(getApplicationContext());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"job schudler stop");
        return true;
    }




}