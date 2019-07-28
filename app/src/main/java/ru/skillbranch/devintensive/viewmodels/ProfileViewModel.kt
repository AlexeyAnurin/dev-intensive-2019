package ru.skillbranch.devintensive.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class ProfileViewModel : ViewModel() {
    // Что я только что написал?
private val repository: PreferencesRepository = PreferencesRepository
private val profileData = MutableLiveData<Profile>()

    init {
        Log.d("M_ProfileViewModel", "init view model")
        profileData.value = repository.getProfile()
    }

    override fun onCleared(){
        super.onCleared()
        Log.d("M_ProfileViewModel", " view model cleared")
    }
    fun getProfileDate (){}

    fun saveProfileData(){}
}