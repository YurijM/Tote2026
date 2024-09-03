package com.mu.tote2026.presentation.screen.profile

sealed class ProfileEvent {
    data class OnNicknameChange(val nickname: String) : ProfileEvent()
    data class OnPhotoUrlChange(val photoUrl: String) : ProfileEvent()
    data class OnGenderChange(val gender: String) : ProfileEvent()
    data object OnSave : ProfileEvent()
}
