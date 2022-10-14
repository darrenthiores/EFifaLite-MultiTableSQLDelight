package com.darrenthiores.efifalite.screen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.darrenthiores.efifalite.component.LoginCard
import com.darrenthiores.efifalite.component.OverviewCarousel
import com.darrenthiores.efifalite.component.StartingCard
import com.darrenthiores.efifalite.receiver.AlarmReceiver
import com.darrenthiores.efifalite.viewModel.OverviewViewModel
import com.darrenthiores.efifalite.workManager.LoginWorker
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun OverviewScreen(
    onCarouselClicked: () -> Unit,
    onStartingCardClicked: () -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val viewModel = getViewModel<OverviewViewModel>()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .semantics { contentDescription = "Overview Screen" }
    ) {
        OverviewCarousel(
            modifier = Modifier.padding(top = 16.dp),
            onClick = onCarouselClicked
        )
        val login = viewModel.getLogin().collectAsState(initial = false)
        val context = LocalContext.current
        LoginCard(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            isLogin = login.value,
            collectLogin = {
                viewModel.updateLogin(false)
                viewModel.updateCoin(100)
                setWork(context)
            },
            showDialog = showDialog,
            onDismiss = onDismiss
        )
        val starting = viewModel.starting.collectAsState()
        StartingCard(
            modifier = Modifier.padding(bottom = 32.dp),
            data = starting.value,
            onClickSeeAll = onStartingCardClicked
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
private fun setDaily(context: Context){
    val alarmManager = context.getSystemService<AlarmManager>()

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 6)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    var timeMillis = calendar.timeInMillis

    if(timeMillis-System.currentTimeMillis() < 0) {
        timeMillis += 86400000
    }

    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

    alarmManager?.set(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent)
    Timber.d("Alarm Set to $timeMillis")
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
private fun setWork(context: Context) {
    val workManager = WorkManager.getInstance(context)

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 6)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    var timeMillis = calendar.timeInMillis

    if(timeMillis-System.currentTimeMillis() < 0) {
        timeMillis += 86400000
    }

    val delayMillis = timeMillis - System.currentTimeMillis()

    val oneTimeWorkRequest = OneTimeWorkRequest.Builder(LoginWorker::class.java)
        .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
        .build()
    workManager.enqueue(oneTimeWorkRequest)

    Timber.d("Work Manager Set $delayMillis")
}