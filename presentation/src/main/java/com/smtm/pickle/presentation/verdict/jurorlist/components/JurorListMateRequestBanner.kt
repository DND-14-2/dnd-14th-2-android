package com.smtm.pickle.presentation.verdict.jurorlist.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smtm.pickle.presentation.R
import com.smtm.pickle.presentation.designsystem.theme.PickleTheme
import com.smtm.pickle.presentation.designsystem.theme.dimension.Dimensions

@Composable
fun JurorListMateRequestBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(Dimensions.buttonHeightLarge)
            .clip(RoundedCornerShape(Dimensions.radius))
            .border(
                width = 1.dp,
                color = PickleTheme.colors.primary200,
                shape = RoundedCornerShape(Dimensions.radius)
            )
            .background(color = PickleTheme.colors.primary50)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_common_social),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(id = R.string.juror_list_mate_request),
            style = PickleTheme.typography.body1Bold,
            color = PickleTheme.colors.gray700
        )
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = PickleTheme.colors.primary300
        )
    }
}

@Preview
@Composable
fun JurorListMateRequestBannerPreview() {
    PickleTheme {
        JurorListMateRequestBanner(onClick = {})
    }
}
