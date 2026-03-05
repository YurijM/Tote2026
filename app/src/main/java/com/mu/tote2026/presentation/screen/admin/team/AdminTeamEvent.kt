package com.mu.tote2026.presentation.screen.admin.team

import android.net.Uri

sealed class AdminTeamEvent {
    data class OnTeamChange(val team: String) : AdminTeamEvent()
    data class OnGroupChange(val group: String) : AdminTeamEvent()
    data class OnFlagChange(val uri: Uri) : AdminTeamEvent()
    data class OnItemNoChange(val itemNo: String) : AdminTeamEvent()
    data object OnSave : AdminTeamEvent()
}
