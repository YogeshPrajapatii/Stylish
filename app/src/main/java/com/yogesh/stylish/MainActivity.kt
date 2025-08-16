package com.yogesh.stylish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.yogesh.stylish.data.local.UserPreferenceManager
import com.yogesh.stylish.data.repositoryimp.AuthRepositoryImp
import com.yogesh.stylish.data.repositoryimp.UserPreferenceRepositoryImp
import com.yogesh.stylish.domain.usecase.ReadOnboardingStatusUseCase
import com.yogesh.stylish.presentation.navigation.Navigation
import com.yogesh.stylish.presentation.ui.screens.splashscreen.SplashViewModel
import com.yogesh.stylish.presentation.ui.screens.splashscreen.SplashViewModelFactory
import com.yogesh.stylish.presentation.ui.theme.MyFirstComposeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // <-- Ye annotation zaroori hai

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authRepository = AuthRepositoryImp()

        val userPreferenceManager = UserPreferenceManager(applicationContext)
        val userPrefRepo = UserPreferenceRepositoryImp(userPreferenceManager)
        val readUseCase = ReadOnboardingStatusUseCase(userPrefRepo)

        val factory = SplashViewModelFactory(readUseCase, authRepository)

        val splashViewModel: SplashViewModel =
            ViewModelProvider(this, factory)[SplashViewModel::class.java]

        setContent {
            MyFirstComposeAppTheme {
                Navigation(viewModel = splashViewModel)
            }
        }
    }
}