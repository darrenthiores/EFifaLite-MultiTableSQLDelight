package com.darrenthiores.efifalite.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import androidx.compose.ui.util.lerp
import com.darrenthiores.efifalite.R
import com.darrenthiores.efifalite.ui.theme.EFifaLiteTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
@ExperimentalPagerApi
fun OverviewCarousel(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val pagerState = rememberPagerState(100/2)

    BaseCarousel(
        modifier = modifier,
        pagerState = pagerState,
        onClick = onClick,
        count = 100,
        image = { index -> getImage(index) }
    )

    LaunchedEffect(true) {
        yield()
        delay(3000)
        tween<Float>(600)
        pagerState.animateScrollToPage(
            page = (pagerState.currentPage + 1) % pagerState.pageCount
        )
    }
}

@Composable
@ExperimentalPagerApi
fun BaseCarousel(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClick: () -> Unit,
    count: Int,
    image: (Int) -> Int,
    customButton: @Composable () -> Unit = {  }
) {
    HorizontalPager(
        count = count,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 32.dp),
        modifier = modifier
            .fillMaxWidth()
            // .height(100.dp)
    ) { pageCount ->
        val page = pageCount % 3
        Card(
            Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(pageCount).absoluteValue

                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(id = image(page)),
                    contentDescription = "Overview Carousel $page",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            onClick()
                        },
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    customButton()
                }
            }
        }
    }
}

@Composable
@Preview
@ExperimentalPagerApi
private fun OverviewCarouselPreview() {
    EFifaLiteTheme {
        OverviewCarousel(
            onClick = {}
        )
    }
}

private fun getImage(
    index: Int
): Int =
    when(index) {
        0 -> R.drawable.overview_carousel_1
        1 -> R.drawable.overview_carousel_2
        2 -> R.drawable.overview_carousel_3
        else -> R.drawable.overview_carousel_1
    }