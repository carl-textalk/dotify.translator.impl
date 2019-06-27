package org.daisy.dotify.translator.impl.sv_SE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import org.daisy.dotify.api.hyphenator.HyphenatorFactoryMaker;
import org.daisy.dotify.api.hyphenator.HyphenatorFactoryMakerService;
import org.daisy.dotify.api.translator.BrailleFilterFactory;
import org.daisy.dotify.api.translator.BrailleFilterFactoryService;
import org.daisy.dotify.api.translator.TranslatorMode;
import org.daisy.dotify.api.translator.TranslatorSpecification;
import org.daisy.dotify.api.translator.TranslatorType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * Provides a Swedish braille filter factory service.
 * @author Joel Håkansson
 *
 */
@Component
public class SwedishBrailleFilterFactoryService implements
		BrailleFilterFactoryService {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(SwedishBrailleFilterFactoryService.class.getPackage().getName() + ".messages", Locale.getDefault());
	private HyphenatorFactoryMakerService hyphenator = null;
	private final ArrayList<TranslatorSpecification> specs;

	/**
	 * Creates a new Swedish braille filter factory service.
	 */
	public SwedishBrailleFilterFactoryService() {
		this.specs = new ArrayList<>();
		String displayName = RESOURCE_BUNDLE.getString("uncontracted-6-dot");
		String desc = RESOURCE_BUNDLE.getString("uncontracted-description");
		String contractedDisplayName = RESOURCE_BUNDLE.getString("contracted-6-dot");
		String contractedDesc = RESOURCE_BUNDLE.getString("contracted-description");
		specs.add(new TranslatorSpecification("sv", TranslatorMode.Builder
				.withType(TranslatorType.UNCONTRACTED)
				.displayName(displayName)
				.description(desc)
				.build()));
		specs.add(new TranslatorSpecification("sv-SE", TranslatorMode.Builder
				.withType(TranslatorType.UNCONTRACTED)
				.displayName(displayName)
				.description(desc)
				.build()));
		specs.add(new TranslatorSpecification("sv", TranslatorMode.Builder
				.withType(TranslatorType.CONTRACTED)
				.displayName(contractedDisplayName)
				.description(contractedDesc)
				.build()));
		specs.add(new TranslatorSpecification("sv-SE", TranslatorMode.Builder
				.withType(TranslatorType.CONTRACTED)
				.displayName(contractedDisplayName)
				.description(contractedDesc)
				.build()));
    }

    @Override
    public boolean supportsSpecification(String locale, String mode) {
        if (("sv".equalsIgnoreCase(locale) || "sv-SE".equalsIgnoreCase(locale)) && mode.equals(TranslatorType.UNCONTRACTED.toString())) {
            return true;
        } else if (("sv".equalsIgnoreCase(locale) || "sv-SE".equalsIgnoreCase(locale)) && mode.equals(TranslatorType.CONTRACTED.toString())) {
            return true;
        } else {
            return false;
        }
	}

	@Override
	public BrailleFilterFactory newFactory() {
		return new SwedishBrailleFilterFactory(hyphenator);
	}

	/**
	 * Sets the hyphenator factory maker service.
	 * @param hyphenator the hyphenator factory maker service.
	 */
	@Reference(cardinality=ReferenceCardinality.MANDATORY)
	public void setHyphenator(HyphenatorFactoryMakerService hyphenator) {
		this.hyphenator = hyphenator;
	}

	/**
	 * Unsets the hyphenator factory maker service.
	 * @param hyphenator the instance to unset.
	 */
	public void unsetHyphenator(HyphenatorFactoryMakerService hyphenator) {
		this.hyphenator = null;
	}

	@Override
	public Collection<TranslatorSpecification> listSpecifications() {
		return specs;
	}

	@Override
	public void setCreatedWithSPI() {
		setHyphenator(HyphenatorFactoryMaker.newInstance());
	}

}
