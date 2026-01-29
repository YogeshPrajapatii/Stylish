package com.yogesh.stylish.data.repositoryimp.profile

import com.yogesh.stylish.data.local.dao.ProfileDao
import com.yogesh.stylish.data.local.entity.ProfileEntity
import com.yogesh.stylish.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {
    override fun getProfile(): Flow<ProfileEntity?> = profileDao.getProfile()

    override suspend fun saveProfile(profile: ProfileEntity) {
        profileDao.updateProfile(profile)
    }
}