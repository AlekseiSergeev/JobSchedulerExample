package com.example.jobschedulerexample

import android.app.job.JobParameters
import android.app.job.JobService
import timber.log.Timber

class ExampleJobService: JobService() {
    private var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Timber.d("Job started")

        doBackgroundWork(params)

        return true
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread(object : Runnable {
            override fun run() {
                for(i in 1..10) {
                    Timber.d("Run: $i")
                    if(jobCancelled) {
                        return
                    }
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

                Timber.d("Job finished")
                jobFinished(params, false)
            }
        }).start()
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Timber.d("Job cancelled before completion")
        jobCancelled = true

        return true
    }
}