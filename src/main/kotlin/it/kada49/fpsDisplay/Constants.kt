package it.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


object Constants {

    const val NAME = "FPS-Display"
    const val ID = "fps"
    const val VERSION = "1.3.0"

    const val PREFIX = "§5[§eFPS§5]"

    val LOGGER: Logger = LogManager.getLogger(ID)

}
