package netty.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SimpleChatClient {
	public static String length2Str(int length) throws UnsupportedEncodingException {
		byte[] bytes = new byte[2];
		bytes[0] = (byte)((length>>8)&0xff);
		bytes[1] = (byte)(length&0xff);
		return new String(bytes,"UTF-8");
	}
	
	public static void main(String[] args) throws Exception {
		new SimpleChatClient("localhost", 8080).run();
		/*int packBodyLength = 0x000c;
		int packBodyLength2 = 0x0000000c;
		byte[] bytes = new byte[2];
		
		System.out.println("int max:"+Integer.MAX_VALUE);
		
		bytes[0] = (byte)((packBodyLength>>8)&0xff);
		bytes[1] = (byte)(packBodyLength&0xff);
		System.out.println(new String(bytes));
//		System.out.println(packBodyLength&0xff);
//		System.out.println((packBodyLength>>8)&0xff);
		
		byte[] bs = p.getBytes();
		for(int i=0; i<bs.length; i++) {
			System.out.println(bs[i]);
		}
		String str = "a";
		System.out.println(str.getBytes().length);*/
	}

	private final String host;
	private final int port;

	public SimpleChatClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(group)
					.channel(NioSocketChannel.class)
					.handler(new SimpleChatClientInitializer());
			Channel channel = bootstrap.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			while (true) {
				String packStr = "HELLO, WORLD";
				String packContent = length2Str(packStr.length()) + packStr;				
				channel.writeAndFlush(packContent+ in.readLine() + "\r\n");
//				channel.writeAndFlush(packStr.length() + packStr + in.readLine());
//				channel.write(packStr.length());
//				channel.writeAndFlush(packStr + in.readLine() + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}

	}
}
