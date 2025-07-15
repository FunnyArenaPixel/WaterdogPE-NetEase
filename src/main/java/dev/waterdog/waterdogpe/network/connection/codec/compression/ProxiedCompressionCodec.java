/*
 * Copyright 2024 WaterdogTEAM
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

package dev.waterdog.waterdogpe.network.connection.codec.compression;

import dev.waterdog.waterdogpe.network.NetworkMetrics;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.cloudburstmc.protocol.bedrock.PacketDirection;
import org.cloudburstmc.protocol.bedrock.data.CompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockBatchWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.BatchCompression;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionStrategy;

import java.util.List;

public class ProxiedCompressionCodec extends CompressionCodec {
    public ProxiedCompressionCodec(CompressionStrategy strategy, boolean prefixed) {
        super(strategy, prefixed);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BedrockBatchWrapper msg, List<Object> out) {
        ByteBuf compressed = msg.getCompressed().slice();
        BatchCompression compression;
        try {
            CompressionAlgorithm algorithm = this.getCompressionAlgorithm(compressed.readByte());
            compression = this.getStrategy().getCompression(algorithm);
            msg.setAlgorithm(compression.getAlgorithm());
            msg.setUncompressed(compression.decode(ctx, compressed.slice()));
            this.onDecompressed(ctx, msg);
            out.add(msg.retain());
        } catch (Exception e1) {
            try {
                CompressionAlgorithm algorithm = PacketCompressionAlgorithm.ZLIB;
                compression = this.getStrategy().getCompression(algorithm);
                msg.setAlgorithm(compression.getAlgorithm());
                msg.setUncompressed(compression.decode(ctx, compressed.slice()));
                this.onDecompressed(ctx, msg);
                out.add(msg.retain());
            } catch (Exception e2) {
                try {
                    CompressionAlgorithm algorithm = PacketCompressionAlgorithm.SNAPPY;
                    compression = this.getStrategy().getCompression(algorithm);
                    msg.setAlgorithm(compression.getAlgorithm());
                    msg.setUncompressed(compression.decode(ctx, compressed.slice()));
                    this.onDecompressed(ctx, msg);
                    out.add(msg.retain());
                } catch (Exception e3) {
                    try {
                        CompressionAlgorithm algorithm = PacketCompressionAlgorithm.NONE;
                        compression = this.getStrategy().getCompression(algorithm);
                        msg.setAlgorithm(compression.getAlgorithm());
                        msg.setUncompressed(compression.decode(ctx, compressed.slice()));
                        this.onDecompressed(ctx, msg);
                        out.add(msg.retain());
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onPassedThrough(ChannelHandlerContext ctx, BedrockBatchWrapper msg) {
        NetworkMetrics metrics = ctx.channel().attr(NetworkMetrics.ATTRIBUTE).get();
        PacketDirection direction = ctx.channel().attr(PacketDirection.ATTRIBUTE).get();
        if (metrics != null && direction != null) {
            metrics.passedThroughBytes(msg.getCompressed().readableBytes(), direction);
        }
    }

    @Override
    protected void onCompressed(ChannelHandlerContext ctx, BedrockBatchWrapper msg) {
        NetworkMetrics metrics = ctx.channel().attr(NetworkMetrics.ATTRIBUTE).get();
        PacketDirection direction = ctx.channel().attr(PacketDirection.ATTRIBUTE).get();
        if (metrics != null && direction != null) {
            metrics.compressedBytes(msg.getCompressed().readableBytes(), direction);
        }
    }

    @Override
    protected void onDecompressed(ChannelHandlerContext ctx, BedrockBatchWrapper msg) {
        NetworkMetrics metrics = ctx.channel().attr(NetworkMetrics.ATTRIBUTE).get();
        PacketDirection direction = ctx.channel().attr(PacketDirection.ATTRIBUTE).get();
        if (metrics != null && direction != null) {
            metrics.decompressedBytes(msg.getUncompressed().readableBytes(), direction);
        }
    }

    @Override
    protected byte getCompressionHeader0(CompressionAlgorithm algorithm) {
        if (algorithm instanceof CompressionType type && type.getBedrockAlgorithm() == null) {
            return type.getHeaderId();
        }
        return super.getCompressionHeader0(algorithm);
    }

    @Override
    protected CompressionAlgorithm getCompressionAlgorithm0(byte header) {
        return CompressionType.fromHeaderId(header);
    }
}
