package it.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Constants {
    object Data{
        const val NAME = "FPS Display"
        const val VERSION = "0.4.0"
        const val ID = "FPS"
        const val PREFIX = "§5[§eFPS§5]"

        val LOGGER: Logger = LogManager.getLogger(ID)
    }

}