import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

object Main {

  def main(args: Array[String]): Unit = {
    val configuration = new LwjglApplicationConfiguration {
      title = "main"
      width = 1024
      height = 640
      samples = 8
    }
    new LwjglApplication(new Application, configuration)
  }

}
