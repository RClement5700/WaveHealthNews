package com.clementcorporation.wavehealthnews.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.clementcorporation.wavehealthnews.R
import com.clementcorporation.wavehealthnews.domain.NewsListItem

@Composable
fun WaveNewsToolBar(
    modifier: Modifier,
    navController: NavHostController,
    showBackButton: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (showBackButton) Arrangement.Start else Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
            }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            contentScale = ContentScale.Crop,
            modifier = modifier,
            painter = painterResource(id = R.drawable.wave_health_logo),
            contentDescription = "Company Logo"
        )
        if (showBackButton) Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "Wave Health News",
            style = if (showBackButton) MaterialTheme.typography.h5 else
                MaterialTheme.typography.h6,
            color = Color.LightGray
        )
    }
}

@Composable
fun NewsListItemTile(newsListItem: NewsListItem, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = Color.White.copy(0.6f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Image(
                    modifier = Modifier.size(48.dp),
                    painter = if (newsListItem.thumbnail.isNullOrEmpty()) {
                        painterResource(id = R.drawable.wave_health_logo)
                    } else {
                        rememberImagePainter(data = newsListItem.thumbnail, builder = {
                            crossfade(false)
                            placeholder(R.drawable.wave_health_logo)
                        })
                    },
                    contentDescription = "News Story Thumbnail",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = newsListItem.date,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                modifier = Modifier.padding(4.dp),
                text = newsListItem.title,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1
            )
        }
    }
}