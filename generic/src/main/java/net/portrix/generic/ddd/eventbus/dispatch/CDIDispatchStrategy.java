package net.portrix.generic.ddd.eventbus.dispatch;

import net.portrix.generic.ddd.eventbus.Callback;
import net.portrix.generic.ddd.eventbus.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.transaction.*;
import java.util.Set;

/**
 * @author Patrick Bittner on 08.05.2014.
 */
public class CDIDispatchStrategy implements EventDispatchStrategy {

    private static final Logger log = LoggerFactory.getLogger(CDIDispatchStrategy.class);

    private final BeanManager beanManager;
    private final UserTransaction userTransaction;

    public CDIDispatchStrategy(final BeanManager beanManager, UserTransaction userTransaction) {
        this.beanManager = beanManager;
        this.userTransaction = userTransaction;
    }

    @Override
    public <B, E> void execute(final E event, Callback[] callbacks, final EventSubscriber<B, E> subscriber) {
        final Set<Bean<?>> beans = beanManager.getBeans(subscriber.getDeclaringClass());

        try {

            final int status = userTransaction.getStatus();

            if (status == Status.STATUS_NO_TRANSACTION) {
                userTransaction.begin();
                for (final Bean<?> bean : beans) {
                    @SuppressWarnings("unchecked")
                    final B object = (B) createBeanInstance(bean);
                    subscriber.accept(object, event);
                }
                for (Callback callback : callbacks) {
                    callback.execute();
                }
                userTransaction.commit();
            } else {
                for (final Bean<?> bean : beans) {
                    @SuppressWarnings("unchecked")
                    final B object = (B) createBeanInstance(bean);
                    subscriber.accept(object, event);
                }
                for (Callback callback : callbacks) {
                    callback.execute();
                }
            }


        } catch (NotSupportedException |
                SystemException |
                HeuristicRollbackException |
                HeuristicMixedException |
                RollbackException e) {
            log.error(e.getMessage(), e);
        }
    }

    private <B> B createBeanInstance(final Bean<B> bean) {
        final Context context = beanManager.getContext(bean.getScope());

        final CreationalContext<B> creationalContext = beanManager.createCreationalContext(bean);

        return context.get(bean, creationalContext);
    }
}
