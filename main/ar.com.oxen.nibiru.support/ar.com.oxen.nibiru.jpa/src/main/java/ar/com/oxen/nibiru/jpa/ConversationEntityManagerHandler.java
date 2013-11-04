package ar.com.oxen.nibiru.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.persistence.EntityManager;

import ar.com.oxen.nibiru.conversation.api.Conversation;
import ar.com.oxen.nibiru.conversation.api.ConversationAccessor;
import ar.com.oxen.nibiru.conversation.api.ConversationTracker;
import ar.com.oxen.nibiru.transaction.api.TransactionCallback;
import ar.com.oxen.nibiru.transaction.api.TransactionTemplate;

class ConversationEntityManagerHandler implements InvocationHandler {
	private final static String ENTITY_MANAGER_KEY = "ar.com.oxen.nibiru.jpa.EntityManager";
	private ConversationAccessor conversationAccessor;
	private EntityManagerCreator entityManagerCreator;
	private TransactionTemplate transactionTemplate;

	static EntityManager buidlProxy(ConversationAccessor conversationAccessor,
			TransactionTemplate transactionTemplate,
			EntityManagerCreator entityManagerCreator) {
		return (EntityManager) Proxy.newProxyInstance(
				ConversationEntityManagerHandler.class.getClassLoader(),
				new Class<?>[] { ConversationEntityManager.class },
				new ConversationEntityManagerHandler(conversationAccessor,
						transactionTemplate, entityManagerCreator));
	}

	ConversationEntityManagerHandler(ConversationAccessor conversationAccessor,
			TransactionTemplate transactionTemplate,
			EntityManagerCreator entityManagerCreator) {
		super();
		this.conversationAccessor = conversationAccessor;
		this.transactionTemplate = transactionTemplate;
		this.entityManagerCreator = entityManagerCreator;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (method.getName().equals("close")
				|| method.getName().equals("clear")) {
			return null;
		} else {
			return method.invoke(this.getConversationEntityManager(), args);
		}
	}

	private EntityManager getConversationEntityManager() {
		Conversation currentConversation = this.conversationAccessor
				.getCurrentConversation();

		if (currentConversation != null) {
			EntityManager conversationEntityManager = currentConversation
					.get(ENTITY_MANAGER_KEY);
			if (conversationEntityManager == null) {
				synchronized (this) {
					conversationEntityManager = currentConversation
							.get(ENTITY_MANAGER_KEY);
					if (conversationEntityManager == null) {
						conversationEntityManager = this.entityManagerCreator
								.create();
						currentConversation.put(ENTITY_MANAGER_KEY,
								conversationEntityManager);

						currentConversation
								.registerTracker(new EntityManagerConversationTracker(
										conversationEntityManager,
										transactionTemplate));
					}
				}
			}
			return conversationEntityManager;
		} else {
			throw new IllegalStateException("No conversation active");
		}
	}

	private static class EntityManagerConversationTracker implements
			ConversationTracker {
		private EntityManager entityManager;
		private TransactionTemplate transactionTemplate;

		public EntityManagerConversationTracker(EntityManager entityManager,
				TransactionTemplate transactionTemplate) {
			super();
			this.entityManager = entityManager;
			this.transactionTemplate = transactionTemplate;
		}

		@Override
		public void onEnd(Conversation conversation) {
			this.transactionTemplate.execute(this.entityManager,
					new TransactionCallback<Void>() {
						@Override
						public Void doInTransaction() {
							entityManager.flush();
							return null;
						}
					});
			this.entityManager.close();
		}

		@Override
		public void onCancel(Conversation conversation) {
			this.entityManager.close();
		}
	}
}