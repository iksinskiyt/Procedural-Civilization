import core.WindowManager;
import org.lwjgl.Version;

public class LWJGLMain {
    private static WindowManager window;
    public static void main(String[] args){
        System.out.println(Version.getVersion());
        window = new WindowManager("Procedural Civilization",1000,1000,false);
        window.init();

        while(!window.windowShouldClose()){
            window.update();
        }

        window.cleanup();
    }

    public static WindowManager getWindow(){
        return window;
    }
}
