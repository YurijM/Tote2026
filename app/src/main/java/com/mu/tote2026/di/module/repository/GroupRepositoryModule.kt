package com.mu.tote2026.di.module.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.mu.tote2026.data.repository.GroupRepositoryImpl
import com.mu.tote2026.domain.repository.GroupRepository
import com.mu.tote2026.domain.usecase.group_usecase.DeleteGroup
import com.mu.tote2026.domain.usecase.group_usecase.GetGroup
import com.mu.tote2026.domain.usecase.group_usecase.GetGroupList
import com.mu.tote2026.domain.usecase.group_usecase.GroupUseCase
import com.mu.tote2026.domain.usecase.group_usecase.SaveGroup
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GroupRepositoryModule {
    @Provides
    @Singleton
    fun provideGroupRepository(
        firestore: FirebaseFirestore
    ) : GroupRepository = GroupRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideGroupUseCase(groupRepository: GroupRepository) = GroupUseCase(
        getGroupList = GetGroupList(groupRepository),
        getGroup = GetGroup(groupRepository),
        saveGroup = SaveGroup(groupRepository),
        deleteGroup = DeleteGroup(groupRepository)
    )
}