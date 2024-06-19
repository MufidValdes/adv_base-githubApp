package com.example.submission3.utils.settings

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ThemeSettingsViewModel(private val pref: SettingsPreferences): ViewModel() {
	fun getThemeSettings(): LiveData<Boolean> {
		return pref.getThemeSetting().asLiveData()
	}

	fun saveThemeSetting(isLightModeActive: Boolean) {
		viewModelScope.launch {
			pref.saveThemeSetting(isLightModeActive)
		}
	}
}
class SettingsViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(ThemeSettingsViewModel::class.java)) {
			return ThemeSettingsViewModel(pref) as T
		}
		throw IllegalArgumentException("Uknown ViewModel class:" + modelClass.name)
	}
}