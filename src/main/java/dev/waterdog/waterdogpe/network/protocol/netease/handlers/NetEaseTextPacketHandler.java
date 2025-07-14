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

package dev.waterdog.waterdogpe.network.protocol.netease.handlers;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v685.serializer.TextSerializer_v685;
import org.cloudburstmc.protocol.bedrock.packet.TextPacket;

/*
 * Catrainbow
 */
public class NetEaseTextPacketHandler extends TextSerializer_v685 implements BedrockPacketSerializer<TextPacket> {
    public static NetEaseTextPacketHandler INSTANCE = new NetEaseTextPacketHandler();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        if (packet.getType() == TextPacket.Type.CHAT || packet.getType() == TextPacket.Type.POPUP) {
            helper.writeString(buffer, "");
        }
        super.serialize(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TextPacket packet) {
        super.deserialize(buffer, helper, packet);
    }
}
