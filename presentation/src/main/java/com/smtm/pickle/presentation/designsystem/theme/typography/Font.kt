import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.*
import com.smtm.pickle.presentation.R

val Pretendard: FontFamily = pretendardVariableFamily()

@OptIn(ExperimentalTextApi::class)
private fun pretendardVariableFamily() = FontFamily(
    Font(
        resId = R.font.pretendard_variable,
        weight = FontWeight.Normal,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(400)
        )
    ),
    Font(
        resId = R.font.pretendard_variable,
        weight = FontWeight.Medium,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(500)
        )
    ),
    Font(
        resId = R.font.pretendard_variable,
        weight = FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(600)
        )
    ),
    Font(
        resId = R.font.pretendard_variable,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(700)
        )
    )
)
