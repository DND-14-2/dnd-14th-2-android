package com.smtm.pickle.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.onboarding.model.OnboardingItems

@Composable
fun OnboardingPagerContent(
    modifier: Modifier = Modifier,
    currentItem: OnboardingItems
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = currentItem.title),
            style = PickleTheme.typography.body2Medium,
            color = PickleTheme.colors.gray600,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = currentItem.description),
            style = PickleTheme.typography.head3Bold,
            color = PickleTheme.colors.gray800,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = currentItem.image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPagerContentPreview() {
    PickleTheme {
        OnboardingPagerContent(
            currentItem = OnboardingItems.Step1
        )
    }
}
