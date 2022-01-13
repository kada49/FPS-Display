package it.kada49.fpsDisplay

import org.apache.logging.log4j.LogManager


class Constants {
    object Data{
        const val NAME = "FPS Display"
        const val VERSION = "0.2.0"
        const val ID = "FPS"

        val LOGGER = LogManager.getLogger(ID)
    }

    object Colors{
        val STRING = arrayOf("White", "Light Grey", "Grey", "Black", "Yellow", "Orange", "Red", "Brown", "Lime", "Green", "Light Blue", "Cyan", "Blue", "Pink", "Magenta", "Purple")
        val INTEGER = arrayOf(0xFFFFFF, 0x9c9d97, 0x474f52, 0x1d1c21, 0xffd83d, 0xf9801d, 0xb02e26, 0x825432, 0x80c71f, 0x5d7c15, 0x3ab3da, 0x169c9d, 0x3c44a9, 0xf38caa, 0xc64fbd, 0x8932b7)

    }

}