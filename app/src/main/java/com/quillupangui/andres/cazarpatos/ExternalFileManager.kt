package com.quillupangui.andres.cazarpatos

import android.app.Activity
import java.io.File

class ExternalFileManager(val actividad: Activity) : FileHandler {
    private val fileName = "login_data.txt"

    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        val data = "${datosAGrabar.first}\n${datosAGrabar.second}"
        val file = File(actividad.getExternalFilesDir(null), fileName)
        file.writeText(data)
    }

    override fun ReadInformation(): Pair<String, String> {
        return try {
            val file = File(actividad.getExternalFilesDir(null), fileName)
            if (!file.exists()) return "" to ""
            val lines = file.readLines()
            val email = lines.getOrNull(0) ?: ""
            val clave = lines.getOrNull(1) ?: ""
            email to clave
        } catch (e: Exception) {
            "" to ""
        }
    }
}