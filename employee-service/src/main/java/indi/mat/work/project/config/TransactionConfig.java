@Configuration
@Aspect
public class TransactionConfig {
    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        // read only
        RuleBasedTransactionAttribute readOnly = new RuleBasedTransactionAttribute();
        readOnly.setReadOnly(true);
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        // required
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        RollbackRuleAttribute rollbackOnException = new RollbackRuleAttribute(Exception.class);
        required.setRollbackRules(Collections.singletonList(rollbackOnException));
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // mapping methods
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // required: add/insert/update/delete methods
        source.addTransactionalMethod("update*", required);
        source.addTransactionalMethod("add*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("delete*", required);
        // read only: count/select/get/page
        source.addTransactionalMethod("count*", readOnly);
        source.addTransactionalMethod("select*", readOnly);
        source.addTransactionalMethod("get*", readOnly);
        source.addTransactionalMethod("page*", readOnly);
        // required: others
        source.addTransactionalMethod("*", required);
        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.newegg.staffing.service.*.impl.*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
