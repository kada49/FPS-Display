package com.kada49.fpsDisplay

import java.awt.Desktop
import java.net.URI

object Utils {

    fun openUrl(url: String) {
        try {
            Desktop.getDesktop().browse( URI(url) )
        } catch (exception: Exception) {}
    }

}