package ar.com.oxen.nibiru.license.cli;

import ar.com.oxen.commons.converter.impl.DigestConverter;
import ar.com.oxen.commons.license.api.HardwareIdProvider;
import ar.com.oxen.commons.license.api.KeyStoreProvider;
import ar.com.oxen.commons.license.api.LicenseAuthorizer;
import ar.com.oxen.commons.license.api.LicenseSerializer;
import ar.com.oxen.commons.license.api.PrivateKeyProvider;
import ar.com.oxen.commons.license.api.SignatureProvider;
import ar.com.oxen.commons.license.impl.ConverterHardwareIdProvider;
import ar.com.oxen.commons.license.impl.DefaultKeyStoreProvider;
import ar.com.oxen.commons.license.impl.DefaultLicenseAuthorizerProvider;
import ar.com.oxen.commons.license.impl.DefaultLicenseInfo;
import ar.com.oxen.commons.license.impl.DefaultLicenseSerializerProvider;
import ar.com.oxen.commons.license.impl.DefaultSignatureProvider;
import ar.com.oxen.commons.license.impl.KeyStorePrivateKeyProvider;
import ar.com.oxen.commons.license.impl.MacAddressHardwareIdProvider;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class LicenseModule extends AbstractModule {
	private String key;
	private String password;
	private String keystorePassword;

	public LicenseModule(String key, String password, String keystorePassword) {
		super();
		this.key = key;
		this.password = password;
		this.keystorePassword = keystorePassword;
	}

	@Override
	protected void configure() {
        /* Hardware identification configuration - SHA-512 over MAC address in this case */
        bind(HardwareIdProvider.class).toInstance(
                        new ConverterHardwareIdProvider(
                                        new MacAddressHardwareIdProvider(),
                                        new DigestConverter("SHA-512")));
        
        /* Digital signature configuration */
        bind(SignatureProvider.class).toInstance(
                        new DefaultSignatureProvider("DSA"));
        
        /* Public an private key configuration */
        KeyStoreProvider ksp = new DefaultKeyStoreProvider(this.keystorePassword);
        bind(PrivateKeyProvider.class).toInstance(
                        new KeyStorePrivateKeyProvider(this.key, this.password, ksp));
        
        /* Serialization configuration */
        bind(new TypeLiteral<LicenseSerializer<DefaultLicenseInfo>>() {})
                .toProvider(new TypeLiteral<DefaultLicenseSerializerProvider<DefaultLicenseInfo>>() {});

        /* Authorization configuration */
        bind(new TypeLiteral<LicenseAuthorizer<DefaultLicenseInfo>>() {})
                .toProvider(new TypeLiteral<DefaultLicenseAuthorizerProvider<DefaultLicenseInfo>>() {});
	}

}