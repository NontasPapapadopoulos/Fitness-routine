package com.example.fitness_routine.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4



@Composable
fun ContentWithDivider(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = contentSpacing4,
        vertical = contentSpacing4
    ),
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val layoutDirection = LocalLayoutDirection.current
        Box(
            modifier = Modifier.padding(
                start = padding.calculateStartPadding(layoutDirection),
                end = padding.calculateEndPadding(layoutDirection),
                top = padding.calculateTopPadding()
            )
        ) {
            content()
        }
        Spacer(modifier = Modifier.size(padding.calculateBottomPadding()))
        Divider()
    }
}

@Composable
fun ContentWithDivider(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = contentSpacing4,
    verticalPadding: Dp = contentSpacing4,
    content: @Composable () -> Unit
) {
    ContentWithDivider(
        modifier = modifier,
        padding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding,
        ),
        content = content
    )
}

@Composable
fun ContentWithDividers(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = contentSpacing4,
        vertical = contentSpacing4
    ),
    dividerPadding: PaddingValues = PaddingValues(0.dp),
    showTopDivider: Boolean = false,
    showBottomDivider: Boolean = false,
    topDividerThickness: Dp? = null,
    bottomDividerThickness: Dp? = null,
    topDividerColor: Color? = null,
    bottomDividerColor: Color? = null,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (showTopDivider) {
            ContentDivider(
                modifier = Modifier.padding(dividerPadding),
                thickness = topDividerThickness,
                color = topDividerColor
            )
        }
        val layoutDirection = LocalLayoutDirection.current
        Box(
            modifier = Modifier.padding(
                start = padding.calculateStartPadding(layoutDirection),
                end = padding.calculateEndPadding(layoutDirection),
                top = padding.calculateTopPadding()
            )
        ) {
            content()
        }
        Spacer(modifier = Modifier.size(padding.calculateBottomPadding()))
        if (showBottomDivider) {
            ContentDivider(
                Modifier.padding(dividerPadding),
                thickness = bottomDividerThickness,
                color = bottomDividerColor
            )
        }
    }
}

@Composable
fun ContentDivider(
    modifier: Modifier = Modifier,
    color: Color? = null,
    thickness: Dp? = null
) {
    when {
        color != null && thickness != null -> Divider(color = color, thickness = thickness)
        color != null -> Divider(modifier = modifier, color = color)
        thickness != null -> Divider(modifier = modifier, thickness = thickness)
        else -> Divider(modifier = modifier)
    }
}

@Preview
@Composable
fun ContentWithDividerPreview() {
    AppTheme {
        Column {
            ContentWithDivider(modifier = Modifier.clickable {}) {
                Text(text = "Hello world!")
            }
            ContentWithDivider {
                Text(text = "Hello world!")
            }
        }
    }
}

@Preview
@Composable
fun ContentWithDividersPreview() {
    AppTheme {
        Column {
            ContentWithDividers(
                modifier = Modifier.clickable {},
                showTopDivider = true,
                topDividerColor = Color.Yellow,
                topDividerThickness = 3.dp
            ) {
                Text(text = "Hello world!")
            }
            ContentWithDividers(
                showTopDivider = true,
                showBottomDivider = true,
                bottomDividerColor = Color.Red,
                topDividerThickness = 2.dp
            ) {
                Text(text = "Hello world!")
            }
        }
    }
}