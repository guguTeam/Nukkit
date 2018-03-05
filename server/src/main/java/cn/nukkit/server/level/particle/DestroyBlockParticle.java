package cn.nukkit.server.level.particle;

import cn.nukkit.server.block.Block;
import cn.nukkit.server.math.Vector3;

/**
 * Created on 2015/11/21 by xtypr.
 * Package cn.nukkit.server.level.particle in project Nukkit .
 */
public class DestroyBlockParticle extends Particle {

    protected final int data;

    public DestroyBlockParticle(Vector3 pos, Block block) {
        super(pos.x, pos.y, pos.z);
        this.data = block.getId() | (block.getDamage() << 8);
    }

    @Override
    public DataPacket[] encode() {
        LevelEventPacket pk = new LevelEventPacket();
        pk.evid = LevelEventPacket.EVENT_PARTICLE_DESTROY;
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.data = this.data;

        return new DataPacket[]{pk};
    }
}