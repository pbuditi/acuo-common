package com.acuo.common.marshal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class MarshallingEventHandler implements ValidationEventHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MarshallingEventHandler.class);

	@Override
	public boolean handleEvent(ValidationEvent event) {
		LOG.warn("{}", event);

		return !(event.getLinkedException() instanceof NumberFormatException);

	}

}