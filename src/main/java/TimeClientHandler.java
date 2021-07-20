import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private ByteBuf buf;

    @Override
    public void handlerAdded (ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved (ChannelHandlerContext ctx) {
        buf.release();
        buf = null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        f.addListener(ChannelFutureListener.CLOSE);

    }


//    @Override
//    public void channelActive(final ChannelHandlerContext ctx) {
//        final ByteBuf time = ctx.alloc().buffer(4);
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        });
//    }

    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) {
//        ByteBuf m = (ByteBuf) msg;
//        buf.writeBytes(m);
//        m.release();
//
//        if(buf.readableBytes() >= 4) {
//            long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
//            System.out.println(new Date (currentTimeMillis));
//            ctx.close();
//        }
        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();

    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
