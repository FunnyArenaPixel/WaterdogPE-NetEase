/*
 * Copyright 2025 WaterdogTEAM
 * Licensed under the GNU General Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.waterdog.waterdogpe.network.protocol.netease;

import dev.waterdog.waterdogpe.network.protocol.netease.handlers.NetEaseAuthInputPacketHandler;
import dev.waterdog.waterdogpe.network.protocol.netease.handlers.NetEaseTextPacketHandler;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v686.Bedrock_v686;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

public class Bedrock_NetEase extends Bedrock_v686 {

    public static final BedrockCodec CODEC = Bedrock_v686.CODEC.toBuilder()
            .minecraftVersion(Bedrock_v686.CODEC.getMinecraftVersion())
            .protocolVersion(Bedrock_v686.CODEC.getProtocolVersion())
            .updateSerializer(TextPacket.class, NetEaseTextPacketHandler.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, NetEaseAuthInputPacketHandler.INSTANCE)
            .build();

}
