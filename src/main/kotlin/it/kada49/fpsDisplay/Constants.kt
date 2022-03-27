package it.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class Constants {
    object Data{
        /**
         * The mod name.
        */
        const val NAME = "FPS Display"

        /**
         * The mod version.
         */
        const val VERSION = "1.0.1"

        /**
         * The mod id.
         */
        const val ID = "fps"

        /**
         * The prefix for all chat messages coming from this mod.
         */
        const val PREFIX = "§5[§eFPS§5]"

        /**
         * The Minecraft version.
         */
        val MC_VERSION: String = "1.8.9"

        val LOGGER: Logger = LogManager.getLogger(ID)
    }

}
