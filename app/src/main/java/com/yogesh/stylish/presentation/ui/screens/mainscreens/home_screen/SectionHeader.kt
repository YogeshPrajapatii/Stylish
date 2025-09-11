package com.yogesh.stylish.presentation.ui.screens.mainscreens.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yogesh.stylish.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionHeader(
    title: String,
    onViewAllClicked: () -> Unit,
    modifier: Modifier = Modifier, // <-- यहाँ modifier को बाहर से आने दें
    subtitle: String? = null,
    icon: ImageVector? = null,
    containerColor: Color,
    contentColor: Color
) {
    Card(
        modifier = modifier // <-- यहाँ बाहर से आया modifier apply hoga
            .fillMaxWidth()
            .padding(vertical = 8.dp), // <-- FIX: horizontal padding को यहाँ से हटाया
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp), // <-- FIX: Row के content को inner padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
                if (subtitle != null && icon != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = contentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = contentColor.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = onViewAllClicked,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, contentColor.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = contentColor,
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.height(36.dp),
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text(text = "View all", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = "View All",
                    modifier = Modifier.size(18.dp),
                    tint = contentColor
                )
            }
        }
    }
}