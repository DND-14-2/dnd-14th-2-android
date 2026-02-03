package com.smtm.pickle.presentation.designsystem.components.snackbar.model

sealed interface SnackbarAlignment {
    data object Top : SnackbarAlignment
    data object Bottom : SnackbarAlignment
}
