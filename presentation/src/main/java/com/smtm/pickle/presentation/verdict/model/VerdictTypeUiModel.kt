package com.smtm.pickle.presentation.verdict.model

import com.smtm.pickle.domain.model.verdict.VerdictType

enum class VerdictTypeUiModel {
    Pending,
    Guilty,
    NotGuilty
}

fun VerdictType.toUiModel(): VerdictTypeUiModel = when (this) {
    VerdictType.Pending -> VerdictTypeUiModel.Pending
    VerdictType.Guilty -> VerdictTypeUiModel.Guilty
    VerdictType.NotGuilty -> VerdictTypeUiModel.NotGuilty
}

fun VerdictTypeUiModel.toDomain(): VerdictType = when (this) {
    VerdictTypeUiModel.Pending -> VerdictType.Pending
    VerdictTypeUiModel.Guilty -> VerdictType.Guilty
    VerdictTypeUiModel.NotGuilty -> VerdictType.NotGuilty
}
