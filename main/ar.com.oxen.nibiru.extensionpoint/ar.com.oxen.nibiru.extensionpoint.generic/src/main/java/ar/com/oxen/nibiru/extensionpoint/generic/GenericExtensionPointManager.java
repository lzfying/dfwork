package ar.com.oxen.nibiru.extensionpoint.generic;

import java.util.Map;

import ar.com.oxen.nibiru.extensionpoint.api.ExtensionPointManager;
import ar.com.oxen.nibiru.extensionpoint.api.ExtensionTracker;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * Generic Extension Point manager implementation.
 * 
 */
public class GenericExtensionPointManager implements ExtensionPointManager {
	private Map<Object, Key> extensionToKey = Maps.newHashMap();
	private Multimap<Key, Object> keyToExtensions = LinkedListMultimap.create();
	private Multimap<Key, ExtensionTracker<Object>> trackers = LinkedListMultimap
			.create();

	@Override
	synchronized public <K> void registerExtension(K extension,
			String extensionPointName, Class<K> extensionPointInterface) {
		Key key = new Key(extensionPointName, extensionPointInterface);
		extensionToKey.put(extension, key);
		keyToExtensions.put(key, extension);
		for (ExtensionTracker<Object> tracker : trackers.get(key)) {
			tracker.onRegister(extension);
		}
	}

	@Override
	synchronized public void unregisterExtension(Object extension) {
		Key key = extensionToKey.remove(extension);
		keyToExtensions.remove(key, extension);

		for (ExtensionTracker<Object> tracker : trackers.get(key)) {
			tracker.onUnregister(extension);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public <T, K extends T> void registerTracker(
			ExtensionTracker<T> tracker, String extensionPointName,
			Class<K> extensionPointInterface) {
		trackers.put(new Key(extensionPointName, extensionPointInterface),
				(ExtensionTracker<Object>) tracker);

		for (Object extension : keyToExtensions.get(new Key(extensionPointName,
				extensionPointInterface))) {
			tracker.onRegister((T) extension);
		}
	}

	private class Key {
		private String extensionPointName;
		private Class<?> extensionPointInterface;

		public Key(String extensionPointName, Class<?> extensionPointInterface) {
			super();
			this.extensionPointName = extensionPointName;
			this.extensionPointInterface = extensionPointInterface;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime
					* result
					+ ((extensionPointInterface == null) ? 0
							: extensionPointInterface.hashCode());
			result = prime
					* result
					+ ((extensionPointName == null) ? 0 : extensionPointName
							.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (extensionPointInterface == null) {
				if (other.extensionPointInterface != null)
					return false;
			} else if (!extensionPointInterface
					.equals(other.extensionPointInterface))
				return false;
			if (extensionPointName == null) {
				if (other.extensionPointName != null)
					return false;
			} else if (!extensionPointName.equals(other.extensionPointName))
				return false;
			return true;
		}

		private GenericExtensionPointManager getOuterType() {
			return GenericExtensionPointManager.this;
		}
	}
}
