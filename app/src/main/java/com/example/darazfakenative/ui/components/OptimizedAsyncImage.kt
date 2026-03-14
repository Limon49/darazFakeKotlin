package com.example.darazfakenative.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun OptimizedAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: @Composable (() -> Unit)? = null,
    shape: Shape? = null
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context = androidx.compose.ui.platform.LocalContext.current)
            .data(model)
            .crossfade(true)
            .memoryCachePolicy(coil.request.CachePolicy.ENABLED)
            .diskCachePolicy(coil.request.CachePolicy.ENABLED)
            .networkCachePolicy(coil.request.CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        loading = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        error = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                // You can add an error icon here
            }
        },
        contentScale = contentScale,
        modifier = if (shape != null) modifier.clip(shape) else modifier
    )
}

@Composable
fun OptimizedCircularAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context = androidx.compose.ui.platform.LocalContext.current)
            .data(model)
            .crossfade(true)
            .memoryCachePolicy(coil.request.CachePolicy.ENABLED)
            .diskCachePolicy(coil.request.CachePolicy.ENABLED)
            .networkCachePolicy(coil.request.CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        loading = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxSize(0.3f)
                )
            }
        },
        error = {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center
            ) {
                // You can add a default avatar icon here
            }
        },
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
