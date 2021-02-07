package example

import arc.*
import arc.util.*
import mindustry.game.EventType.*
import mindustry.mod.*
import mindustry.ui.dialogs.*

class ExampleKotlinMod : Mod(){

    init{
        Log.info("Loaded ExampleKotlinMod constructor.")

        //listen for game load event
        Events.on(ClientLoadEvent::class.java){
            //show dialog upon startup
            Time.runTask(10f) {
                BaseDialog("frog").apply{
                    cont.add("behold").row()
                    //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                    cont.image(Core.atlas.find("example-kotlin-mod-frog")).pad(20f).row()
                    cont.button("I see"){ hide() }.size(100f, 50f)
                    show()
                }
            }
        }
    }

    override fun loadContent(){
        Log.info("Loading some example content.")
    }
}
