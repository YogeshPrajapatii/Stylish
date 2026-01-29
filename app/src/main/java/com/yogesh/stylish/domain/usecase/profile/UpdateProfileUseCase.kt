package com.yogesh.stylish.domain.usecase.profile

import com.yogesh.stylish.data.local.entity.ProfileEntity
import com.yogesh.stylish.domain.repository.profile.ProfileRepository
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val repository: ProfileRepository) {
    suspend operator fun invoke(profile: ProfileEntity) = repository.saveProfile(profile)
}