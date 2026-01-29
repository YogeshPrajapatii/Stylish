package com.yogesh.stylish.domain.usecase.profile

import com.yogesh.stylish.data.local.entity.ProfileEntity
import com.yogesh.stylish.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    operator fun invoke(): Flow<ProfileEntity?> = repository.getProfile()
}