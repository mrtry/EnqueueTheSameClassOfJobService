package io.mrtry.enqueuethesameclassofjobservice

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.util.*


/**
 * Created by mrtry on 2018/08/08.
 */
class TestJobService  : JobService() {
    override fun onStopJob(p0: JobParameters?): Boolean {
        Log.d(TAG, "onStopJob")
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob")

        Thread(
                Runnable {
                    Log.d(TAG, "5s elapsed. @JobID:${params!!.jobId}")
                    sleep(5 * 1000)

                    jobFinished(params,false)
                }
        ).start()

        return true
    }

    fun sleep(mSec: Long) {
        Thread.sleep(mSec)
    }

    companion object {
        val TAG = TestJobService::class.java.simpleName

        fun registerToJobScheduler(context: Context, index: Int) {
            val jobInfo = JobInfo.Builder(index, ComponentName(context, TestJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setOverrideDeadline(0)
                    .build()

            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            scheduler.schedule(jobInfo)
        }
    }
}