import java.util.List;
import java.util.Map;

public abstract class CommonValidator {

    public String evaluation(String s) {
        StringBuilder sb = new StringBuilder();
        if(customEvaluation(sb, s)) {
            CommonRuleService service = SpringContextUtils.getBean(CommonRuleServiceImpl.class);
            Map<String, Map<Integer, List<CommonRuleEntity>>> map = service.getAll();
            if (map.containsKey(getClass().getSimpleName())) {
                Map<Integer, List<CommonRuleEntity>> gRules = map.get(getClass().getSimpleName());
                for (Map.Entry<Integer, List<CommonRuleEntity>> gRule : gRules.entrySet()) {
                    CommonRuleStrategy strategy = CommonRuleStrategy.build();
                    List<CommonRuleEntity> l = gRule.getValue();
                    strategy.executes(l, s);
                    if (strategy.isCurrStatus()) {
                        ValidatorUtils.addQir(sb, "规则" + gRule.getKey() + "校验：", "【", String.join("   ", strategy.getRuleMatchResult()), "】");
                    }
                }
            }
        }
        return sb.toString();
    }

    public abstract boolean customEvaluation(StringBuilder sb, String s);

}
