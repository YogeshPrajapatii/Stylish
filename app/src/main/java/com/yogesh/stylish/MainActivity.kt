package com.yogesh.stylish

// Sahi naam import karein, agar Android Studio na kare to manually karein
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. AuthRepositoryImpl ko SAHI NAAM se aur BINA CONTEXT ke banayein
        val authRepository = AuthRepositoryImp() // <-- Galti #1 aur #2 theek hui

        val userPreferenceManager = UserPreferenceManager(applicationContext)
        val userPrefRepo = UserPreferenceRepositoryImp(userPreferenceManager)
        val readUseCase = ReadOnboardingStatusUseCase(userPrefRepo)

        // 2. Ab Factory banayein
        val factory = SplashViewModelFactory(readUseCase, authRepository)

        // 3. Factory ka istemal karke ViewModel ka instance banayein
        val splashViewModel: SplashViewModel =
            ViewModelProvider(this, factory)[SplashViewModel::class.java]

        setContent {
            MyFirstComposeAppTheme {
                // 4. Bane hue ViewModel ko Navigation function me pass karein
                Navigation(viewModel = splashViewModel)
            }
        }
    }
}