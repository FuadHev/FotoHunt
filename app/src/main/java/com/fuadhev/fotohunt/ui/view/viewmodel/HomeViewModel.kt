package com.fuadhev.fotohunt.ui.view.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fuadhev.fotohunt.common.Resource
import com.fuadhev.fotohunt.model.FotoResponse
import com.fuadhev.fotohunt.model.Hit
import com.fuadhev.fotohunt.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val repo: Repository) : ViewModel() {

    val fotosMutableLiveData = MutableLiveData<Resource<List<Hit>>>()


    fun searchFotos(image_name: String, image_type: String,page:Int=1) {
        viewModelScope.launch(IO) {

            fotosMutableLiveData.postValue(Resource.Loading())

            try {
                val response=repo.searchFotos(image_name,image_type,page)
                if (response.isSuccessful&&response.code()==200){
                    val body=response.body()
                    fotosMutableLiveData.postValue(Resource.Success(body?.hits?:emptyList()))
                    return@launch

                }

            }catch (e:java.lang.Exception){
                fotosMutableLiveData.postValue(Resource.Error(e.localizedMessage!!.toString()))
                return@launch
            }

            fotosMutableLiveData.postValue(Resource.Error("Problem occurred during api request!"))

        }


    }

}