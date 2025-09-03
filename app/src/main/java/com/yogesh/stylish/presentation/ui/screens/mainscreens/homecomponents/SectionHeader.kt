package com.yogesh.stylish.presentation.ui.screens.mainscreens.homecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    modifier: Modifier = Modifier,
    subtitle: String? = null, 
    icon: ImageVector? = null, 
    containerColor: Color, 
    contentColor: Color 
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
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
                        tint = contentColor.copy(alpha = 0.8f)
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
            shape = RoundedCornerShape(25),
            border = BorderStroke(1.dp, contentColor.copy(alpha = 0.5f))
        ) {
            Text(text = "View all", color = contentColor)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "View All",
                modifier = Modifier.size(18.dp),
                tint = contentColor
            )
        }
    }
}