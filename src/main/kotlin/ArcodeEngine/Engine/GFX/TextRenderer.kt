package ArcodeEngine.Engine.GFX

import ArcodeEngine.Engine.ArcodeEngine
import ArcodeEngine.Engine.Geometry.Text
import ArcodeEngine.Engine.Window

class TextRenderer {
    companion object {
        var fontMapID = ArcodeEngine.RegisterTexture("src/main/kotlin/ArcodeEngine/Engine/res/arcade-font.png")

        @JvmStatic
        fun DrawString(window: Window, text: Text) {
            for(r in text.chars)
                Renderer.DrawTexturedRect(window, r, fontMapID)
        }

        private fun DrawString(window: Window, text: Text, fontID: Int) {
            for(r in text.chars) {
                Renderer.DrawTexturedRect(window, r, fontID)
            }
        }
    }
}