package com.smtm.pickle.data.repository

import com.smtm.pickle.data.source.remote.api.UserApi
import com.smtm.pickle.data.source.remote.model.user.NicknameRequest
import com.smtm.pickle.domain.repository.NicknameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NicknameRepositoryImpl @Inject constructor(
    private val userApi: UserApi
) : NicknameRepository {

    private val _nicknameFlow = MutableStateFlow("닉네임")

    override suspend fun saveNickname(nickname: String) {
        userApi.changeNickname(NicknameRequest(nickname))
        _nicknameFlow.value = nickname
    }

    // TODO: 닉네임 조회 API 완료 후 연결
    override suspend fun getNickname(): String = _nicknameFlow.value

    override fun observeNickname(): Flow<String> = _nicknameFlow.asStateFlow()
}
