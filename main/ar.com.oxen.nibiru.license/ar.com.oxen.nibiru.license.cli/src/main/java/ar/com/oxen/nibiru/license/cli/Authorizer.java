package ar.com.oxen.nibiru.license.cli;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.inject.Inject;

import ar.com.oxen.commons.license.api.HardwareIdProvider;
import ar.com.oxen.commons.license.api.LicenseAuthorizer;
import ar.com.oxen.commons.license.api.LicenseSerializer;
import ar.com.oxen.commons.license.impl.DefaultLicenseInfo;

import com.google.inject.Guice;

/**
 * Command-line authorizer
 */
public class Authorizer {
	private final LicenseSerializer<DefaultLicenseInfo> licenseSerializer;
	private final LicenseAuthorizer<DefaultLicenseInfo> licenseAuthorizer;
	private final HardwareIdProvider hardwareIdProvider;

	@Inject
	public Authorizer(LicenseSerializer<DefaultLicenseInfo> licenseSerializer,
			LicenseAuthorizer<DefaultLicenseInfo> licenseAuthorizer,
			HardwareIdProvider hardwareIdProvider) {
		this.licenseSerializer = licenseSerializer;
		this.licenseAuthorizer = licenseAuthorizer;
		this.hardwareIdProvider = hardwareIdProvider;
	}

	public void authorize(Map<String, String> args) {
		String request = ask("License info string", "request", args);

		DefaultLicenseInfo licenseInfo = ".".equals(request) ? new DefaultLicenseInfo(
				null, null, null, null, hardwareIdProvider.getHardwareId())
				: licenseSerializer.deserializeLicenceInfo(request);

		String customerName = ask("Customer name",
				licenseInfo.getCustomerName());

		String module = ask("Module", licenseInfo.getModule());

		Date expirationDate = null;
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		boolean dateOk = false;
		while (!dateOk) {
			try {
				String dateStr = ask(
						"Expiration date",
						licenseInfo.getExpirationDate() != null ? df
								.format(licenseInfo.getExpirationDate()) : "");
				if (dateStr != null && !dateStr.trim().equals("")) {
					expirationDate = df.parse(dateStr);
				}
				dateOk = true;
			} catch (ParseException e) {
				dateOk = false;
			}
		}

		String code = ask("Code", licenseInfo.getCode());

		DefaultLicenseInfo newLicenseInfo = new DefaultLicenseInfo(
				customerName, module, expirationDate, code,
				licenseInfo.getHardwareId());
		System.out.println("Generated license info: " + newLicenseInfo);
		System.out.println("Serialization: "
				+ licenseSerializer.serializeLicenceInfo(newLicenseInfo));

		String license = this.licenseSerializer
				.serializeLicence(this.licenseAuthorizer
						.authorize(newLicenseInfo));
		System.out.println("Authorized license stirng: " + license);
	}

	public static void main(String[] args) {
		if (args.length == 1 && args[0].equals("-?")) {
			System.out
					.println("Format: authorize [alias=key alias] [password=key passowrd] [keystorePassword=keystore password] [request=license info request string]}");
			System.exit(0);
		}

		Map<String, String> argMap = parseArguments(args);

		String alias = ask("Key alias", "alias", argMap);
		String password = ask("Key password", "password", argMap);
		String keystorePassword = ask("Keystore password", "keystorePassword",
				argMap);

		Guice.createInjector(
				new LicenseModule(alias, password, keystorePassword))
				.getInstance(Authorizer.class).authorize(argMap);
	}

	private static String ask(String description, String defaultValue) {
		if (defaultValue != null) {
			System.out.println(String.format("%s [%s]: ", description,
					defaultValue));
		} else {
			System.out.println(String.format("%s: ", description));
		}

		Scanner scanner = new Scanner(System.in);
		String readValue;
		do {
			readValue = scanner.nextLine().trim();
		} while (defaultValue == null && readValue.equals(""));

		if (readValue.equals("")) {
			return defaultValue;
		} else {
			return readValue;
		}
	}

	private static String ask(String description, String key,
			Map<String, String> args) {
		return ask(description, args.get(key));
	}

	private static Map<String, String> parseArguments(String[] args) {
		Map<String, String> argMap = new HashMap<String, String>();

		for (String arg : args) {
			String[] split = arg.split("=");
			if (split.length == 2) {
				argMap.put(split[0], split[1]);
			}
		}

		return argMap;
	}

}
