package com.darrenthiores.efifalite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import com.darrenthiores.efifalite.ui.theme.EFifaLiteTheme
import com.darrenthiores.efifalite.viewModel.AppViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import org.koin.android.ext.android.inject

@ExperimentalPagerApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: AppViewModel by inject()
        setContent {
            EFifaLiteTheme {
                EFifaApp(
                    viewModel = viewModel
                )
            }
        }
    }
}