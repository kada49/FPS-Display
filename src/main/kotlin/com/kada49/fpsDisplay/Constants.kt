package com.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager

class Constants {
    object Data{
        const val NAME = "FPS Display"
        const val VERSION = "0.1.0"
        const val ID = "FPS"

        val LOGGER = LogManager.getLogger(ID)
    }
}