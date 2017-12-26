package com.jzli.netty.demo.chapter14_4.codec;

import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/12/11
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public final class MarshallingCodecFactory {

    /**
     * 创建Jboss Marshaller
     * 
     * @return
     * @throws IOException
     */
    protected static Marshaller buildMarshalling() throws IOException {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	Marshaller marshaller = marshallerFactory
		.createMarshaller(configuration);
	return marshaller;
    }

    /**
     * 创建Jboss Unmarshaller
     * 
     * @return
     * @throws IOException
     */
    protected static Unmarshaller buildUnMarshalling() throws IOException {
	final MarshallerFactory marshallerFactory = Marshalling
		.getProvidedMarshallerFactory("serial");
	final MarshallingConfiguration configuration = new MarshallingConfiguration();
	configuration.setVersion(5);
	final Unmarshaller unmarshaller = marshallerFactory
		.createUnmarshaller(configuration);
	return unmarshaller;
    }
}
