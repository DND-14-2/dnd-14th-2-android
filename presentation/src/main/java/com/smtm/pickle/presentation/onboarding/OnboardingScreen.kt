package com.smtm.pickle.presentation.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.common.utils.BackPressFinishHandler
import com.smtm.pickle.presentation.designsystem.components.appbar.PickleAppBar
import com.smtm.pickle.presentation.designsystem.components.appbar.model.NavigationItem
import com.smtm.pickle.presentation.designsystem.components.snackbar.SnackbarHost
import com.smtm.pickle.presentation.designsystem.components.snackbar.model.SnackbarState
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.navigation.navigator.AuthNavigator
import com.smtm.pickle.presentation.onboarding.components.OnboardingBottomButton
import com.smtm.pickle.presentation.onboarding.components.OnboardingPagerSection
import com.smtm.pickle.presentation.onboarding.model.OnboardingItems
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navigator: AuthNavigator,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarState = remember { SnackbarState() }

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is OnboardingEffect.NavigateToLogin -> {
                        navigator.navigateToLogin()
                    }
                }
            }
        }
    }

    BackPressFinishHandler(snackbarState)

    OnboardingContent(
        snackbarState = snackbarState,
        onSkipOrFinish = viewModel::completeOnboarding
    )
}

@Composable
private fun OnboardingContent(
    snackbarState: SnackbarState,
    onSkipOrFinish: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { OnboardingItems.entries.size })
    val scope = rememberCoroutineScope()

    val onPrevPage: () -> Unit = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } }
    val onNextPage: () -> Unit = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } }

    Scaffold(
        topBar = {
            PickleAppBar(
                navigationItem = if (pagerState.currentPage == 0) {
                    NavigationItem.None
                } else {
                    NavigationItem.Back(
                        onClick = onPrevPage
                    )
                },
                actions = {
                    TextButton(onClick = onSkipOrFinish) {
                        Text(
                            text = stringResource(R.string.onboarding_skip),
                            style = PickleTheme.typography.body1Bold,
                            color = PickleTheme.colors.primary500
                        )
                    }
                }
            )
        },
        bottomBar = {
            OnboardingBottomButton(
                currentPage = pagerState.currentPage,
                lastPageIndex = pagerState.pageCount - 1,
                onPrev = onPrevPage,
                onNext = onNextPage,
                onFinish = onSkipOrFinish
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OnboardingPagerSection(
                modifier = Modifier.align(Alignment.Center),
                pagerState = pagerState
            )
        }
    }

    SnackbarHost(snackbarState)
}

@Preview
@Composable
private fun OnboardingPreview() {
    PickleTheme {
        OnboardingContent(
            snackbarState = remember { SnackbarState() },
            onSkipOrFinish = {}
        )
    }
}
