package com.firhanalisofi.ducatimototype.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.firhanalisofi.ducatimototype.R
import com.firhanalisofi.ducatimototype.ui.theme.DucatiMotoTheme

@Composable
fun CartItem(
    typeId: Long,
    photoUrl: String,
    title: String,
    totalPoint: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = stringResource(R.string.skin_picture),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_price,
                    totalPoint
                ),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleSmall,
            )
        }
        ProductCounter(
            orderId = typeId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(typeId, count + 1) },
            onProductDecreased = { onProductCountChanged(typeId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    DucatiMotoTheme {
        CartItem(
            4, "", "Prime Bundle", 7100, 0,
            onProductCountChanged = { _, _ -> },
        )
    }
}