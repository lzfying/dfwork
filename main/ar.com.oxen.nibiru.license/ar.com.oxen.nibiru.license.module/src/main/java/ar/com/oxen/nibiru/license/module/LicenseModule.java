package ar.com.oxen.nibiru.license.module;

import ar.com.oxen.commons.converter.impl.DigestConverter;
import ar.com.oxen.commons.license.api.HardwareIdProvider;
import ar.com.oxen.commons.license.api.LicenseSerializer;
import ar.com.oxen.commons.license.impl.ConverterHardwareIdProvider;
import ar.com.oxen.commons.license.impl.DefaultLicenseInfo;
import ar.com.oxen.commons.license.impl.DefaultLicenseSerializerProvider;
import ar.com.oxen.commons.license.impl.MacAddressHardwareIdProvider;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class LicenseModule extends AbstractModule {

	@Override
	protected void configure() {
        /* Hardware identification configuration - SHA-512 over MAC address in this case */
        bind(HardwareIdProvider.class).toInstance(
                        new ConverterHardwareIdProvider(
                                        new MacAddressHardwareIdProvider(),
                                        new DigestConverter("SHA-512")));
        
     
        /* Serialization configuration */
        bind(new TypeLiteral<LicenseSerializer<DefaultLicenseInfo>>() {})
                .toProvider(new TypeLiteral<DefaultLicenseSerializerProvider<DefaultLicenseInfo>>() {});
	}

}