package com.yogesh.stylish.presentation.ui.screens.onboardingscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yogesh.stylish.R
import com.yogesh.stylish.data.local.UserPreferenceManager
import com.yogesh.stylish.data.repositoryimp.UserPreferenceRepositoryImp
import com.yogesh.stylish.domain.usecase.SaveOnboardingStatusUseCase
import com.yogesh.stylish.presentation.navigation.Routes

@Composable
fun OnBoarding1(navController: NavHostController) {

    // ViewModel ko yahan bhi waise hi initialize karein
    val context = LocalContext.current
    val userPreferenceManager = UserPreferenceManager(context)
    val userPrefRepo = UserPreferenceRepositoryImp(userPreferenceManager)
    val saveUseCase = SaveOnboardingStatusUseCase(userPrefRepo)
    val factory = OnboardingViewModelFactory(saveUseCase)
    val onboardingViewModel: OnboardingViewModel = viewModel(factory = factory)

    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {

        Row(Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("1/3")
            Text(text = "Skip", modifier = Modifier.clickable {
                onboardingViewModel.onOnboardingFinished()
                navController.navigate(Routes.Login)
            })
        }

        Box(Modifier
            .fillMaxWidth()
            .padding(top = 28.dp), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(R.drawable.obtwo),
                contentDescription = "OnBoarding Screen One")
        }


        Column(modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Make Payment",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp))

            Text("Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp))
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.End) {
            Text("Next",
                color = Color.Red,
                modifier = Modifier.clickable { navController.navigate(Routes.OnBoarding2) })
        }
    }


}

@Preview(showSystemUi = true)
@Composable
fun OBS1_Preview() {
    val navController = rememberNavController()
    OnBoarding1(navController)
}