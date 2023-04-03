import ProductDimensions.Abi
import ProductDimensions.Server
import ProductDimensions.Version

object ProductDimensions {
    val list = listOf(Version.name, Abi.name, Server.name)

    object Version {
        const val name = "version"
        const val free = "free"
        const val premium = "premium"
    }

    object Abi {
        const val name = "abi"
        const val arm = "arm"
        const val arm64 = "arm64"
        const val x86 = "x86"
    }

    object Server {
        const val name = "server"
        const val dev = "dev"
        const val staging = "staging"
        const val prod = "prod"
    }
}

class ProductFlavor(
    val name: String,
    val dimension: String,
    val suffix: String? = null
)

object ProductFlavors {
    val variants = listOf(
        ProductFlavor(Version.free, Version.name),
        ProductFlavor(Version.premium, Version.name, suffix = ".premium"),
        ProductFlavor(Abi.arm, Abi.name),
        ProductFlavor(Abi.arm64, Abi.name),
        ProductFlavor(Abi.x86, Abi.name),
        ProductFlavor(Server.dev, Server.name, suffix = ".dev"),
        ProductFlavor(Server.staging, Server.name, suffix = ".staging"),
        ProductFlavor(Server.prod, Server.name, suffix = ".prod")
    )
}