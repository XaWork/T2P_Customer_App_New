package me.taste2plate.app.customer.presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.simpleAnimation(
    pagerState: PagerState,
    page: Int
): Modifier {
    return graphicsLayer {
        val pageOffset: Float =
            (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
        lerp(
            start = ScaleFactor(0.05F, 0.05F),
            stop = ScaleFactor(1F, 1F),
            fraction = pageOffset.coerceIn(0f, 1f)
        )
    }
}

/*
.graphicsLayer {
    val pageOffset: Float =
        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
    translationX = pageOffset * size.width
    alpha = 1 - pageOffset.absoluteValue
}*/
/*
graphicsLayer {
    val pageOffset: Float =
        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction.absoluteValue
    translationX = pageOffset * size.width
    lerp(
        start = ScaleFactor(0.5f, 0.5f),
        stop = ScaleFactor(1f, 1f),
        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
    ).also {
        scaleX = scaleX
        scaleY = scaleY
    }
    cameraDistance = 8 * density

}*/
