package dev.bourg.treasurehunt.game;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

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
            return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY2OTQ5MzU4MDE5OSwKICAicHJvZmlsZUlkIiA6ICI3YmRhNDBlM2E1YjU0YzE0YWJmZGYzNGMyODY2NjQ0NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJfRWdvcl9wbGF5XyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mYjljZWE5MmU0ZDY0MzVlYmY4ZDZmNWU3Y2U1MzM2NjUzOTYwYzJhZjY5ODVkMjk1NjNmNDJmNTg1OGNmODE5IgogICAgfQogIH0KfQ==", "AE0JdZTzp9EA1iY6wGhljLhlRF7dIAv6KBzVxsB1nurc/eNe24WBReBvSPyLp5foTruYx3HEHrYwa1smvgHGmIIg5pCzMJzo+gCAMUiYhw8a9TppDwy+uc6IKoAf1RkZc+O8Y5GgigdbFiQ6F3YeCS/QoSxFaa7CqSDOr9KgrL0EQuGxqALd0GwS89hy6OlURnc+3FAHQBjBQ2gCwMsqSULEwjz8BTSXEBC/Wv2VQimXnQVswzgAOrHLIHvvzW0ZVQm1rNVOyNSiylmCxHLHtYoOLIhWWht21qQ76UFw2T48fI6LnpBiDLaSNdvMRtgiQFFOb91aRG3D5ylptaHNTrBpbxqmvWwuPamWfA05ziUtwX3ktg98g3Bht4NNxSRepOa5Q3e3pOLKEobRkEQ7jVJJPwM4GkcrJPglFyliAHgqx3bf4CXj2HbkC6OTLJ8AMfyX95VT8KNxJ0eQYC4CCl1UuFRCI3/5tZyZMsnTwTayW2Ae8DEVE3xIZInwVkzHjcnZ9vsg9xH1750GPqVC6fe8x9PDPkvakpF5MOMukelRiYWsJcdkqlcUS0lwwWmT0TwTZc6EajmX0JiqcPhHxz/6x20tU2RxvlEQmhdle95akCQr8sH9YqYL+nYMKGJUAANDz0GtQcGKK6wQdYFSbCOzPXxkNhDRuhXiLS3DLYQ=");
        }
    }

}
