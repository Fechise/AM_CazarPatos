package com.quillupangui.andres.cazarpatos

import android.app.Activity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.core.content.edit

class EncryptedSharedPreferencesManager(val actividad: Activity) : FileHandler {
    private val masterKey = MasterKey.Builder(actividad)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPref = EncryptedSharedPreferences.create(
        actividad,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        sharedPref.edit {
            putString(LOGIN_KEY, datosAGrabar.first)
                .putString(PASSWORD_KEY, datosAGrabar.second)
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        val email = sharedPref.getString(LOGIN_KEY, "") ?: ""
        val clave = sharedPref.getString(PASSWORD_KEY, "") ?: ""
        return (email to clave)
    }
}