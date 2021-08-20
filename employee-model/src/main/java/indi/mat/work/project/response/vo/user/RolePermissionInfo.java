package indi.mat.work.project.response.vo.user;

public class RolePermissionInfo {
    private Long roleId;
    private String herf;
    private Integer functionCode;

    public RolePermissionInfo() {
    }

    public RolePermissionInfo(Long roleId, String herf, Integer functionCode) {
        this.roleId = roleId;
        this.herf = herf;
        this.functionCode = functionCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public Integer getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(Integer functionCode) {
        this.functionCode = functionCode;
    }
}
