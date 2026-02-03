package com.smtm.pickle.presentation.designsystem.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.components.appbar.model.AppBarAlignment
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.textfield.PickleTextField
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickleAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    color: Color = PickleTheme.colors.base0,
    alignment: AppBarAlignment = AppBarAlignment.Center,
    navigationItem: NavigationItem = NavigationItem.None,
    actions: @Composable RowScope.() -> Unit = {},
    bottomContent: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color)
                .statusBarsPadding()
                .height(Dimensions.appbarHeight)
        ) {
            when (alignment) {
                AppBarAlignment.Start -> {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        NavigationIcon(navigationItem)

                        if (title != null) {
                            Text(text = title, style = PickleTheme.typography.body1Bold)
                        }
                    }
                }

                AppBarAlignment.Center -> {
                    Box(modifier = Modifier.align(Alignment.CenterStart)) {
                        NavigationIcon(navigationItem)
                    }

                    if (title != null) {
                        Box(modifier = Modifier.align(Alignment.Center)) {
                            Text(text = title, style = PickleTheme.typography.body1Bold)
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = actions
            )
        }
        bottomContent?.invoke()
    }
}

@Composable
fun PickleSearchAppBar(
    modifier: Modifier = Modifier,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    hint: String = "",
    onSearch: (() -> Unit)? = null,
    onBackClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PickleTheme.colors.base0)
            .statusBarsPadding()
            .height(Dimensions.appbarHeight)
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(R.drawable.ic_appbar_back_left_align),
                contentDescription = "뒤로가기",
                tint = Color.Unspecified,
            )
        }

        PickleTextField.Search(
            value = searchValue,
            onValueChange = onSearchValueChange,
            hint = hint,
            onSearch = onSearch,
        )
    }
}

@Composable
private fun NavigationIcon(item: NavigationItem) {
    when (item) {
        is NavigationItem.Back -> {
            IconButton(onClick = item.onClick) {
                Icon(
                    painter = painterResource(R.drawable.ic_appbar_back),
                    contentDescription = "뒤로가기",
                    tint = Color.Unspecified,
                    modifier = Modifier
                )
            }
        }

        NavigationItem.PickleLogo -> {
            // TODO: 앱바 로고 삽입
            Text("로고 구현")
        }

        NavigationItem.None -> Unit
    }
}

@Preview
@Composable
private fun PickleAppBarPreview() {
    PickleTheme {
        Column {
            PickleAppBar(
                title = "1",
                navigationItem = NavigationItem.Back {},
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.ic_snackbar_fail),
                        contentDescription = "뒤로가기",
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_snackbar_fail),
                        contentDescription = "뒤로가기",
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            PickleAppBar(
                title = "2026년 2월 19일",
                navigationItem = NavigationItem.Back {},
                alignment = AppBarAlignment.Start,
                actions = {
                    Icon(
                        painter = painterResource(R.drawable.ic_snackbar_fail),
                        contentDescription = "뒤로가기",
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_snackbar_fail),
                        contentDescription = "뒤로가기",
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun PickleSearchAppBarPreview() {
    PickleTheme {
        PickleSearchAppBar(
            searchValue = "",
            onSearchValueChange = {}
        ) { }
    }
}
