package com.example.jobschedulerexample

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.BuildConfig
import com.example.jobschedulerexample.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scheduleBtn.setOnClickListener {
            val componentName = ComponentName(this, ExampleJobService::class.java)
            val info = JobInfo.Builder(111, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build()

            val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val resultCode = scheduler.schedule(info)
            if(resultCode == JobScheduler.RESULT_SUCCESS) {
                Timber.d("Job scheduled")
            } else {
                Timber.d("Job scheduling failed")
            }
        }

        binding.cancelBtn.setOnClickListener {
            val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.cancel(111)
            Timber.d("Job cancelled")
        }
    }
}