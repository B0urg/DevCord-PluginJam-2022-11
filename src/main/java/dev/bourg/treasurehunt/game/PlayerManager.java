package dev.bourg.treasurehunt.game;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class PlayerManager {

    private final GameManager gameManager;

    public PlayerManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void changeSkins(){
        Bukkit.getOnlinePlayers().forEach(this::changeSkin);
    }

    public void changeSkin(Player player){
        GameProfile profile = ((CraftPlayer)player).getHandle().getGameProfile();
        ServerGamePacketListenerImpl listener = ((CraftPlayer)player).getHandle().connection;

        listener.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));

        profile.getProperties().removeAll("textures");
        profile.getProperties().put("textures", getSkin());

        listener.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, ((CraftPlayer)player).getHandle()));


    }

    private Property getSkin(){
        Random random = new Random();
        int num = random.nextInt(2);
        if (num == 1) {
            return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY2OTQ3Nzc5Njc1NCwKICAicHJvZmlsZUlkIiA6ICIwYzE1OTI3Yjc4OTY0MTA3OTA5MWQyMjkxN2U0NmIyYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJZb3VBcmVTY2FyeSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8xZTBmYTgxMTJiZGNjMDliZTQ1NWJjMDk0NGQzYTQ0OTBiMjg4ZWUxZmFhYWM5YjcwNmJhYjQ1NDVkMmNhOWU1IgogICAgfQogIH0KfQ==", "fiYgjLdX3tXr0OOt2SC6sKH2zn3prPblcIpPEkJCLPl2EFsGm26kEzPQdr4EdZu3lyhCYt2S1PYpgl3BO+OSLbSs9NLN1GMk4G0CKFS3E86bQJvUl4T6yGq64Qey9zLERUnWmOJiABVupTAjkI72rV4CzO65qDclYuHXIsOaO7JfURxGg2XrpQX7pm3Nvx9+0LdsSePad6nDGPPdP0bmI8t2tx2EmMPtmhVwhxc1urjBnPg/VgSImYQhoRBjlFHfTc9ltRYXDRUeQc+TOMb2j1vvGMPhemJUiGAhIRRWe1GQh8I0U/BtFcUwf/xnLfxy8o7+245YD2xG1tMu7QUGfXOjywv3BgbGQN8BiaAA/pRKdsfRTzzaZIQ8pdPuVX5pb1m6GHXYqhcdtm6S+oVJ5maZEj+kocGzk0iENT4HMD0gyh4ND986IcCqjdHgGeikrdsbBzBKznUPJcuu+Sw25ZZu+Q/xW2PeHbNMUvcqS9tWSGzeo2ZN298gBUrEmoNl74zyUCQx548UEazIt8fTonobs3WYhF0fhBntt00xbz7Z3/vSsDc5g3QWQvFqGTePnxQC9NBOQGI2tHZjV0ppc7dVPT4/JiU+lTQGJhWfVMds5YG7SyAtiwUeeToZ3Zfp5cu2y/BY7Ajlw/O27AxL6TJSDy0Lq8sdhSkoTsN2ElM=");
        } else {
            return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY2OTQ5NjE5NDI0MCwKICAicHJvZmlsZUlkIiA6ICJmMjc0YzRkNjI1MDQ0ZTQxOGVmYmYwNmM3NWIyMDIxMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJIeXBpZ3NlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MjlhNmJhZDUyMmJmZDQ0MjliMGQ5NWQ0ZDc3ZDc4YjUyZjA1NzlmYWMxNDEwMDdlN2ZhODFlYmNhOWRjMTExIgogICAgfQogIH0KfQ==", "WpStlvEBnofCUpUUAca6ooU/nVvKUBbqdx+1eKu1b3Z9ITHwzIHJcTD9VIjXt0I2rnFiulMdQNCKeC7fVDvk1ZBr10QSzXUEGO5bHYX3Gf05wPioUGRbVX/sDHVM8C6IIxbigrRFOi4BeALONTpGpB1aVZidEIYjfv+tyGJaNvoeE0x3if2pDfEtZzssJF/cCvR2ZAOZXi3j59jcE695Z0I91mL10GGSzPb4uO7vQUC9qVKRuQbbz/9oga5LYEr1iITyf4t1MUDJhawivf5yHR3ekLwLDMCwLBd8jhi5LNRXZnd5LLpQUW5j79ZnPzfPUlWUFr/K/2NsRDyj+Alg3PbXhcn1qR9n6Rqq6JkbjvEZI9ZSuuDg7recw5HMBmAXG1lH887pRG99kr0mLrEcp/bkJz/p23/mQP5zHl03Olm+dTGAR9WVSTWzumEilLcYOhQBagGiSuBzo8A8LqPq8NLy/QGrNG1pz0fR4bxSbCNs62kx0VcAMINzTIDCQ4GJO1xUQp3WPR0eYxrdqOmzAGX+xqMh890DWT6ldTKSj4gbJvYx3ncNCv3wMBzwRY+Tkrm9cy8/loNI0FuPy5BWsOx+GqOdYQ52pQpDRqz/+qwj3t1iqynswTMqMypRAtoSXWlsB3Q5oC0wxgrcJgsqymkxCIYw0mCSfB5sg2Rd8T4=");
        }
    }


}
