package com.example.gitapp.domain.usecases

import com.example.gitapp.data.mapper.toRepo
import com.example.gitapp.domain.models.Repo
import com.example.gitapp.domain.repository.UserRepository
import com.example.gitapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserRepos @Inject constructor(
    private val repository:UserRepository
) {
    operator fun invoke(username:String):Flow<Resource<List<Repo>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getUserRepos(username).map { it.toRepo() }
            emit(Resource.Success(data))
        }  catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        }catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "one error"))
        }
    }

}