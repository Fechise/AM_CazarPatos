package com.quillupangui.andres.cazarpatos

import android.app.Activity

class InternalFileManager(val actividad: Activity) : FileHandler {
    private val fileName = "login_data.txt"

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        val data = "${datosAGrabar.first}\n${datosAGrabar.second}"
        actividad.openFileOutput(fileName, Activity.MODE_PRIVATE).use {
            it.write(data.toByteArray())
        }
    }

    override fun ReadInformation(): Pair<String, String> {
        return try {
            val fileInput = actividad.openFileInput(fileName)
            val lines = fileInput.bufferedReader().readLines()
            val email = lines.getOrNull(0) ?: ""
            val clave = lines.getOrNull(1) ?: ""
            email to clave
        } catch (e: Exception) {
            "" to ""
        }
    }
}