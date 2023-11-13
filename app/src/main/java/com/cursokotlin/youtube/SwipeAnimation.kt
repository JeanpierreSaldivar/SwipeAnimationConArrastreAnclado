package com.cursokotlin.youtube

import androidx.annotation.OptIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


enum class TypeDrag {
    Start,
    Center,
    End
}

@kotlin.OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeAnimation(){

    val density = LocalDensity.current

    val screenSizeDp = LocalConfiguration.
                            current.screenWidthDp.dp

    val screenSizePx = with(density){
        screenSizeDp.toPx()
    }

    val anchor = remember {
        DraggableAnchors{
            TypeDrag.Start at 0f
            TypeDrag.Center at screenSizePx/2
            TypeDrag.End at screenSizePx
        }
    }

    val state = remember {
        AnchoredDraggableState(
            initialValue = TypeDrag.Start,
            positionalThreshold = {distance -> distance * 0.3f},
            velocityThreshold = { with(density){100.dp.toPx()} },
            animationSpec = tween()
            )
    }

    SideEffect {
        state.updateAnchors(anchor)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(state, Orientation.Vertical)
            .background(Color.Yellow)
    ){
        Icon(painter = painterResource(id = R.drawable.android),
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = state
                            .requireOffset()
                            .roundToInt()
                    )
                }.size(screenSizeDp),
                contentDescription = "android"
        )
    }
}
