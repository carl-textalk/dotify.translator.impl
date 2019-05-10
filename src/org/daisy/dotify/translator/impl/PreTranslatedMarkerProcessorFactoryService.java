package org.daisy.dotify.translator.impl;

import org.daisy.dotify.api.translator.MarkerProcessorFactory;
import org.daisy.dotify.api.translator.MarkerProcessorFactoryService;
import org.daisy.dotify.api.translator.TranslatorType;
import org.osgi.service.component.annotations.Component;

/**
 * Provides a pre-translated marker processor factory service.
 * @author Joel Håkansson
 * @deprecated without replacement
 */
@Deprecated
@Component
public class PreTranslatedMarkerProcessorFactoryService implements
		MarkerProcessorFactoryService {

	@Override
	public boolean supportsSpecification(String locale, String mode) {
		return mode.equals(TranslatorType.PRE_TRANSLATED.toString());
	}

	@Override
	public MarkerProcessorFactory newFactory() {
		return new PreTranslatedMarkerProcessorFactory();
	}

	@Override
	public void setCreatedWithSPI() {
	}

}
