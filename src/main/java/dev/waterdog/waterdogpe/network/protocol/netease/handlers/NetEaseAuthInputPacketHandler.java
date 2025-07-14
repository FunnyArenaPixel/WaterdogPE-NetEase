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
import org.cloudburstmc.protocol.bedrock.codec.v662.serializer.PlayerAuthInputSerializer_v662;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;

/*
 * Catrainbow
 */
public class NetEaseAuthInputPacketHandler extends PlayerAuthInputSerializer_v662 implements BedrockPacketSerializer<PlayerAuthInputPacket> {
    public static NetEaseAuthInputPacketHandler INSTANCE = new NetEaseAuthInputPacketHandler();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        super.serialize(buffer, helper, packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        super.deserialize(buffer, helper, packet);

    }
}
