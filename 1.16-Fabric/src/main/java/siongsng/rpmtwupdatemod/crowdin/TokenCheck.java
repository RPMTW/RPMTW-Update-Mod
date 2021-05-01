package siongsng.rpmtwupdatemod.crowdin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import siongsng.rpmtwupdatemod.function.SendMsg;

import java.io.IOException;

public class TokenCheck {
    public static Boolean isCheck = false;

    public void Check(String token) throws IOException {
        HttpClient client = HttpClients.custom().build();
        HttpUriRequest request = RequestBuilder.get()
                .setUri("https://api.crowdin.com/api/v2/projects/442446")
                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .setHeader("Authorization", "Bearer " + token)
                .build();
        HttpResponse response = client.execute(request);
        PlayerEntity p = MinecraftClient.getInstance().player;
        assert p != null;
        if (response.getStatusLine().getStatusCode() == 200) {
            SendMsg.send("§a檢測成功，您的Token(登入權杖)是有效的。");
            isCheck = true;
        } else {
            SendMsg.send("§c檢測失敗，Token(登入權杖)無效，請再嘗試新增或至RPMTW官方Discord群組尋求協助。\n官方Discord群組:https://discord.gg/5xApZtgV2u");
            isCheck = false;
        }
    }
}
