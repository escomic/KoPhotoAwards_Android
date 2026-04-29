package com.devsimtaku.kophoto.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devsimtaku.kophoto.core.designsystem.KPIcon
import com.devsimtaku.kophoto.core.designsystem.theme.KoPhotoTheme

@Composable
fun KPPhotoItem(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier,
    photographer: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                spotColor = MaterialTheme.colorScheme.primaryContainer,
                ambientColor = MaterialTheme.colorScheme.primaryContainer
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            KPAsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                photographer?.let {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = KPIcon.PersonFlat,
                            contentDescription = "photographer",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        KPChip(
                            text = it,
                            style = KPChipStyle.Small
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KPPhotoItemPreview() {
    KoPhotoTheme {
        KPPhotoItem(
            imageUrl = "",
            title = "경복궁의 가을",
            photographer = "홍길동",
            modifier = Modifier.padding(16.dp)
        )
    }
}
