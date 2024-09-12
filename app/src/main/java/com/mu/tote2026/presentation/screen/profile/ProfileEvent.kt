package com.mu.tote2026.presentation.screen.profile

sealed class ProfileEvent {
    data class OnNicknameChange(val nickname: String) : ProfileEvent()
    //data class OnPhotoChange(val uri: Uri) : ProfileEvent()
    data class OnPhotoChange(val uri: ByteArray) : ProfileEvent()
    data class OnGenderChange(val gender: String) : ProfileEvent()
    data object OnSave : ProfileEvent()
    data object OnCancel : ProfileEvent()
}
