package com.clementcorporation.wavehealthnews.presentation.news_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.clementcorporation.wavehealthnews.R
import com.clementcorporation.wavehealthnews.data.dtos.Article
import com.clementcorporation.wavehealthnews.util.Constants.CONTENT_DELIMITER
import com.clementcorporation.wavehealthnews.util.Constants.HYPERLINK_TAG
import com.clementcorporation.wavehealthnews.util.WaveNewsToolBar

@Composable
fun NewsDetailsScreen(article: Article?, navController: NavHostController) {
    BackHandler {
        navController.popBackStack()
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        article?.let { story ->
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                WaveNewsToolBar(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape),
                    navController = navController,
                    showBackButton = true
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = story.source.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.news_details_screen_label_story_by),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = story.author,
                        style = MaterialTheme.typography.titleMedium,
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 8.dp,
                    tonalElevation = 8.dp
                ) {
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(250.dp),
                        painter = if (story.urlToImage.isEmpty()) {
                            painterResource(id = R.drawable.wave_health_logo)
                        } else {
                            rememberImagePainter(data = story.urlToImage, builder = {
                                crossfade(false)
                                placeholder(R.drawable.wave_health_logo)
                            })
                        },
                        contentDescription = "New's Story Image",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = story.content.substring(0, story.content.lastIndexOf(CONTENT_DELIMITER)))
                Text(
                    text = stringResource(id = R.string.news_details_screen_label_read_more),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                val uri = story.url
                val sourceHyperlink = buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Blue)) {
                        append(uri)
                        addStringAnnotation(
                            tag = HYPERLINK_TAG,
                            annotation = uri,
                            start = length - uri.length,
                            end = length
                        )
                    }
                }
                Text(text = sourceHyperlink)
            }
        }
    }
}