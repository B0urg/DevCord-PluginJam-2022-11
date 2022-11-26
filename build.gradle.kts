import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.3.11"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "dev.bourg.treasureHunt"
version = "1.0.0"
description = "Test plugin for paperweight-userdev"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()


        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

}

// Configure plugin.yml generation
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "dev.bourg.treasurehunt.TreasureHunt"
    apiVersion = "1.19"
    authors = listOf("Author")
    commands {
        register("game") {

        }
    }
}