package com.devsimtaku.kophoto.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devsimtaku.kophoto.core.designsystem.theme.KoPhotoTheme

enum class KPChipStyle {
    Small, Normal
}

@Composable
fun KPChip(
    text: String,
    modifier: Modifier = Modifier,
    style: KPChipStyle = KPChipStyle.Normal,
    onClick: (() -> Unit)? = null
) {
    val backgroundColor = when (style) {
        KPChipStyle.Small -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
        KPChipStyle.Normal -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    
    val textColor = when (style) {
        KPChipStyle.Small -> MaterialTheme.colorScheme.secondary
        KPChipStyle.Normal -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    val textStyle = when (style) {
        KPChipStyle.Small -> MaterialTheme.typography.labelMedium
        KPChipStyle.Normal -> MaterialTheme.typography.bodySmall
    }
    
    val paddingHorizontal = when (style) {
        KPChipStyle.Small -> 12.dp
        KPChipStyle.Normal -> 18.dp
    }
    
    val paddingVertical = when (style) {
        KPChipStyle.Small -> 2.dp
        KPChipStyle.Normal -> 8.dp
    }

    val borderColor = when (style) {
        KPChipStyle.Small -> Color.Transparent
        KPChipStyle.Normal -> MaterialTheme.colorScheme.outlineVariant
    }

    Text(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            )
            .clip(CircleShape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
            .padding(
                horizontal = paddingHorizontal,
                vertical = paddingVertical
            ),
        text = text,
        style = textStyle,
        color = textColor
    )
}

@Preview(showBackground = true)
@Composable
private fun KPChipPreview() {
    KoPhotoTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KPChip(
                text = "Normal Style",
                style = KPChipStyle.Normal,
                onClick = {}
            )
            KPChip(
                text = "Small Style",
                style = KPChipStyle.Small
            )
        }
    }
}
