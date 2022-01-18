package it.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager


class Constants {
    object Data{
        const val NAME = "FPS Display"
        const val VERSION = "0.2.2"
        const val ID = "FPS"
        const val PREFIX = "§5[§eFPS§5]"

        val LOGGER = LogManager.getLogger(ID)
    }

}