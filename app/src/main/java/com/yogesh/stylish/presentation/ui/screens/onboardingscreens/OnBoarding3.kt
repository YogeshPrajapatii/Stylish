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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yogesh.stylish.R
import com.yogesh.stylish.presentation.navigation.Routes

@Composable
fun OnBoarding3(navController: NavHostController) {

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    Column(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.statusBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Row(Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("3/3")
            Text(text = "Skip", modifier = Modifier.clickable {
                // Yahan bhi ViewModel ka object istemal karein
                onboardingViewModel.onOnboardingFinished()
                navController.navigate(Routes.Login)
            })
        }

        Box(Modifier
            .fillMaxWidth()
            .padding(top = 28.dp), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(R.drawable.img_onboarding_3),
                contentDescription = "OnBoarding Screen Three")
        }
        Column(modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Get Your Order",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp))
            Text("Amet minim mollit non deserunt ullamco est sit aliqua dolor do amet sint. Velit officia consequat duis enim velit mollit.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp))
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Previous",
                modifier = Modifier.clickable { navController.navigate(Routes.OnBoarding2) })

            Text("Get Started", color = Color.Red, modifier = Modifier.clickable {
                // Pehle ViewModel ke object par function call karein
                onboardingViewModel.onOnboardingFinished()
                // Fir aage navigate karein
                navController.navigate(Routes.SignUp) {
                    popUpTo(Routes.OnBoarding3) { inclusive = true }
                }
            })
        }
    }
}